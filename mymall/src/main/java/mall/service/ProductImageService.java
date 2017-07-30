package mall.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import mall.dao.ProductDAO;
import mall.dao.ProductImageDAO;
import mall.entity.Product;
import mall.entity.ProductImage;

import org.springframework.stereotype.Service;

@Service("productImageService")
public class ProductImageService {
	
	
	@Resource
	ProductImageDAO productImageDAO;
	
	@Resource
	ProductDAO productDAO;
	
	public List<ProductImage> list(int pid,String type){
		
	 return	productImageDAO.listAll(pid, type);
	 
	}
	
	public int add(int pid,String type){
		Product product=productDAO.get(pid);
		if(product!=null){
			ProductImage productImage=new ProductImage();
			productImage.setProduct(product);
			productImage.setType(type);
			productImageDAO.add(productImage);
			int id=productImage.getId();
			return id;
		}
		return 0;
	}
	
	public boolean deleteByProduct(int pid,HttpServletRequest request){
		Product product=getProduct(pid);
		if(product!=null){
			List<ProductImage> productImages=product.getProductImages();
			if(!productImages.isEmpty()){
			for(ProductImage productImage:productImages){
				int row=delete(productImage.getId(), request);
				if(row==0)
					return false;
			}
			}
		}
		return true;
	}
	
	public int  delete(int id,HttpServletRequest request){
		ProductImage productImage=productImageDAO.get(id);
		if(productImage!=null){
			productImageDAO.delete(id);
			String type=productImage.getType();
			String path=request.getServletContext().getRealPath("/img");
			File image=new File(path);
			  if("type_single".equals(type)){
				  image=new File(image,"productSingle/"+id+".jpg");
			  }else{
				  image=new File(image,"productDetail/"+id+".jpg");
			  }
			  image.delete();
			  Product product=productImage.getProduct();
			  if(product!=null)
		  return product.getId();
		}
		return 0;
	}
	
	public Product getProduct(int pid){
		  Product product=productDAO.get(pid);
		  if(product!=null){
			  List<ProductImage> pisSingle=productImageDAO.listAll(pid, "type_single");
			  List<ProductImage>  pisDetail=productImageDAO.listAll(pid, "type_detail");
			  List<ProductImage> productImages=new ArrayList<ProductImage>();
			  productImages.addAll(pisSingle);
			  productImages.addAll(pisDetail);
			  product.setProductImages(productImages);
			  product.setProductDetailImages(pisDetail);
			  product.setProductSingleImages(pisSingle);
		  }
		return product;
	}
}
