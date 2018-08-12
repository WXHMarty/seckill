package org.seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * seckill业务实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillDao seckillDao;

	@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Autowired
	private RedisDao redisDao;

	// md5颜值字符串，用于混淆md5
	private final String slat = "hsfda%$&)%kl41fes53hjksHDFKL(^&&$~;;";

	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 10);
	}

	public Seckill getSeckillById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		//缓存优化
		//访问redis
		Seckill seckill = redisDao.getSeckill(seckillId);
		if(seckill == null){
			//访问数据库
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			}else{
				//如果数据库中有就放入redis中
				redisDao.putSeckill(seckill);
			}
		}
		
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();

		// 系统当前时间
		Date nowTime = new Date();

		// 秒杀时间还没开始或者秒杀时间已经结束
		if (nowTime.getTime() < startTime.getTime()
				|| nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(),
					startTime.getTime(), endTime.getTime());
		}
		String md5 = this.getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	/**
	 * 使用注解来配置事务的优点 
	 * 1.开发团队达成一致的约定，明确标注事务方法的编程风格
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作
	 * 3.不是所有的方法都需要事务
	 */
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if (md5 == null || !md5.equals(this.getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}

		Date now = new Date();
		try {
			// 记录购买行为
			int inserCount = successKilledDao.insertSuccessKilled(
					seckillId, userPhone);
			if (inserCount <= 0) {
				// 如果inserCount<=0，意味着重复秒杀
				throw new RepeatKillException("seckill repeated");
			} else {
				// 执行秒杀业务逻辑，热点商品秒杀，此步骤会发生高并发行为，因此尽量减少此步骤运行
				int updateCount = seckillDao.reduceNumber(seckillId, now);
				if (updateCount <= 0) {
					// 如果updateCount<=0则没有更新到记录,秒杀结束
					throw new SeckillCloseException("seckill is closed");
				} else {
					// 秒杀成功
					SuccessKilled successKilled = successKilledDao
							.queryByIdWithSeckillId(seckillId, userPhone);
					return new SeckillExecution(seckillId, 1, "秒杀成功",
							successKilled);
				}
			}
			
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 所有变异期间的异常转换为运行期间的异常
			throw new SeckillException("seckill inner error: " + e.getMessage());
		}
	}

	public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
		if (md5 == null || !md5.equals(this.getMD5(seckillId))) {
			return new SeckillExecution(seckillId, -1, "秒杀失败");
		}
		Date killTime = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", killTime);
		map.put("result", null);
		try{
			seckillDao.killByProcedure(map);
			int result = MapUtils.getInteger(map, "result", -2);
			if(result == 1){
				SuccessKilled sk = successKilledDao.queryByIdWithSeckillId(seckillId, userPhone);
				return new SeckillExecution(seckillId, 1, "秒杀成功", sk);
			}else{
				return new SeckillExecution(seckillId, -1, "秒杀失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return new SeckillExecution(seckillId, -2, "内部异常");
		}
	}

}
