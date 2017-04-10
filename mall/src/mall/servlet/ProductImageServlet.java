package mall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mall.Dao.ProductImageDAO;
import mall.bean.Product;
import mall.bean.ProductImage;
import mall.util.ImageUtil;
import mall.util.Page;

public class ProductImageServlet extends BaseBackServlet{

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response,
			Page page) {
		Map<String, String> params=new HashMap<String, String>();
		InputStream is=parseUpload(request, params);
		int pid=Integer.parseInt(params.get("pid"));
		Product product=productDAO.get(pid);
		String type=params.get("type");
		ProductImage bean=new ProductImage();
		bean.setType(type);
		bean.setProduct(product);
		productImageDAO.add(bean);
		int id=bean.getId();
		String path="";
		String imageFolder_small=null;
	    String imageFolder_middle=null;
		if(type==ProductImageDAO.type_single){
			 path=request.getServletContext().getRealPath("/img/productSingle");
			 imageFolder_small= request.getServletContext().getRealPath("img/productSingle_small");
	         imageFolder_middle= request.getServletContext().getRealPath("img/productSingle_middle");
		}else{
			 path=request.getServletContext().getRealPath("/img/productDetail");
		}
		File image=new File(path);
		File file=new File(image, id+".jpg");
		try {
			if(null!=is && is.available()!=0){
				try(OutputStream os=new FileOutputStream(file)) {
					byte[] bytes=new byte[1024*1024];
					int length=0;
					while((length=is.read(bytes))!=-1){
						os.write(bytes, 0, length);
					}
					os.flush();
					BufferedImage img = ImageUtil.change2jpg(file);
	                ImageIO.write(img, "jpg", file);  
	                if(ProductImageDAO.type_single.equals(bean.getType())){
                        File f_small = new File(imageFolder_small, id+".jpg");
                        File f_middle = new File(imageFolder_middle, id+".jpg");
                        ImageUtil.resizeImage(file, 56, 56, f_small);
                        ImageUtil.resizeImage(file, 217, 190, f_middle);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					is.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "@admin_productImage_list?pid="+pid;
	}

	@Override
	public String delete(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		int id=Integer.parseInt(request.getParameter("id"));
		ProductImage pi=productImageDAO.get(id);
		int pid=productImageDAO.get(id).getProduct().getId();
		productImageDAO.delete(id);
		
		if(ProductImageDAO.type_single.equals(pi.getType())){
            String imageFolder_single= request.getSession().getServletContext().getRealPath("img/productSingle");
            String imageFolder_small= request.getSession().getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle= request.getSession().getServletContext().getRealPath("img/productSingle_middle");
             
            File f_single =new File(imageFolder_single,pi.getId()+".jpg");
            f_single.delete();
            File f_small =new File(imageFolder_small,pi.getId()+".jpg");
            f_small.delete();
            File f_middle =new File(imageFolder_middle,pi.getId()+".jpg");
            f_middle.delete();
             
        }
 
        else{
            String imageFolder_detail= request.getSession().getServletContext().getRealPath("img/productDetail");
            File f_detail =new File(imageFolder_detail,pi.getId()+".jpg");
            f_detail.delete();          
        }
		
		return "@admin_productImage_list?pid="+pid;
	}

	@Override
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String update(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String list(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		int pid=Integer.parseInt(request.getParameter("pid"));
		Product p=productDAO.get(pid);
		List<ProductImage> pisDetail=
				productImageDAO.list(p, productImageDAO.type_detail, page.getStart(), page.getCount());
		List<ProductImage> pisSingle=
				productImageDAO.list(p, productImageDAO.type_single, page.getStart(), page.getCount());
		request.setAttribute("p", p);
		request.setAttribute("pisSingle", pisSingle);
		request.setAttribute("pisDetail", pisDetail);
		
		return "/admin/listProductImage.jsp";
	}

}
