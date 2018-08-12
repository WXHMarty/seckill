package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

/**
 * seckillҵ��ӿ�:վ��ʹ���ߵĽǶ�������ƽӿ�
 * 
 * @author Administrator
 * 
 */
public interface SeckillService {

	/**
	 * ��ѯ������ɱ��¼
	 * 
	 * @return
	 */
	public List<Seckill> getSeckillList();

	/**
	 * ��ѯ������ɱ��¼
	 * 
	 * @param seckillId
	 * @return
	 */
	public Seckill getSeckillById(long seckillId);

	/**
	 * ��ɱ�����������ɱ�ӿڵ�ַ�� �������ϵͳʱ�����ɱʱ��
	 * 
	 * @param seckillId
	 * @return
	 */
	public Exposer exportSeckillUrl(long seckillId);

	/**
	 * ִ����ɱ����
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException;
	
	/**
	 * ͨ���洢����ִ����ɱ����
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);
}
