package mall.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mall.dao.CategoryDAO;
import mall.dao.ProductDAO;
import mall.entity.Category;
import mall.entity.Product;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service("categoryService")
public class CategoryService {

	@Resource
	CategoryDAO categoryDAO;
	
	@Resource
	ProductDAO productDAO;
	
	@Resource
	ProductService productService;
	
	public void add(Category category){
		if(category!=null){
			categoryDAO.add(category);
		}
	};

	public void delete(int id){;
	   categoryDAO.delete(id);
	}

	public void update(Category category){
		if(category!=null){
			categoryDAO.update(category);
		}
	};

	public Category get(int id){
		Category category=categoryDAO.get(id);
		return category;
	};
	
	public Category getCategory(int id){
		Category category=categoryDAO.get(id);
		if(category!=null){
		 List<Product> products=productDAO.listByCategory(id, 0, Short.MAX_VALUE);
		 productService.setSaleAndReviewNumber(products);
		 category.setProducts(products);
		}
		return category;
	}

	public List<Category> listAll(){;
	    return categoryDAO.listAll();
	}

	public List<Category> list(int start, int count){
		return categoryDAO.list(start, count);
	};
	
	public int getTotal(){
		return categoryDAO.getTotal();
	}
	
	public List<Category> search(String key,int start, int count){
		if(key!=null && key!="")
		return categoryDAO.search(key, start, count);
		return new ArrayList<Category>();
	}

}
