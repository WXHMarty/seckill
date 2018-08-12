package org.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

/**
 * dao��ӿ�
 * @author Administrator
 */
public interface SeckillDao {

	/**
	 * �����
	 * @param seckillId
	 * @param killTime
	 * @return ���ص����ݱ�ʾ���ݱ��и��µ���������
	 */
	public int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
	
	/**
	 * ��ȡSeckill����
	 * @param seckillId
	 * @return
	 */
	public Seckill queryById(long seckillId);
	
	/**
	 * ����ƫ������ѯ��ɱ��Ʒ�б�
	 * @param offet
	 * @param limit
	 * @Paramע�� �󶨲���
	 * @return
	 */
	public List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
	
	/**
	 * ʹ�ô洢����ִ����ɱ
	 * @param paramMap
	 */
	public void killByProcedure(Map<String, Object> paramMap);
}
