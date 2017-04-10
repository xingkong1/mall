package mall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mall.bean.Category;
import mall.util.ImageUtil;
import mall.util.Page;

public class CategoryServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response,
			Page page) {
		Map<String, String> params=new HashMap<>();
		InputStream is=parseUpload(request, params);
		String name=params.get("name");
		Category category=new Category();
		category.setName(name);
		categoryDAO.add(category);
		int id=category.getId();
		String path=request.getServletContext().getRealPath("/img/category");
		File image=new File(path);
		File file=new File(image, id+".jpg");
		try {
			if(null!=is && 0!=is.available()){
			try(FileOutputStream fos=new FileOutputStream(file)) {
				byte[] bytes=new byte[1024*1024];
				int length=0;
				while((length=is.read(bytes))!=-1){
					fos.write(bytes, 0, length);
				}
				fos.flush();
				//通过如下代码，把文件保存为jpg格式
                BufferedImage img = ImageUtil.change2jpg(file);
                ImageIO.write(img, "jpg", file);  
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				is.close();
			}	
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "@admin_category_list";
	}

	@Override
	public String delete(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		String i=request.getParameter("id");
		if(i!=null){
			int id=Integer.valueOf(i);
			categoryDAO.delete(id);
		}
		
		return "@admin_category_list";
	}

	@Override
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		String i=request.getParameter("id");
		if(i!=null){
			int id=Integer.valueOf(i);
			Category category=categoryDAO.get(id);
			request.setAttribute("c", category);
			return "/admin/editCategory.jsp";
		}
		return "@admin_category_list";
	}

	@Override
	public String update(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		Map<String, String> params=new HashMap<>();
		InputStream is=parseUpload(request, params);
		String name=params.get("name");
		String id=params.get("id");
		Category category=new Category();
		category.setName(name);
		category.setId(Integer.valueOf(id));
		categoryDAO.update(category);
		String path=request.getServletContext().getRealPath("/img/category");
		File image=new File(path);
		File file=new File(image, id+".jpg");
		try {
			if(null!=is && 0!=is.available()){
			try(FileOutputStream fos=new FileOutputStream(file)) {
				byte[] bytes=new byte[1024*1024];
				int length=0;
				while((length=is.read(bytes))!=-1){
					fos.write(bytes, 0, length);
				}
				fos.flush();
				//通过如下代码，把文件保存为jpg格式
                BufferedImage img = ImageUtil.change2jpg(file);
                ImageIO.write(img, "jpg", file);  
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				is.close();
			}	
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "@admin_category_list";
	}

	@Override
	public String list(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		List<Category> list=categoryDAO.list(page.getStart(), page.getCount());
		int total=categoryDAO.getTotal();
		page.setTotal(total);
		request.setAttribute("thecs", list);
		request.setAttribute("page", page);
		return "/admin/listCategory.jsp";
	}

}
