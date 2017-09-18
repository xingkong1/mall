package mall.web.fore;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.dao.ReviewDAO;
import mall.dao.UserDAO;
import mall.entity.Category;
import mall.entity.OrderItem;
import mall.entity.Product;
import mall.entity.PropertyValue;
import mall.entity.Review;
import mall.entity.User;
import mall.service.CategoryService;
import mall.service.OrderItemService;
import mall.service.ProductService;
import mall.util.Page;
import mall.util.ProductComparator;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ForeController {
	
	@Resource
	CategoryService categoryService;
	
	@Resource
	ProductService productService;
	
	@Autowired
	UserDAO userDAO;
	
	@Resource
	ReviewDAO reviewDAO;

	@RequestMapping("/forehome")
	public String home(Model model){
		List<Category> cs=categoryService.listAll();
		productService.fill(cs);
		productService.fillByRow(cs);
		model.addAttribute("cs", cs);
		return "home";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(@RequestParam("username")String username,@RequestParam("password")String password,
			@RequestParam("checkCode")String checkCode,@RequestParam(value="remember",required=false)String remember,
			HttpSession session,HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> data=new HashMap<>();
		String code=(String)session.getAttribute("validateCode");
		if(checkCode==null||"".equals(checkCode.trim())||!checkCode.equals(code)){
			data.put("msg", "checkCode_error");
			return data;
		}else{
			Subject currentUser = SecurityUtils.getSubject();
		    if (!currentUser.isAuthenticated()) {
		    	UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		        try{
		        	token.setRememberMe(true);
		            currentUser.login(token);
		        	data.put("msg", "success");
		        }catch(UnknownAccountException ex){
		        	data.put("msg", "account_error");
		        }catch(IncorrectCredentialsException ex){
		        	data.put("msg", "password_error");
		        }catch(AuthenticationException ex){
		        	data.put("msg", "authentication_error");
		        }
		    }
		    User user=userDAO.getByName(username);
		    session.setAttribute("user", user);
			//addCookie(username, password, remember, response, request);
		}
		return data;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return "forword:/login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/forehome";
	}
	
	@RequestMapping(value="/forecategory",method=RequestMethod.GET)
	public String Category(@RequestParam("cid")int cid,@RequestParam(value="sort",required=false,defaultValue="all")String sort,Model model){
		Category category=categoryService.getCategory(cid);
		Comparator<Product> comparator=ProductComparator.getComparator(sort);
		Collections.sort(category.getProducts(), comparator);
	    model.addAttribute("c", category);
		return "/category";
	}
	
	@RequestMapping(value="/foresearch")
	public String search(@RequestParam("keyword")String keyword,@RequestParam(value="sort",required=false,defaultValue="all")String sort,
			Page<Product> page,Model model){
		productService.searchProducts(keyword, page);
		Comparator<Product> comparator=ProductComparator.getComparator(sort);
		Collections.sort(page.getData(), comparator);
		model.addAttribute("keyword", keyword);
		return "/searchResult";
	}
	
	@RequestMapping(value="/foreproduct")
	public String Product(int pid,Model model){
		Product product=productService.get(pid);
		List<PropertyValue> propertyValues=productService.getPropertyValues(pid);
		List<Review> reviews=reviewDAO.listByProduct(pid, 0, Integer.MAX_VALUE);
		model.addAttribute("p", product);
		model.addAttribute("pvs", propertyValues);
		model.addAttribute("reviews", reviews);
		return "/product";
	}
	
	@RequestMapping(value="forecheckLogin",method=RequestMethod.GET)
	public @ResponseBody String checkLogin(HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user!=null){
			return "success";
		}
		return "false";
	}
	
	@RequestMapping(value="/foreloginAjax")
	public @ResponseBody String loginAjax(@RequestParam("name")String username,@RequestParam("password")String password,
			HttpSession session){
		User user=userDAO.getByUser(username, password);
		if(user!=null){
			session.setAttribute("user", user);
			return "success";
		}
		return "fasle";
	}
	
	
	private void addCookie(String username,String password,String remember,
			HttpServletResponse response,HttpServletRequest request){
		Cookie cookie1=new Cookie("username", username);
		Cookie cookie2=new Cookie("password", password);
		cookie1.setPath(request.getContextPath()+"/login.jsp");
		cookie2.setPath(request.getContextPath()+"/login.jsp");
		if(remember.equals("true")){
			cookie1.setMaxAge(7*60*60*24);
			cookie2.setMaxAge(7*60*60*24);
		}else{
			cookie1.setMaxAge(0);
			cookie2.setMaxAge(0);
		}
		response.addCookie(cookie1);
		response.addCookie(cookie2);
	}
	
	
	
	
}
