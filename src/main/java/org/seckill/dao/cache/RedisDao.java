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
	 * 从redis中获取数据
	 * @param seckillId
	 * @return
	 */
	public Seckill getSeckill(long seckillId){
		Jedis jedis = jedisPool.getResource();
		try{
			String key = "seckill:" + seckillId;
			//没有实现内部内部序列化操作，采用自定义序列化,protostuff
			byte[] bytes = jedis.get(key.getBytes());
			//如果从缓存中获取到了数据
			if(bytes != null){
				//创建一个空对象
				Seckill seckill = schema.newMessage();
				//将schema中的数据复制到空对象seckill中,也就是反序列化
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
	 * 将数据写入到redis中
	 * @param seckill
	 * @return
	 */
	public String putSeckill(Seckill seckill){
		Jedis jedis = jedisPool.getResource();
		try{
			String key = "seckill:" + seckill.getSeckillId();
			byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
					LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			//超时缓存
			int timeout = 60 * 60;//缓存一个小时
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
