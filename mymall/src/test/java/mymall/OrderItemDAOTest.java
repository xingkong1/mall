package mymall;

import java.util.List;

import javax.annotation.Resource;

import mall.dao.OrderItemDAO;
import mall.entity.OrderItem;
import mall.entity.Product;
import mall.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-mybatis.xml"})
public class OrderItemDAOTest {
     
	@Resource
	private OrderItemDAO orderItemDAO;
	
	@Test
	public void add(){
		OrderItem orderItem=new OrderItem();
		orderItem.setNumber(20);
		Product product=new Product();
		product.setId(730);
		orderItem.setProduct(product);
		User user=new User();
		user.setId(1);
		orderItem.setUser(user);
		orderItemDAO.add(orderItem);
		System.out.println(orderItem.getId());
	}
	
	@Test
	public void get(){
		OrderItem orderItem=orderItemDAO.get(4);
		System.out.println(orderItem.getNumber());
		System.out.println(orderItem.getUser());
	}
	
	@Test
	public void update(){
		OrderItem orderItem=orderItemDAO.get(4);
		orderItem.setNumber(10);
		orderItemDAO.update(orderItem);
		System.out.println(orderItem.getNumber());
	}
	
	@Test
	public void getByUser(){
		List<OrderItem> orderItems=orderItemDAO.listByUser(1, 0, 5);
		for(OrderItem orderItem:orderItems){
			System.out.println(orderItem);
		}
	}
	
	@Test
	public void listByProduct(){
		List<OrderItem> orderItems=orderItemDAO.listByProduct(321, 0, 5);
		for(OrderItem orderItem:orderItems){
			System.out.println(orderItem);
		}
	}
	
	@Test
	public void listByOrder(){
		List<OrderItem> orderItems=orderItemDAO.listByOrder(7, 0, 5);
		for(OrderItem orderItem:orderItems){
			System.out.println(orderItem);
		}
	}
	
	@Test
	public void delete(){
		orderItemDAO.delete(4);
		
	}
	
}
