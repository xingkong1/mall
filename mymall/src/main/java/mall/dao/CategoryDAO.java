package mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import mall.entity.Category;

@Repository
public interface CategoryDAO {

	public void add(Category bean);

	public void delete(int id);

	public void update(Category bean);

	public Category get(int id);

	public List<Category> listAll();

	public List<Category> list(@Param("start") int start, @Param("count") int count);

	public int getTotal();
	
	public List<Category> search(@Param("key")String key,@Param("start") int start, @Param("count") int count);
}
