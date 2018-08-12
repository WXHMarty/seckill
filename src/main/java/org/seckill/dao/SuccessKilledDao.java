package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * ��ɱ�ɹ���DAO��
 * @author Administrator
 */
public interface SuccessKilledDao {

	/**���빺����ϸ,�ɹ����ظ�
	 * @param seckillId
	 * @param userPhone
	 * @return ���ص����ݱ�ʾ���ݱ��и��µ���������
	 */
	public int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	
	/**
	 * ����id��ѯSuccessKilled��Я����ɱ����ʵ��
	 * @param seckillId
	 * @return
	 */
	public SuccessKilled queryByIdWithSeckillId(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
