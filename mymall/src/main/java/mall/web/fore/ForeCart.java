package mall.web.fore;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import mall.entity.OrderItem;
import mall.entity.Product;
import mall.entity.User;
import mall.service.OrderItemService;
import mall.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ForeCart {

	@Resource
	OrderItemService orderItemService;
	
	@Resource
	ProductService productService;
	
	@RequestMapping(value="/foreaddCart")
	public @ResponseBody String addCart(@RequestParam("pid") int pid,@RequestParam("num") int num
			,HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user == null)
		  return "false";
		List<OrderItem> orderItems=orderItemService.listByUser(user.getId());
		boolean find=true;
		for(OrderItem orderItem:orderItems){
			if(orderItem.getProduct().getId()==pid){
				orderItem.setNumber(orderItem.getNumber()+num);
				orderItemService.update(orderItem);
				find =false;
				break;
			}
		}
		if(find){
			OrderItem orderItem=new OrderItem();
			orderItem.setProduct(new Product(pid));
			orderItem.setUser(user);
			orderItem.setNumber(num);
			orderItemService.add(orderItem);
		}
		
		return "success";
	}
	
	@RequestMapping("/forecart")
	public String getCart(HttpSession session,Model model ){
		User user=(User)session.getAttribute("user");
		if(user==null)
			return "/login";
		List<OrderItem> orderItems=orderItemService.listByUser(user.getId());
		model.addAttribute("ois", orderItems);
		return "/cart";
	}
	
	@RequestMapping("/foredeleteOrderItem")
	public @ResponseBody String deleteCart(@RequestParam(value="oiid") int oiid,HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user==null)
			return "false";
		orderItemService.delete(oiid);
		return "success";
	}
	
	@RequestMapping("/forechangeOrderItem")
	public String changeCart(@RequestParam(value="oiid") int oiid,@RequestParam("num") int num,HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user==null)
			return "false";
		OrderItem orderItem=orderItemService.get(oiid);
		if(orderItem==null)
			return "false";
		orderItem.setNumber(orderItem.getNumber()+num);
		orderItemService.update(orderItem);
		return "success";
	}
	
	@RequestMapping("/forebuyone")
	public String buyone(@RequestParam("pid")int pid,@RequestParam("num") int num,
			HttpSession session,Model model){
		User user=(User)session.getAttribute("user");
		if(user==null)
			return "false";
		List<OrderItem> ois=new ArrayList<OrderItem>();
		Product product=productService.get(pid);
		OrderItem orderItem=new OrderItem();
		orderItem.setNumber(num);
		orderItem.setProduct(product);
		orderItem.setUser(user);
		float total=num*product.getPromotePrice();
	    ois.add(orderItem);
	    session.setAttribute("ois", ois);
	    model.addAttribute("total", total);
		return "/buy";
	}
	
	@RequestMapping("/forebuy")
	public String buy(int[] oiid,HttpSession session,Model model){
		if(oiid==null||oiid.length==0)
			return "redirect:/forecart";
		List<OrderItem> ois=new ArrayList<OrderItem>();
		float total=0;
		for(int oid:oiid){
			OrderItem orderItem=orderItemService.get(oid);
			ois.add(orderItem);
			total+=orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
		}
		session.setAttribute("ois", ois);
	    model.addAttribute("total", total);
		return "/buy";
	}
	
	
	
}
