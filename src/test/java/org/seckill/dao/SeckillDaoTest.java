package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/*
 * 配置spring和junit整合，junit启动时加载springIoc容器
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	//注入Dao实现类依赖
	@Resource
	private SeckillDao seckilldao; 
	
	@Test
	public void testReduceNumber() throws Exception{
		Date date=new Date();
		int updateCount=seckilldao.reduceNumber(1000L, date);
		System.out.println(updateCount);
		
	}
	
	@Test
	public void testQueryById() throws Exception{
		long id=1000;
		Seckill seckill=seckilldao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}
	
	@Test
	public void testQueryAll() throws Exception{
		List<Seckill> seckills=seckilldao.queryAll(0, 4);
		for(Seckill seckill:seckills){
			System.out.println(seckill);
		}
	}
}
