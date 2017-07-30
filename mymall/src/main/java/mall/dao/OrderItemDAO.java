package mall.dao;

import java.util.List;

import mall.entity.OrderItem;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDAO {
      
	public void add(OrderItem bean);

	public void delete(int id);

	public void update(OrderItem bean);

	public OrderItem get(int id);

	public List<OrderItem> listByUser(@Param("uid")int uid,@Param("start") int start, @Param("count") int count);

	public List<OrderItem> listByOrder(@Param("oid")int oid,@Param("start") int start, @Param("count") int count);
	
	public List<OrderItem> listByProduct(@Param("pid")int pid,@Param("start") int start, @Param("count") int count);

	public int getTotal();
	
	public int getSaleNumber(int pid);
}
