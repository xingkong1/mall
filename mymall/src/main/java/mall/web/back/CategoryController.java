package mall.web.back;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import mall.entity.Category;
import mall.service.CategoryService;
import mall.util.ImageUtil;
import mall.util.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Resource
	private CategoryService categoryService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Page<Category> page){
		int start=page.getStart();
		int count=page.getCount();
		int total=categoryService.getTotal();
		List<Category> categories=categoryService.list(start, count);
		page.setData(categories);
		page.setTotal(total);
		return "/admin/listCategory";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@RequestParam("name")String name,@RequestParam("filepath")MultipartFile file,HttpServletRequest request){
		
		Category category=new Category();
		category.setName(name);
		categoryService.add(category);
		int id=category.getId();
		String path=request.getServletContext().getRealPath("/img/category");
		File image=new File(path,id+".jpg");
		
		try {
			file.transferTo(image);
		} catch (IllegalStateException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return "redirect:/category/list";
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public String delete(@Valid @PathVariable("id")String id,HttpServletRequest request){
		
		categoryService.delete(Integer.parseInt(id));
		String path=request.getServletContext().getRealPath("/img/category");
		File image=new File(path,id+".jpg");
		image.delete();
		return "redirect:/category/list";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable("id")String id,Model model){
		Category category=categoryService.get(Integer.parseInt(id));
		if(category!=null){
			model.addAttribute("c", category);
			return "/admin/editCategory";
		}
		return "redirect:/category/list";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(Category category,@RequestParam("filepath")MultipartFile file,HttpServletRequest request){
		
		categoryService.update(category);
		int id=category.getId();
		String path=request.getServletContext().getRealPath("/img/category");
		File image=new File(path,id+".jpg");
		
		try {
			file.transferTo(image);
		} catch (IllegalStateException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return "redirect:/category/list";
	}
	
	@RequestMapping(value="/search")
	public String search(String key,Model model){
		Page<Category> page=new Page<>();
		List<Category> categories=categoryService.search(key, page.getStart(), page.getCount());
		page.setData(categories);
		model.addAttribute("page", page);
		return "/admin/listCategory";
	}
}
