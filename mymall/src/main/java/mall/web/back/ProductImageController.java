package mall.web.back;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;

import mall.entity.Product;
import mall.entity.ProductImage;
import mall.service.ProductImageService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/productImage")
public class ProductImageController {

	@Resource
	ProductImageService productImageService;
	
	@RequestMapping(value="/list/{pid}",method=RequestMethod.GET)
	public String list(@PathVariable("pid") int pid,Model model){
		Product product=productImageService.getProduct(pid);
		model.addAttribute("p",product);
		return "/admin/listProductImage";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@RequestParam("filepath") MultipartFile file,HttpServletRequest request ,
			@RequestParam("type") String type,@RequestParam("pid") int pid) throws IllegalStateException, IOException{
		  if(file!=null){
			  int id=productImageService.add(pid, type);
			  if(id!=0){
				  String path=request.getServletContext().getRealPath("/img");
				  File image=new File(path);
				  if("type_single".equals(type)){
					  image=new File(image,"productSingle/"+id+".jpg");
				  }else{
					  image=new File(image,"productDetail/"+id+".jpg");
				  }
				  file.transferTo(image);
			  }
		  }
		return "redirect: /mymall/productImage/list/"+pid;
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public String delete(@PathVariable("id") int id,HttpServletRequest request){
		int pid=productImageService.delete(id,request);
		 return "redirect: /mymall/productImage/list/"+pid;  
	}
	
}
