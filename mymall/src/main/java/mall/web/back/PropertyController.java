package mall.web.back;

import java.util.List;

import javax.annotation.Resource;

import mall.entity.Category;
import mall.entity.Property;
import mall.service.PropertyService;
import mall.util.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/property")
public class PropertyController {

	@Resource
	PropertyService propertyService;
	
	@RequestMapping(value="/list/{cid}",method=RequestMethod.GET)
	public String list(@PathVariable("cid") int cid,Page<Property> page,Model model){
		List<Property> properties=propertyService.getByCategory(cid,page);
		Category category=new Category();
		if(properties!=null&&!properties.isEmpty())
			category=properties.get(0).getCategory();
		page.setData(properties);
		model.addAttribute("page", page);
		model.addAttribute("c", category);
		return "/admin/listProperty";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@RequestParam("name") String name,@RequestParam("cid") int cid){
		
		propertyService.add(name, cid);
		
		return "redirect: /mymall/property/list/"+cid;
	}
	
	@RequestMapping(value="/delete/{id}")
	public String delete(@PathVariable("id") int id,@RequestParam("cid") int cid){
		
		propertyService.delete(id);
		
		return "redirect: /mymall/property/list/"+cid;
	}
	
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public String edit(@PathVariable("id") int id, Model model){
		 Property property =propertyService.get(id);
		 model.addAttribute("p", property);
		return "/admin/editProperty";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(Property property,@RequestParam("cid") int cid){
		propertyService.update(property, cid);
		return "redirect: /mymall/property/list"+cid;
	}
	
}
