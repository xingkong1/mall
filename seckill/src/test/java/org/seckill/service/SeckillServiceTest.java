package org.seckill.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * 配置spring和junit整合，junit启动时加载springIoc容器
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() throws Exception{
		List<Seckill> seckills=seckillService.getSeckillList();
		logger.info("list={}",seckills);
	}
	
	@Test
	public void testGetById() throws Exception{
		Seckill seckill=seckillService.getById(1000L);
		logger.info("seckill={}",seckill);
	}
	
	@Test
	public void testSeckill() throws Exception{
		long id=1000;
		Exposer exposer=seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()){
			logger.info("exposer={}",exposer);
			String md5=exposer.getMd5();
			long userPhone=13923246885L;
			try {
				SeckillExecution seckillExecution=seckillService.executeSeckill(id, userPhone, md5);
				logger.info("result={}",seckillExecution);
			} catch (RepeatKillException e1) {
				logger.error(e1.getMessage());
			}catch (SeckillCloseException e2) {
				logger.error(e2.getMessage());
			}
			
		}else{
			//秒杀未开启
			logger.warn("exposer={}",exposer);
		}
	}
}
	
