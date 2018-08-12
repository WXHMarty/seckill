package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * 秒杀成功类DAO层
 * @author Administrator
 */
public interface SuccessKilledDao {

	/**插入购买明细,可过滤重复
	 * @param seckillId
	 * @param userPhone
	 * @return 返回的数据表示数据表中更新的数据条数
	 */
	public int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	
	/**
	 * 根据id查询SuccessKilled并携带秒杀对象实体
	 * @param seckillId
	 * @return
	 */
	public SuccessKilled queryByIdWithSeckillId(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
