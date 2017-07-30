package mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import mall.entity.Property;

@Repository
public interface PropertyDAO {
 
	public void add(Property bean);

	public void delete(int id);

	public void update(Property bean);

	public Property get(int id);

	public List<Property> listAll();

	public List<Property> list(@Param("start") int start, @Param("count") int count);
	
	public int getTotal(int cid);
	
	public List<Property> listBycid(@Param("cid")int cid,@Param("start") int start,@Param("count")int count);
	
	
}
