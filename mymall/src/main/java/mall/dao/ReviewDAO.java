package mall.dao;

import java.util.List;

import mall.entity.Review;

import org.apache.ibatis.annotations.Param;

public interface ReviewDAO {
	
	public void add(Review bean);

	public void delete(int id);

	public void update(Review bean);

	public Review get(int id);

	public List<Review> listByProduct(@Param("pid")int pid,@Param("start") int start, @Param("count") int count);

	public List<Review> listByUser(@Param("uid")int uid,@Param("start") int start, @Param("count") int count);
	
	public int getTotal(int pid);
}
