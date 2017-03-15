package org.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

public interface SeckillDao {

	/*
	 * 减库存
	 */
	int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);
	
	/*
	 * 根据id查询秒杀商品
	 */
	Seckill queryById(long seckillId);
	
	/*
	 * 根据偏移量查询秒杀商品列表
	 * java没有保存形参的记录，queryAll(offset,limit)会被默认为queryAll(arg0,arg1),
	 * 因此可以使用@param注解的变现出实际形参
	 */
	List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
	
	/**
	 * 使用存储过程使用秒杀
	 * @param paramMap
	 */
	void killByProcedure(Map<String, Object> paramMap);
}
