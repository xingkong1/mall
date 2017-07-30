package mall.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HelloWorldController {
    @RequestMapping("/user")
	public String hello(Model model){
		model.addAttribute("name", "yang");
		return "user";
	}
}
