package mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import mall.entity.Category;
import mall.entity.Product;

@Repository
public interface ProductDAO {

	public int getTotal(int cid);
	
	public void add(Product product);

	public void delete(int id);

	public void update(Product product);

	public Product get(int id);

	public List<Product> listByCategory(@Param("cid") int cid,@Param("start") int start,@Param("count") int count);
	
	public List<Product> listProducts(@Param("start") int start, @Param("count") int count);
	
	public void setFirstProductImage(Product p);
	
	public List<Product> search(@Param("keyword") String keyword,@Param("start") int start,@Param("count") int count);
	
	
	
	
}
