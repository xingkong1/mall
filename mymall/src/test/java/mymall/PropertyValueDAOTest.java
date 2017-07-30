package mymall;

import java.util.List;

import javax.annotation.Resource;

import mall.dao.PropertyValueDAO;
import mall.entity.PropertyValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mybatis.xml"})
public class PropertyValueDAOTest {

	@Resource
	PropertyValueDAO propertyValueDAO;
	
	@Test
	public void test1(){
		PropertyValue propertyValue=propertyValueDAO.get(800);
		System.out.println(propertyValue);
		System.out.println(propertyValue.getProduct());
		System.out.println(propertyValue.getProperty());
	}
	
	@Test
	public void test2(){
		List<PropertyValue> propertyValues=propertyValueDAO.list(0, Short.MAX_VALUE);
		for(PropertyValue propertyValue:propertyValues){
			System.out.println(propertyValue.getId());
			System.out.println(propertyValue);
			System.out.println(propertyValue.getProduct());
			System.out.println(propertyValue.getProperty());
		}
	}
}
