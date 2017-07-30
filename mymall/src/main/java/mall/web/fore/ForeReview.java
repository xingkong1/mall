package mall.web.fore;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import mall.entity.Order;
import mall.entity.Product;
import mall.entity.Review;
import mall.entity.User;
import mall.service.OrderService;
import mall.service.ReviewService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForeReview {

	@Resource
	OrderService orderService;
	
	@Resource
	ReviewService reviewService;
	
	@RequestMapping("/forereview")
	public String review(int oid,Model model){
		Order order=orderService.getOrder(oid);
		Product product=order.getOrderItems().get(0).getProduct();
		List<Review> reviews=reviewService.listByProduct(product.getId());
		model.addAttribute("o", order);
		model.addAttribute("p", product);
		model.addAttribute("reviews", reviews);
		return "/review";
	}
	
	@RequestMapping("/foredoreview")
	public String addReview(@RequestParam("content") String content,@RequestParam("oid") int oid,
			@RequestParam("pid") int pid,HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user==null)
			return "/login";
		Review review=new Review();
		review.setContent(content);
		review.setCreateDate(new Date());
		review.setUser(user);
		review.setProduct(new Product(pid));
		reviewService.add(review);
		Order order=orderService.getOrder(oid);
		order.setStatus("finish");
		orderService.updateOrder(order);
		return "redirect :/review?oid="+oid;
	}
}
