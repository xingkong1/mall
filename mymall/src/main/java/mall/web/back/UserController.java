package mall.web.back;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import mall.dao.UserDAO;
import mall.entity.User;
import mall.util.Page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	UserDAO userDAO;
    
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String listUser(Page<User> page){
		int start=page.getStart();
		int count=page.getCount();
		int total=userDAO.getTotal();
		List<User> users=userDAO.list(start, count);
		page.setTotal(total);
		page.setData(users);
		return "/admin/listUser";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String addUser(User user,HttpSession session){
		userDAO.add(user);
		session.setAttribute("user", user);
		return "/registerSuccess";
	}
	
	@RequestMapping(value="/registerName")
	public @ResponseBody String registerName(String name){
		User user=userDAO.getByName(name);
		if(user==null)
			return "success";
		return "false";
	}
	
	
}
