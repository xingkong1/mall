package mymall;

import java.util.List;

import javax.annotation.Resource;

import mall.dao.PropertyDAO;
import mall.entity.Category;
import mall.entity.Property;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-mybatis.xml"})
public class PropertyDAOTest {

	@Resource
	public PropertyDAO propertyDAO;
	
	@Test
	public void get(){
		Property property=propertyDAO.get(50);
		System.out.println(property.getName());
		System.out.println(property.getCategory().getName());
		
	}
	
	@Test
	public void add(){
		Property property=new Property();
		Category category=new Category();
		category.setId(83);
		property.setName("长度");
		property.setCategory(category);
		propertyDAO.add(property);
	}
	
	@Test
	public void listAll(){
		List<Property> properties=propertyDAO.listAll();
		for(Property property:properties){
			System.out.println(property.getName()+":"+property.getCategory().getName());
		}
	}
	
	
	@Test
	public void total(){
		//int total=propertyDAO.getTotal();
		//System.out.print(total);
	}
	
	@Test
	public void delete(){
		propertyDAO.delete(258);
	}
	
	@Test
	public void list(){
		List<Property> properties=propertyDAO.listBycid(60, 0, 10);
		for(Property property:properties){
			System.out.println(property.getName()+":"+property.getCategory().getName());
		}
	}
	
}
