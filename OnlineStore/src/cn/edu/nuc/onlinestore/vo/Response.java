package cn.edu.nuc.onlinestore.vo;

import java.io.Serializable;

/**
 * 服务器反馈给客户端的结果信息
 * 
 * @author 王凯
 *
 * 
 */
public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1301418053396888480L;
	/**
	 * 登录消息
	 */
	public static final int LOGIN_MESSAGE = 0;

	/**
	 * 注册消息
	 */
	public static final int REGISTER_MESSAGE = 1;

	/**
	 * 检索商品消息
	 */
	public static final int SEARCH_MESSAGE = 2;

	/**
	 * 下线消息
	 */
	public static final int OFFLINE_MESSAGE = 3;

	/**
	 * 结账消息
	 */
	public static final int PAY_MESSAGE = 4;

	/**
	 * 消息类型
	 */
	private int messageType;

	/**
	 * 反馈结果（成功，失败）
	 */
	private boolean result;

	/**
	 * 服务器端给客户端发的消息
	 */
	private String message;

	/**
	 * 传输的对象
	 */
	private Object obj;

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Response [messageType=" + messageType + ", result=" + result
				+ ", message=" + message + ", obj=" + obj + "]";
	}

	
}
