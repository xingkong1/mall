package mall.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mall.dao.CategoryDAO;
import mall.dao.OrderItemDAO;
import mall.dao.ProductDAO;
import mall.dao.ProductImageDAO;
import mall.dao.PropertyDAO;
import mall.dao.PropertyValueDAO;
import mall.dao.ReviewDAO;
import mall.entity.Category;
import mall.entity.OrderItem;
import mall.entity.Product;
import mall.entity.ProductImage;
import mall.entity.Property;
import mall.entity.PropertyValue;
import mall.util.Page;

@Service("productService")
public class ProductService {

	@Resource
	ProductDAO productDAO;
	
	@Resource
	CategoryDAO categoryDAO;
	
	@Resource
	PropertyValueDAO propertyValueDAO;
	
	@Resource
	PropertyDAO propertyDAO;
	
	@Resource
	ProductImageDAO productImageDAO;
	
	@Resource
	ProductImageService productImageService;
	
	@Resource
	ReviewDAO reviewDAO;
	
	@Resource
	OrderItemDAO orderItemDAO;
	
	@Transactional
	public void add(Product product,int cid){
		
		Category category=categoryDAO.get(cid);
		product.setCategory(category);
		product.setCreateDate(new Date());
		  productDAO.add(product);
	}
	
	@Transactional
	public void delete(int id,HttpServletRequest request){
		Product product=productDAO.get(id);
		if(product!=null){
			productImageService.deleteByProduct(id, request);
			List<PropertyValue> propertyValues=propertyValueDAO.listByPid(id);
			for(PropertyValue propertyValue:propertyValues){
				if(propertyValue!=null)
					propertyValueDAO.delete(propertyValue.getId());
			}
		}
		productDAO.delete(id);
		
	}
	
	public Product get(int pid){
		Product product=productDAO.get(pid);
		List<ProductImage> singleImages=productImageDAO.listAll(pid,"type_single");
		List<ProductImage> detailImages=productImageDAO.listAll(pid,"type_detail");
		product.setProductDetailImages(detailImages);
		product.setProductSingleImages(singleImages);
		setFirstProductImage(product);
		setSaleAndReviewNumber(product);
		return product;
	}
	
	@Transactional
	public void update(Product product,int cid){
		Category category=categoryDAO.get(cid);
		product.setCategory(category);
		productDAO.update(product);
	}
	
	public List<PropertyValue> getPropertyValues(int id){
		Product product=productDAO.get(id);
		List<PropertyValue> propertyValues=new ArrayList<PropertyValue>();
		 List<Property> properties=propertyDAO.listBycid(product.getCategory().getId(),0, Short.MAX_VALUE);
		 List<PropertyValue> propertyValues1=propertyValueDAO.listByPid(product.getId());  
		if(product!=null&&properties.size()!=propertyValues1.size()){
			 for(Property property:properties){
				 PropertyValue propertyValue=new PropertyValue();
				 propertyValue.setProperty(property);
				 propertyValues.add(propertyValue);	 
			 }
			 for(PropertyValue propertyValue1:propertyValues1){
				 for(PropertyValue propertyValue:propertyValues){
					 if(propertyValue.getProperty().equals(propertyValue1.getProperty()))
						propertyValue.setValue(propertyValue1.getValue()); 
				 }	 
			 }
			
		}else{
			propertyValues=propertyValues1;
		}
		
		
		
		return propertyValues;
	}
	
	public boolean updatePropertyValue(int pvid,int ptid,int pid,String value){
		PropertyValue propertyValue=new PropertyValue();
		propertyValue.setValue(value);
		if(pvid!=0){
			propertyValue.setId(pvid);
			propertyValueDAO.update(propertyValue);
		}else{
			Property property=propertyDAO.get(ptid);
			Product product=productDAO.get(pid);
			propertyValue.setProperty(property);
			propertyValue.setProduct(product);
			propertyValueDAO.add(propertyValue);
		}
		
		propertyValue=propertyValueDAO.get(propertyValue.getId());
		if(propertyValue!=null&&value.equals(propertyValue.getValue())){
			return true;
		}
		return false;
	}
	
	public List<Product> listByCategory(int cid,Page<Product> page){
		
		 List<Product> products=productDAO.listByCategory(cid, page.getStart(), page.getCount());
		int total =productDAO.getTotal(cid);
		page.setTotal(total);
		 if(!products.isEmpty()){
		 for(Product product:products){
			 List<ProductImage> pImages=productImageDAO.listAll(product.getId(),"type_single");
				if(pImages!=null&&!pImages.isEmpty())
				product.setFirstProductImage(pImages.get(0));
			}
		 return products;
		 }
		 return null;
	}
	
	public void fill(List<Category> cs) {
		for(Category c:cs){
		List<Product> products=productDAO.listByCategory(c.getId(), 0, Short.MAX_VALUE);
		for(Product product:products){
			List<ProductImage> pImages=productImageDAO.listAll(product.getId(),"type_single");
			if(pImages!=null&&!pImages.isEmpty())
			product.setFirstProductImage(pImages.get(0));
		}
		c.setProducts(products);
		}
	};
	
	 
	public void setFirstProductImage(Product p){
		List<ProductImage> pImages=productImageDAO.listAll(p.getId(),"type_single");
		if(pImages!=null&&!pImages.isEmpty())
		p.setFirstProductImage(pImages.get(0));
	}

	public void fillByRow(List<Category> cs) {
     
		 int productNumberEachRow = 8;
	        for (Category c : cs) {
	            List<Product> products =  c.getProducts();
	            List<List<Product>> productsByRow =  new ArrayList<>();
	            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
	                int size = i+productNumberEachRow;
	                size= size>products.size()?products.size():size;
	                List<Product> productsOfEachRow =products.subList(i, size);
	                productsByRow.add(productsOfEachRow);
	            }
	            c.setProductsByRow(productsByRow);
	        }
	};
	
	public void setSaleAndReviewNumber(Product p){
		if(p!=null){
			int pid=p.getId();
			int saleNumber=orderItemDAO.getSaleNumber(pid);
			int reviewNumber=reviewDAO.getTotal(pid);
			p.setSaleCount(saleNumber);
			p.setReviewCount(reviewNumber);
			setFirstProductImage(p);
		}
	}
	
	public void setSaleAndReviewNumber(List<Product> products){
		for(Product product:products){
			setSaleAndReviewNumber(product);
		}
	}
	
	public List<Product> searchProducts(String keyword,Page<Product> page){
		List<Product> products=new ArrayList<Product>();
		if(keyword!=null&&!"".equals(keyword)){
		page.setCount(20);
		products =productDAO.search(keyword, page.getStart(), page.getCount());
		setSaleAndReviewNumber(products);
		page.setData(products);
		page.setTotal(products.size());
		}
		return products;
	}
	
	public boolean reduceStock(OrderItem orderItem){
		int num=orderItem.getNumber();
		Product product=productDAO.get(orderItem.getProduct().getId());
		 int stock=product.getStock();
		 if(stock>=num){
			 product.setStock(stock-num);
			 productDAO.update(product);
			 return true;
		 }else{
			 return false;
		 }
	}
}
