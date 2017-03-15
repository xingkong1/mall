package org.seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * 业务层实现类
 * @author Lenovo
 *
 */
@Service
public class SeckillServiceImpl implements SeckillService{

	@Resource
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Autowired
	private RedisDao redisDao;
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	//md5字符串，用于混淆MD5
	private final String slat="skjkjhkghjjksjkhfjkk&&&##s444";
	
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 5);
	}

	
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		//缓存优化
		//访问redis
		Seckill seckill=redisDao.getSeckill(seckillId);
		if(seckill==null){
			//访问数据库
			seckill=seckillDao.queryById(seckillId);
			if(seckill==null){
				return new Exposer(false, seckillId);
			}else{
				//放入redis
				redisDao.putSeckill(seckill);
			}
			
		}
		Date startTime=seckill.getStartTime();
		Date endTime=seckill.getEndTime();
		Date nowTime=new Date();
		if(nowTime.getTime()<startTime.getTime()||
		   nowTime.getTime()>endTime.getTime()){
			return new Exposer(false, seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
		}
		//转化特定字符串的过程，不可逆
		String md5=getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}
	
	private String getMD5(long seckillId){
		String base=seckillId+"/"+slat;
		String md5=DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if(md5==null||!md5.equals(getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite");
		}
		//执行秒杀逻辑：减库存+记录购买行为
		Date nowTime=new Date();
		try {
			//记录购买行为
			int insertCount=successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if(insertCount<=0){
				throw new RepeatKillException("seckill repeated");
			}else{
				//秒杀成功
				int updateCount=seckillDao.reduceNumber(seckillId, nowTime);
				if(updateCount<=0){
					//没有跟新到记录，秒杀结束
					throw new SeckillCloseException("seckill is closed");
				}else{
					SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS, successKilled);
				}
				
			}
			
			
		}catch (SeckillCloseException e1) {
			throw e1;
		}catch (RepeatKillException e2) {
			throw e2;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			//所以编译期异常，转化为运行期异常
			throw new SeckillException("seckill inner error"+e.getMessage());
		}
	
	}

    /**
     * 执行存储过程的秒杀方法
     */
	public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5)
    {
		if(md5==null||!md5.equals(getMD5(seckillId))){
			return new SeckillExecution(seckillId, SeckillStateEnum.DATA_REWRITE);
		}
		Date nowTime=new Date();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", nowTime);
		map.put("result", null);
		//执行存储过程，result被赋值
		try {
			seckillDao.killByProcedure(map);
			int result=MapUtils.getInteger(map, "result",-2);
			if(result==1){
				SuccessKilled sk=successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, sk);
			}else{
				return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
		}
		
	}

}
