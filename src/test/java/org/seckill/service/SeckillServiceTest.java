package org.seckill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml",
		"classpath:spring/spring-service.xml" })
public class SeckillServiceTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}", list);
	}

	@Test
	public void testGetSeckillById() {
		long id = 1000L;
		Seckill seckill = seckillService.getSeckillById(id);
		logger.info("seckill={}", seckill);
	}

	@Test
	public void testExportSeckillUrl() {
		long id = 1000L;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		logger.info("exposer={}", exposer);
	}

	@Test
	public void testExecuteSeckill() {
		long id = 1000L;
		long phone = 18801405609L;
		String md5 = "55sa6545a2s5wq4s2";
		SeckillExecution exception = seckillService.executeSeckill(id, phone,
				md5);
		logger.info("result={}", exception);
	}

	/**
	 * ºØ≥…≤‚ ‘exportSeckillUrl()∫ÕexecuteSeckill()
	 */
	@Test
	public void testSeckillLogic() {
		long id = 1000L;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if (exposer.isExposer()) {
			logger.info("exposer={}", exposer);
			long phone = 18801405609L;
			String md5 = "55sa6545a2s5wq4s2";
			try {
				SeckillExecution exception = seckillService.executeSeckill(id,
						phone, md5);
				logger.info("result={}", exception);
			} catch (RepeatKillException e) {
				logger.error(e.getMessage());
			} catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	@Test
	public void testExecuteSeckillProcedure(){
		long seckillId = 1003L;
		long phone = 18801405609L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if(exposer.isExposer()){
			String md5 = exposer.getMd5();
			 SeckillExecution  execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
			 logger.info(execution.getStateInfo());
		}
	}
}
