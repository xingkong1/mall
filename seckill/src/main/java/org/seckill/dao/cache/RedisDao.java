package org.seckill.dao.cache;

import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis数据缓存操作层
 * @author Lenovo
 *
 */
public class RedisDao {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	private JedisPool jedisPool;

	private RedisDao(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}
	
	private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);

	public Seckill getSeckill(long seckillId) {
		//redis操作逻辑
		try {
			Jedis jedis=jedisPool.getResource();
			try {
				String key="seckill:"+seckillId;
				//redis没有实现内部序列化操作,自定义序列化
				//get-->byte[]-->反序列化-->object(Seckill)
				byte[] bytes=jedis.get(key.getBytes());
				if(bytes!=null){
					Seckill seckill=schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill,schema);
					//seckill被反序列化
					return seckill;
				}
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	public String putSeckill(Seckill seckill) {
        //set object(Seckill)-->序列化-->byte[]
		try {
			Jedis jedis=jedisPool.getResource();
			try {
				String key="seckill:"+seckill.getSeckillId();
				byte[] bytes=ProtostuffIOUtil.toByteArray(seckill, schema,
				LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeout=60*60;
				String result=jedis.setex(key.getBytes(),timeout, bytes);
				return result;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
}
