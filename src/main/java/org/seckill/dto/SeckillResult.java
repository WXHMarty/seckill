package org.seckill.dto;
/**
 * 用来封装json结果，所有ajax请求的返回类型都可以是SeckillResult
 * @author Administrator
 *
 * @param <T>
 */
public class SeckillResult<T> {

	private boolean success;//请求是否成功
	
	private T data;//泛型类型的数据
	
	private String error;//错误信息

	public SeckillResult(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}

	public SeckillResult(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
