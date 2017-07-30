package mall.web.fore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import mall.dao.OrderDAO;
import mall.entity.Order;
import mall.entity.OrderItem;
import mall.entity.User;
import mall.service.OrderItemService;
import mall.service.OrderService;
import mall.service.ProductService;
import mall.util.Page;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ForeOrder {

	@Resource
	OrderService orderService;
	
	@Resource
	OrderItemService orderItemService;
	
	@Resource
	ProductService productService;
	
	@RequestMapping("/forecreateOrder")
	public String addOrder(Order order,HttpSession session,Model model){
		List<OrderItem> orderItems=(List<OrderItem>)session.getAttribute("ois");
		User user=(User)session.getAttribute("user");
		if(orderItems.isEmpty())
			return "/login";
		
		order.setUser(user);
		orderService.addOrder(order);
		float total=0f;
		for(OrderItem orderItem:orderItems){
			if(orderItem.getId()==0){
				orderItem.setOrder(order);
                orderItemService.add(orderItem);
				total+=orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
				break;
			}
			orderItem.setOrder(order);
			orderItemService.update(orderItem);
			total+=orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
		}
		model.addAttribute("total", total);
		model.addAttribute("oid", order.getId());
		return "/alipay";
	}
	
	@RequestMapping("/forepayed")
	public String payOrder(@RequestParam("oid") int oid,@RequestParam("total")float total,Model model){
		Order order=orderService.getOrder(oid);
		order.setPayDate(new Date());
		order.setStatus("waitDelivery");
		order.setTotal(total);
		orderService.updateOrder(order);
		List<OrderItem> orderItems=orderItemService.listByOrder(oid);
		for(OrderItem orderItem:orderItems){
			productService.reduceStock(orderItem);
		}
		model.addAttribute("o", order);
		return "/payed";
	}
	
	@RequestMapping("/forebought")
	public String getOrdersByUser(HttpSession session,Model model){
		User user=(User)session.getAttribute("user");
		if(user==null)
			return "/login";
		List<Order> os=orderService.listOrdersByUser(user.getId(), "delete");
		model.addAttribute("os", os);
		return "/bought";
	}
	
	@RequestMapping("/foredeleteOrder")
    public @ResponseBody String deleteOrder(HttpSession session,int oid){
		User user=(User)session.getAttribute("user");
		if(user==null)
			return "false";
		Order order=orderService.getOrder(oid);
		if(order==null)
			return "false";
		order.setStatus("delete");
		orderService.updateOrder(order);
    	return "success";
    }
	
	@RequestMapping("/foreconfirmPay")
	public String confirmPay(int oid,HttpSession session,Model model){
		User user=(User)session.getAttribute("user");
		if(user==null)
			return "/login";
		Order order=orderService.getOrder(oid);
		model.addAttribute("o", order);
		return "/confirmPay";
	}
	
	@RequestMapping("/foreorderConfirmed")
	public String orderConfirmed(int oid,HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user==null)
			return "/login";
		Order order=orderService.getOrder(oid);
		order.setStatus("waitReview");
		order.setConfirmDate(new Date());
		orderService.updateOrder(order);
		return "/orderConfirmed";
	}
	
}
