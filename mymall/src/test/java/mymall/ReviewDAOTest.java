package mymall;

import java.util.Date;

import javax.annotation.Resource;

import mall.dao.ReviewDAO;
import mall.entity.Review;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-mybatis.xml"})
public class ReviewDAOTest {

	@Resource
	ReviewDAO reviewDAO;
	
	
	
	@Test
	public void get(){
		Review review=reviewDAO.get(1);
		System.out.println(review);
	}
	
	@Test
	public void add(){
		Review review=new Review();
		review.setContent("还可以");
		review.setCreateDate(new Date());
		reviewDAO.add(review);
	}
	
	@Test
	public void delete(){
		reviewDAO.delete(3);
	}
}
