package mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import mall.entity.Product;
import mall.entity.ProductImage;

@Repository
public interface ProductImageDAO  {


	public void add(ProductImage pi);

	public void delete(int id);

	public void update(ProductImage pi);

	public ProductImage get(int id);

	public int getTotal();
	
    public List<ProductImage> listAll(@Param("pid") int pid, @Param("type") String type) ;
    
  
    public List<ProductImage> list(@Param("pid") int pid, @Param("type")String type, @Param("start") int start,@Param("count") int count) ;
}
