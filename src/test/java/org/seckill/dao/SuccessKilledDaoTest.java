package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

	@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void TestInsertSuccessKilled(){
		long id = 1001L;
		long phone = 18801405609L;
		int insertCount = successKilledDao.insertSuccessKilled(id, phone);
		System.out.println("插入了" + insertCount + "条数据！");
	}
	
	@Test
	public void testQueryByIdWithSeckillId(){
		long id = 1001L;
		long phone = 18801405609L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckillId(id, phone);
		System.out.println(successKilled.toString());
		System.out.println(successKilled.getSeckill().toString());
	}
}
