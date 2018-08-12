package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * redis缓存测试类
 * @author Administrator
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void testSeckill() throws Exception{
		//测试取和存
		long id = 1001L;
		Seckill seckill = redisDao.getSeckill(id);
		if(seckill == null){
			seckill = seckillDao.queryById(id);
			if(seckill != null){
				String result = redisDao.putSeckill(seckill);
				System.out.println("put结果为：" + result);
				seckill = redisDao.getSeckill(id);
				System.out.println("get结果为：" + result);
			}
		}
	}
}
