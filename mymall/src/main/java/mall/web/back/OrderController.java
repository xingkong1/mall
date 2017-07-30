package mall.web.back;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mall.entity.Order;
import mall.service.OrderService;
import mall.util.Page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	OrderService orderService;
	
	@RequestMapping("/list")
	public String getAllOrder(@ModelAttribute Page<Order> page){
		orderService.listOrders(page);
		return "/admin/listOrder";
	}
	
	@RequestMapping("/delivery")
	public String delivery(int id){
		Order order=orderService.getOrder(id);
		if(order!=null){
			order.setDeliveryDate(new Date());
			order.setStatus("waitConfirm");
			orderService.updateOrder(order);
		}
		return "/admin/listOrder";
	}
	
	
}
