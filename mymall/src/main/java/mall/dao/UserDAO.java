package mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import mall.entity.User;

@Repository
public interface UserDAO {

	public void add(User bean);

	public void delete(int id);

	public void update(User bean);

	public User get(int id);

	public List<User> listAll();

	public List<User> list(@Param("start") int start, @Param("count") int count);

	public int getTotal();
	
	public User getByName(String name);
	
	public User getByUser(@Param("name") String name,@Param("password") String password);
}
