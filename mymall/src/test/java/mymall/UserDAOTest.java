package mymall;

import java.util.List;

import javax.annotation.Resource;

import mall.dao.UserDAO;
import mall.entity.OrderStatus;
import mall.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-mybatis.xml"})
public class UserDAOTest {

	@Resource
	public UserDAO userDAO;
	
	@Test
	public void get(){
		User user=userDAO.get(1);
		System.out.println(user.getName());
		
		User user2=userDAO.getByName("zhangsan");
		System.out.println(user2);
		
		User user3=userDAO.getByUser("zhangsan", "1234");
		System.out.println(user3);
	}
	
	@Test
	public void listAll(){
		List<User> users=userDAO.listAll();
		for(User user:users){
			System.out.println(user.getName());
		}
	}
	
	@Test
	public void list(){
		List<User> categories=userDAO.list(0, 10);
		for(User c:categories){
			System.out.println(c.getId()+" : "+c.getName());
		}
	}
	
	@Test
	public void add(){
	
		User user=new User();
		user.setName("root");
		user.setPassword("1234");
		userDAO.add(user);
		System.out.println(user.getId());
	}
	
	@Test
	public void total(){
		int total=userDAO.getTotal();
		System.out.print(total);
	}
	
	@Test
	public void delete(){
		userDAO.delete(85);
	}
	
	@Test
	public void test(){
		System.out.print(OrderStatus.WAIT_PAY.toString());
	}
}
