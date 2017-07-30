package mall.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mall.dao.OrderItemDAO;
import mall.entity.Order;
import mall.entity.OrderItem;
import mall.entity.Product;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
 
	@Resource
	OrderItemDAO orderItemDAO;
	
	@Resource
	ProductService productService;
	
	public void add(OrderItem bean){
		if(bean!=null){
			Order order=bean.getOrder();
			if(order==null){
				bean.setOrder(new Order(-1));
			}
			orderItemDAO.add(bean);
		}
	};

	public void delete(int id){
		orderItemDAO.delete(id);
	};

	public void update(OrderItem bean){
		orderItemDAO.update(bean);
	};

	public OrderItem get(int id){
		OrderItem orderItem=new OrderItem();
		 OrderItem oItem=orderItemDAO.get(id);
		 if(oItem!=null){
			 productService.setFirstProductImage(oItem.getProduct());
			 orderItem=oItem;
		 }
		return orderItem;
	};

	public List<OrderItem> listByUser(int uid){
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		List<OrderItem> orderItems1=orderItemDAO.listByUser(uid, 0, Short.MAX_VALUE);
		if(orderItems1!=null){
			orderItems=orderItems1;
			for(OrderItem orderItem:orderItems){
				Product product=orderItem.getProduct();
				if(product!=null)
					productService.setFirstProductImage(product);
			}
		}
		return orderItems;
	};

	public List<OrderItem> listByOrder(int oid){
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		List<OrderItem> orderItems1=orderItemDAO.listByOrder(oid, 0, Short.MAX_VALUE);
		if(orderItems1!=null&&!orderItems1.isEmpty()){
			for(OrderItem orderItem:orderItems1){
				Product product=orderItem.getProduct();
				if(product!=null)
				productService.setFirstProductImage(product);
			}
			orderItems=orderItems1;
		}
		return orderItems;
	};
	
	public List<OrderItem> listByProduct(int pid){
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		List<OrderItem> orderItems1=orderItemDAO.listByProduct(pid, 0, Short.MAX_VALUE);
		if(orderItems1!=null){
			orderItems=orderItems1;
		}
		return orderItems;
	};
	
	public float getTotal(List<OrderItem> orderItems){
		float total=0;
		if(orderItems!=null&&!orderItems.isEmpty()){
			for(OrderItem orderItem:orderItems){
				total+=getTotal(orderItem);
			}
		}
		return total;
	}
	
	public float getTotal(OrderItem orderItem){
		float total=0;
		if(orderItem!=null)
			total =orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
		return total;
	}
	
	public void fillOrder(Order order){
		List<OrderItem> orderItems=listByOrder(order.getId());
		order.setOrderItems(orderItems);
	}

	public int getTotal(){
		return orderItemDAO.getTotal();
	};
	
	public int getSaleNumber(int pid){
		return orderItemDAO.getSaleNumber(pid);
	};
}
