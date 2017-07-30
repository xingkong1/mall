package mall.dao;

import java.util.List;

import mall.entity.Order;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO {

	public void add(Order bean);

	public void delete(int id);

	public void update(Order bean);

	public Order get(int id);

	public List<Order> listOrders(@Param("start") int start, @Param("count") int count);
	
	public List<Order> listOrdersByUser(@Param("uid")int uid,
			@Param("excludedStatus")String excludedStatus,@Param("start") int start,@Param("count") int count);

	public int getTotal();
}
