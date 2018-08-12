package org.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

/**
 * dao层接口
 * @author Administrator
 */
public interface SeckillDao {

	/**
	 * 减库存
	 * @param seckillId
	 * @param killTime
	 * @return 返回的数据表示数据表中更新的数据条数
	 */
	public int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
	
	/**
	 * 获取Seckill数据
	 * @param seckillId
	 * @return
	 */
	public Seckill queryById(long seckillId);
	
	/**
	 * 根据偏移量查询秒杀商品列表
	 * @param offet
	 * @param limit
	 * @Param注解 绑定参数
	 * @return
	 */
	public List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
	
	/**
	 * 使用存储过程执行秒杀
	 * @param paramMap
	 */
	public void killByProcedure(Map<String, Object> paramMap);
}
