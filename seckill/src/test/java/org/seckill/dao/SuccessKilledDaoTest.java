package org.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * 配置spring和junit整合，junit启动时加载springIoc容器
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

	@Resource
	private SuccessKilledDao successkilledDao;
	
	@Test
	public void insertSuccessKilledTest() throws Exception{
		int insertCount=successkilledDao.insertSuccessKilled(1001L,13923246885L);
		System.out.println(insertCount);
	}
	
	@Test
	public void queryByIdWithSeckillTest() throws Exception{
		SuccessKilled successKilled=successkilledDao.queryByIdWithSeckill(1000L,361555);
		Seckill seckill=successKilled.getSeckill();
		System.out.println(successKilled);
		System.out.println(seckill);
		
	}
}
