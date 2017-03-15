package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

/*
 * ҵ���߼��ӿ�
 */
public interface SeckillService {

	/*
	 * ��ѯ���е���ɱ��¼
	 */
	List<Seckill> getSeckillList();
	
	/*
	 * ��ѯ������ɱ��¼
	 */
	Seckill getById(long seckillId);
	
	/*
	 * ��ɱ����ʱ�����ɱ�ӿڵ�ַ��
	 * �������ϵͳʱ�����ɱʱ��
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * ִ����ɱ����
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
	throws SeckillException,RepeatKillException,SeckillCloseException;
	
	/**
	 * ִ����ɱ���� ʹ�ô洢����
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckillProcedure(long seckillId,long userPhone,String md5)
	throws SeckillException,RepeatKillException,SeckillCloseException;
	
}