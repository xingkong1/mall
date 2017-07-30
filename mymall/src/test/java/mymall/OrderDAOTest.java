package mymall;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mall.dao.OrderDAO;
import mall.entity.Order;
import mall.entity.OrderStatus;
import mall.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-mybatis.xml"})
public class OrderDAOTest {

	@Resource
	private OrderDAO orderDAO;
	
	@Test
	public void add(){
		Order order=new Order();
		User user=new User();
		user.setId(2);
		order.setAddress("大良");
		order.setCreateDate(new Date());
		order.setMobile("1111134666");
		order.setOrderCode("1234");
		order.setUser(user);
		orderDAO.add(order);
		System.out.println("order:"+order.getId());
	}
	
	@Test
	public void get(){
		Order order=orderDAO.get(9);
		System.out.println(order);
	}
	@Test
	public void list(){
		List<Order> orders=orderDAO.listOrders(0, Short.MAX_VALUE);
		for(Order order:orders){
			System.out.println(order);
		}
	}
	
	@Test
	public void delete(){
		orderDAO.delete(9);
	}
	
	@Test
	public void listByUser(){
		List<Order> orders=orderDAO.listOrdersByUser(1, OrderStatus.DELETE.toString(), 0, Short.MAX_VALUE);
		for(Order order:orders){
			System.out.println(order);
		}
	}
}
