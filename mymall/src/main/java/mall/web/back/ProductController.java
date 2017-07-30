package mall.web.back;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import mall.entity.Category;
import mall.entity.Product;
import mall.entity.PropertyValue;
import mall.service.ProductService;
import mall.util.Page;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Resource
	ProductService productService;
	
	
	@RequestMapping(value="/list/{cid}",method=RequestMethod.GET)
	public String listByCid(@PathVariable(value="cid")String cid,Page<Product> page, Model model){
		int id=Integer.parseInt(cid);
		List<Product> products=productService.listByCategory(id, page);
		page.setData(products);
		Category category=products.get(0).getCategory();
		model.addAttribute("page", page);
		model.addAttribute("c", category);
		return "/admin/listProduct";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid Product product,Errors errors,@RequestParam("cid") String cid ){
		if(errors.hasErrors()){
			return "/WEB-INF/pages/error/error";
		}
		productService.add(product, Integer.parseInt(cid));
		return "redirect: /mymall/product/list/"+cid;
	}
	
	@RequestMapping(value="/delete/{pid}",method=RequestMethod.GET)
	public String delete(@NotNull @PathVariable("pid")int pid,@RequestParam("cid") int cid,HttpServletRequest request){
		productService.delete(pid,request);
		return "redirect: /mymall/product/list/"+cid;
	}
	
	@RequestMapping(value="/edit/{pid}",method=RequestMethod.GET)
	public String edit(@NotEmpty @PathVariable("pid") int pid,Model model){
		Product product=productService.get(pid);
		model.addAttribute("p", product);
		return "/admin/editProduct";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(@Valid Product product,Errors errors,@RequestParam("cid") int cid){
		if(errors.hasErrors()){
			return "/WEB-INF/pages/error/error";
		}
		productService.update(product, cid);
		return "redirect: /mymall/product/list/"+cid;
	}
	
	@RequestMapping(value="/editPropertyValue/{id}",method=RequestMethod.GET)
	public String editPropertyValue(@NotEmpty @PathVariable("id") int id,Model model){
		Product product=productService.get(id);
		List<PropertyValue> propertyValues=productService.getPropertyValues(id);
		model.addAttribute("p", product);
		model.addAttribute("pvs", propertyValues);
		return "/admin/editProductValue";
	}
	
	@RequestMapping(value="/updatePropertyValue",method=RequestMethod.POST)
	@ResponseBody
	public String updatePropertyValue(@RequestParam("value") String value,
			@RequestParam(value="pvid",required=false) int pvid,
			@RequestParam(value="ptid",required=false) int ptid,
			@RequestParam(value="pid",required=false) int pid
			){
		if(productService.updatePropertyValue(pvid,ptid, pid ,value)){
			return "success";
		}
			
		return "false";
	}
	
	
}
