package mall.dao;

import java.util.List;

import mall.entity.PropertyValue;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyValueDAO {

	public void add(PropertyValue bean);

	public void delete(int id);

	public void update(PropertyValue bean);

	public PropertyValue get(int id);
	
	public PropertyValue getByValue(@Param("ptid")int ptid,@Param("pid")int pid);

	public List<PropertyValue> listByPid(int pid);

	public List<PropertyValue> list(@Param("start") int start, @Param("count") int count);

	public int getTotal();
}
