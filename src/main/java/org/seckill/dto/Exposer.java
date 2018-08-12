package org.seckill.dto;
/**
 * 暴露秒杀地址DTO
 * @author Administrator
 *
 */
public class Exposer {

	//秒杀是否开启
	private boolean exposer;
	
	//一种加密措施
	private String md5;
	
	//秒杀对象的ID
	private long seckillId;
	
	//系统当前时间
	private long now;
	
	//秒杀开始时间
	private long start;
	
	//秒杀结束时间
	private long end;

	public Exposer(boolean exposer, String md5, long seckillId) {
		super();
		this.exposer = exposer;
		this.md5 = md5;
		this.seckillId = seckillId;
	}

	public Exposer(boolean exposer, long seckillId, long now, long start, long end) {
		super();
		this.exposer = exposer;
		this.seckillId = seckillId;
		this.now = now;
		this.start = start;
		this.end = end;
	}

	public Exposer(boolean exposer, long seckillId) {
		super();
		this.exposer = exposer;
		this.seckillId = seckillId;
	}

	public boolean isExposer() {
		return exposer;
	}

	public void setExposer(boolean exposer) {
		this.exposer = exposer;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Exposer [exposed=" + exposer + ", md5=" + md5 + ", seckillId="
				+ seckillId + ", now=" + now + ", start=" + start + ", end="
				+ end + "]";
	}

}
