package cn.edu.nuc.onlinestore.service;

import java.net.Socket;

import cn.edu.nuc.onlinestore.io.IOUtility;
import cn.edu.nuc.onlinestore.vo.Request;

/**
 * 登录注册相关逻辑
 * @author 王凯
 *
 */
public class LoginRegisterService {
	
	/**
	 * 客户端socket
	 */
	private Socket client;
	
	public LoginRegisterService(Socket client) {
		this.client = client;
	}
	
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @throws Exception 登录过程中出错
	 */
	public void userLogin(String username, String password) throws Exception {
		Request request = new Request();
		request.setType(Request.LOGIN_MESSAGE);
		request.setUsername(username);
		request.setPassword(password);
		IOUtility.persistObjectNoClose(request, client.getOutputStream());
	}
	
	/**
	 * 用户注册
	 * @param username 用户名
	 * @param password 密码
	 * @throws Exception 注册过程中出错
	 */
	public void userRegister(String username, String password) throws Exception {
		System.out.println(client.getOutputStream());
		Request request = new Request();
		request.setType(Request.REGISTER_MESSAGE);
		request.setUsername(username);
		request.setPassword(password);
		IOUtility.persistObjectNoClose(request, client.getOutputStream());
	}
}
