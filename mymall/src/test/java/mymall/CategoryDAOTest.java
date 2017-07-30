package mymall;

import java.util.List;

import javax.annotation.Resource;

import mall.dao.CategoryDAO;
import mall.entity.Category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-mybatis.xml"})
public class CategoryDAOTest {

	
	@Resource
	public CategoryDAO beanDAO;
	
	@Test
	public void get(){
		Category category=beanDAO.get(80);
		//Category category=categoryDAO.get(80);
		System.out.println(category.getName());
	}
	
	@Test
	public void listAll(){
		List<Category> categories=beanDAO.listAll();
		for(Category c:categories){
			System.out.println(c.getName());
		}
	}
	
	@Test
	public void list(){
		List<Category> categories=beanDAO.list(0, 10);
		for(Category c:categories){
			System.out.println(c.getId()+" : "+c.getName());
		}
	}
	
	@Test
	public void add(){
	
		Category category=new Category();
		category.setName("矿泉水");
		beanDAO.add(category);
		System.out.println(category.getId());
	}
	
	@Test
	public void total(){
		int total=beanDAO.getTotal();
		System.out.print(total);
	}
	
	@Test
	public void delete(){
		beanDAO.delete(85);
	}
}
