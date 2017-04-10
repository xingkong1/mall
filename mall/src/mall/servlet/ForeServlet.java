package mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.util.HtmlUtils;

import mall.Dao.ProductDAO;
import mall.Dao.ProductImageDAO;
import mall.bean.Category;
import mall.bean.Order;
import mall.bean.OrderItem;
import mall.bean.Product;
import mall.bean.ProductImage;
import mall.bean.PropertyValue;
import mall.bean.Review;
import mall.bean.User;
import mall.comparator.ProductAllComparator;
import mall.comparator.ProductDateComparator;
import mall.comparator.ProductPriceComparator;
import mall.comparator.ProductReviewComparator;
import mall.comparator.ProductSaleCountComparator;
import mall.util.Page;

@SuppressWarnings("serial")
public class ForeServlet extends BaseForeServlet {

	public String home(HttpServletRequest request,HttpServletResponse response) {
		List<Category> cs=categoryDAO.list();
		productDAO.fill(cs);
		productDAO.fillByRow(cs);
		request.setAttribute("cs", cs);
		return "/home.jsp";
	}
	
	public String registerName(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name=request.getParameter("name");
		name=HtmlUtils.htmlEscape(name);
		boolean exist=userDAO.isExist(name);
		if(exist){
			String msg="false";
			response.setContentType("text/html;charset=utf-8");
			return "%"+msg;
		}
		return "%true";
	}
	
	
	public String register(HttpServletRequest request,HttpServletResponse response){
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		password=HtmlUtils.htmlEscape(password);
		User user=userDAO.get(name);
		if(user==null){
			user=new User();
			user.setName(name);
			user.setPassword(password);
			userDAO.add(user);
			request.getSession().setAttribute("user", user);
			return "/registerSuccess.jsp";
		}
		String msg="用户已经存在";
		request.setAttribute("msg", msg);
		return "/register.jsp";
	}
	
	public String logout(HttpServletRequest request,HttpServletResponse response){
		request.getSession().removeAttribute("user");
		return "@forehome";
	}
	
	public String login(HttpServletRequest request,HttpServletResponse response){
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		password=HtmlUtils.htmlEscape(password);
		User user=userDAO.get(name, password);
		if(user==null){
			String msg="用户名或密码错误";
			request.setAttribute("msg", msg);
			return "/register.jsp";		
		}
		request.getSession().setAttribute("user", user);
		return "@forehome";
	}
	
	public String product(HttpServletRequest request,HttpServletResponse response){
		int pid=Integer.parseInt(request.getParameter("pid"));
		Product product=productDAO.get(pid);
		List<ProductImage> productDetailImages=productImageDAO.list(product, ProductImageDAO.type_detail);
		List<ProductImage> productSingleImages=productImageDAO.list(product, ProductImageDAO.type_single);
		product.setProductDetailImages(productDetailImages);
		product.setProductSingleImages(productSingleImages);
		
		List<PropertyValue> pvs=propertyValueDAO.list(pid);
		List<Review> reviews=reviewDAO.list(pid);
		
		productDAO.setSaleAndReviewNumber(product);
		
		request.setAttribute("p", product);
		request.setAttribute("reviews", reviews);
		request.setAttribute("pvs", pvs);
		
		return "/product.jsp";
	}
	
