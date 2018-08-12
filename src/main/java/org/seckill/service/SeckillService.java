package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

/**
 * seckill业务接口:站在使用者的角度上来设计接口
 * 
 * @author Administrator
 * 
 */
public interface SeckillService {

	/**
	 * 查询所有秒杀记录
	 * 
	 * @return
	 */
	public List<Seckill> getSeckillList();

	/**
	 * 查询单个秒杀记录
	 * 
	 * @param seckillId
	 * @return
	 */
	public Seckill getSeckillById(long seckillId);

	/**
	 * 秒杀开启是输出秒杀接口地址， 否则输出系统时间和秒杀时间
	 * 
	 * @param seckillId
	 * @return
	 */
	public Exposer exportSeckillUrl(long seckillId);

	/**
	 * 执行秒杀操作
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException;
	
	/**
	 * 通过存储过程执行秒杀操作
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);
}
