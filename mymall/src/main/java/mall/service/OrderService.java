package mall.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mall.dao.OrderDAO;
import mall.entity.Order;
import mall.entity.OrderItem;
import mall.util.Page;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Resource
	OrderDAO orderDAO;
	
	@Resource
	OrderItemService orderItemService;
	
	public void addOrder(Order order){
		String orderCode=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+RandomUtils.nextInt(1000);
		order.setCreateDate(new Date());
		order.setOrderCode(orderCode);
		order.setStatus("waitPay");
		orderDAO.add(order);
	};

	public void deleteOrder(int id){
		orderDAO.delete(id);
	};

	public void updateOrder(Order bean){;
	      orderDAO.update(bean);
	}

	public Order getOrder(int id){
		  Order order= orderDAO.get(id);
		  orderItemService.fillOrder(order);
		  return order;
	}

	public void listOrders(Page<Order> page){
		List<Order> os=new ArrayList<Order>();
		List<Order> orders=orderDAO.listOrders(page.getStart(), page.getCount());
		if(orders!=null&&!orders.isEmpty()){
			for(Order order:orders){
				List<OrderItem> orderItems=orderItemService.listByOrder(order.getId());
				int totalNumber=0;
				for(OrderItem orderItem:orderItems){
					totalNumber+=orderItem.getNumber();
				}
				float total =orderItemService.getTotal(orderItems);
				order.setOrderItems(orderItems);
				order.setTotal(total);
				order.setTotalNumber(totalNumber);
				page.setTotal(orders.size());
			}
			os=orders;
		}
		page.setData(os);
	};
	
	public List<Order> listOrdersByUser(int uid,
			String excludedStatus){
		List<Order> os=new ArrayList<Order>();
		List<Order> orders=orderDAO.listOrdersByUser(uid, excludedStatus, 0, Short.MAX_VALUE);
		if(orders!=null&&!orders.isEmpty()){
			for(Order order:orders){
				List<OrderItem> orderItems=orderItemService.listByOrder(order.getId());
				float total =orderItemService.getTotal(orderItems);
				order.setOrderItems(orderItems);
				order.setTotal(total);
				
			}
			os=orders;
		}
		return os;
	};

	public int getTotal(){
		return 0 ;
	};
}