	public String checkLogin(HttpServletRequest request,HttpServletResponse response){
		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			return "%false";
		}
		return "%success";
	}
	
	public String loginAjax(HttpServletRequest request,HttpServletResponse response){
		
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		password=HtmlUtils.htmlEscape(password);
		User user=userDAO.get(name, password);
		if(user==null){
			return "%false";		
		}
		request.getSession().setAttribute("user", user);
		return "%success";
	}
	
	public String category(HttpServletRequest request,HttpServletResponse response){
		int cid=Integer.parseInt(request.getParameter("cid"));
		String sort=request.getParameter("sort");
		Category category=categoryDAO.get(cid);
		productDAO.fill(category);
		productDAO.setSaleAndReviewNumber(category.getProducts());
		if(sort!=null){
			switch (sort) {
			case "all":
				Collections.sort(category.getProducts(), new ProductAllComparator());
				break;
			case "review":
				Collections.sort(category.getProducts(), new ProductReviewComparator());
				break;
			case "date":
				Collections.sort(category.getProducts(), new ProductDateComparator());
				break;
			case "saleCount":
				Collections.sort(category.getProducts(), new ProductSaleCountComparator());
				break;
			case "price":
				Collections.sort(category.getProducts(), new ProductPriceComparator());
				break;
			}
		}
		request.setAttribute("c", category);
		return "/category.jsp";
	}
	
	public String search(HttpServletRequest request,HttpServletResponse response){
		String keyword=request.getParameter("keyword");
		List<Product> ps=productDAO.search(keyword, 0, 20);
		productDAO.setSaleAndReviewNumber(ps);
		request.setAttribute("ps", ps);
		return "/searchResult.jsp";
	}

	public String buyone(HttpServletRequest request,HttpServletResponse response){
		int pid=Integer.parseInt(request.getParameter("pid"));
		int num=Integer.parseInt(request.getParameter("num"));
		User user=(User)request.getSession().getAttribute("user");
		Product p=productDAO.get(pid);
		
		List<OrderItem> ois=orderItemDAO.listByUser(user.getId());
		for(OrderItem oi:ois){
			if(oi.getProduct().getId()==pid){
				oi.setNumber(oi.getNumber()+num);
				orderItemDAO.update(oi);
				int oiid=oi.getId();
				return "@forebuy?oiid="+oiid;
			}
		}
		OrderItem oi=new OrderItem();
		oi.setNumber(num);
		oi.setProduct(p);
		oi.setUser(user);
		orderItemDAO.add(oi);
		int oiid=oi.getId();
		return "@forebuy?oiid="+oiid;
	}
	
	public String buy(HttpServletRequest request,HttpServletResponse response){
		String[] oiids=request.getParameterValues("oiid");
		List<OrderItem> ois=new ArrayList<>();
		float total=0;
		
		for(String oiid:oiids){
			OrderItem oi=orderItemDAO.get(Integer.parseInt(oiid));
			ois.add(oi);
			total+=oi.getNumber()*oi.getProduct().getPromotePrice();
		}
		request.getSession().setAttribute("ois", ois);
		request.setAttribute("total", total);
		return "/buy.jsp";
	}
	
	public String addCart(HttpServletRequest request,HttpServletResponse response){
		int pid=Integer.parseInt(request.getParameter("pid"));
		int num=Integer.parseInt(request.getParameter("num"));
		User user=(User)request.getSession().getAttribute("user");
		Product p=productDAO.get(pid);
		
		List<OrderItem> ois=orderItemDAO.listByUser(user.getId());
		for(OrderItem oi:ois){
			if(oi.getProduct().getId()==pid){
				oi.setNumber(oi.getNumber()+num);
				orderItemDAO.update(oi);
				return "%success";
			}
		}
		OrderItem oi=new OrderItem();
		oi.setNumber(num);
		oi.setProduct(p);
		oi.setUser(user);
		orderItemDAO.add(oi);
		request.getSession().setAttribute("ois", ois);
		return "%success";
	}
	
	public String cart(HttpServletRequest request,HttpServletResponse response){
		User user=(User)request.getSession().getAttribute("user");
		List<OrderItem> ois=orderItemDAO.listByUser(user.getId());
		request.setAttribute("ois", ois);
		return "/cart.jsp";
	}
	
	public String changeOrderItem(HttpServletRequest request,HttpServletResponse response){
		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			return "@login.jsp";
		}
		int oiid=Integer.parseInt(request.getParameter("oiid"));
		int num=Integer.parseInt(request.getParameter("number"));
		OrderItem oi=orderItemDAO.get(oiid);
		if(oi!=null){
			oi.setNumber(num);
			orderItemDAO.update(oi);
			return "%success";
		}
		return "%false";
	}
	
	public String deleteOrderItem(HttpServletRequest request,HttpServletResponse response){
		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			return "%false";
		}
		int oiid=Integer.parseInt(request.getParameter("oiid"));
		OrderItem oi=orderItemDAO.get(oiid);
		if(oi!=null){
		orderItemDAO.delete(oiid);
			return "%success";
		}
		return "%false";
	}
	
	public String createOrder(HttpServletRequest request,HttpServletResponse response) throws IOException{
		User user=(User)request.getSession().getAttribute("user");
		List<OrderItem> ois=orderItemDAO.listByUser(user.getId());
		if(ois.isEmpty()){
			return "@login.jsp";
		}
		String address=request.getParameter("address");
		String post=request.getParameter("post");
		String receiver=request.getParameter("receiver");
		String mobile=request.getParameter("mobile");
		String userMessage=request.getParameter("userMessage");
		String orderCode=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+RandomUtils.nextInt(10000);
		
		Order order=new Order();
		order.setAddress(address);
		order.setMobile(mobile);
		order.setOrderCode(orderCode);
		order.setOrderItems(ois);
		order.setPost(post);
		order.setReceiver(receiver);
		order.setUserMessage(userMessage);
		order.setUser(user);
		order.setCreateDate(new Date());
		order.setStatus(orderDAO.waitPay);
		
		orderDAO.add(order);
		for(OrderItem oi:ois){
			oi.setOrder(order);
			orderItemDAO.update(oi);
		}
		float total=Float.parseFloat(request.getParameter("total"));
		request.setAttribute("total", total);
		request.setAttribute("oid", order.getId());
		return "/alipay.jsp";
	}
	
	public String payed(HttpServletRequest request,HttpServletResponse response){
		int oid=Integer.parseInt(request.getParameter("oid"));
		Order order=orderDAO.get(oid);
		order.setStatus(orderDAO.waitDelivery);
		order.setPayDate(new Date());
		orderDAO.update(order);
		request.setAttribute("o", order);
		
		return "/payed.jsp";
	}
	
	public String bought(HttpServletRequest request,HttpServletResponse response){
		User user=(User)request.getSession().getAttribute("user");
		List<Order> os=orderDAO.list(user.getId(), orderDAO.delete);
		orderItemDAO.fill(os);
		
		
		request.setAttribute("os", os);
		
		return "/bought.jsp";
	}
	
	public String confirmPay(HttpServletRequest request,HttpServletResponse response){
		int oid=Integer.parseInt(request.getParameter("oid"));
		Order order=orderDAO.get(oid);
		orderItemDAO.fill(order);
		request.setAttribute("o", order);
		
		return "/confirmPay.jsp";
	}
	
	public String orderConfirmed(HttpServletRequest request,HttpServletResponse response){
		int oid=Integer.parseInt(request.getParameter("oid"));
		Order order=orderDAO.get(oid);
		order.setStatus(orderDAO.waitReview);
		order.setConfirmDate(new Date());
		orderDAO.update(order);
		
		return "/orderConfirmed.jsp";
	}
	
	public String deleteOrder(HttpServletRequest request,HttpServletResponse response){
		int oid=Integer.parseInt(request.getParameter("oid"));
		Order order=orderDAO.get(oid);
		order.setStatus(orderDAO.delete);
		orderDAO.update(order);
		
		return "%success";
	}
	
	public String review(HttpServletRequest request,HttpServletResponse response){
		int oid=Integer.parseInt(request.getParameter("oid"));
		Order order=orderDAO.get(oid);
		orderItemDAO.fill(order);
		Product p=order.getOrderItems().get(0).getProduct();
		List<Review> reviews=reviewDAO.list(p.getId());
		productDAO.setSaleAndReviewNumber(p);
		request.setAttribute("p", p);
		request.setAttribute("reviews", reviews);
		request.setAttribute("o", order);
		
		return "/review.jsp";
	}
	
	public String doreview(HttpServletRequest request,HttpServletResponse response){
		int oid=Integer.parseInt(request.getParameter("oid"));
		Order order=orderDAO.get(oid);
		order.setStatus(orderDAO.finish);
		orderDAO.update(order);
		int pid=Integer.parseInt(request.getParameter("pid"));
		Product p=productDAO.get(pid);
	    String content=request.getParameter("content");
	    content=HtmlUtils.htmlEscape(content);
	    User user=(User)request.getSession().getAttribute("user");
		Review review=new Review();
		review.setContent(content);
		review.setCreateDate(new Date());
		review.setProduct(p);
		review.setUser(user);
		reviewDAO.add(review);
		
		
		return "@forereview?oid="+oid+"&showonly=true";  
	}
	
}