package org.seckill.dao.cache;

import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String s = "dsd";
	private final JedisPool jedisPool;
	
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	
	public RedisDao(String ip, int port){
		jedisPool = new JedisPool(ip, port);
	}
	
	/**
	 * ��redis�л�ȡ����
	 * @param seckillId
	 * @return
	 */
	public Seckill getSeckill(long seckillId){
		Jedis jedis = jedisPool.getResource();
		try{
			String key = "seckill:" + seckillId;
			//û��ʵ���ڲ��ڲ����л������������Զ������л�,protostuff
			byte[] bytes = jedis.get(key.getBytes());
			//����ӻ����л�ȡ��������
			if(bytes != null){
				//����һ���ն���
				Seckill seckill = schema.newMessage();
				//��schema�е����ݸ��Ƶ��ն���seckill��,Ҳ���Ƿ����л�
				ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
				return seckill;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}finally{
			jedis.close();
		}
		return null;
	}
	
	/**
	 * ������д�뵽redis��
	 * @param seckill
	 * @return
	 */
	public String putSeckill(Seckill seckill){
		Jedis jedis = jedisPool.getResource();
		try{
			String key = "seckill:" + seckill.getSeckillId();
			byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
					LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			//��ʱ����
			int timeout = 60 * 60;//����һ��Сʱ
			String result = jedis.setex(key.getBytes(), timeout, bytes);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}finally{
			jedis.close();
		}
		return null;
	}
}
