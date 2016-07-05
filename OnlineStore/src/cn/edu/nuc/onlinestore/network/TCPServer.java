package cn.edu.nuc.onlinestore.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import cn.edu.nuc.onlinestore.io.IOUtility;
import cn.edu.nuc.onlinestore.model.Goods;
import cn.edu.nuc.onlinestore.model.User;
import cn.edu.nuc.onlinestore.service.LoginRegisterService;
import cn.edu.nuc.onlinestore.vo.Request;
import cn.edu.nuc.onlinestore.vo.Response;

/**
 * 服务器线程
 * @author 王凯
 *
 */
public class TCPServer extends Thread{
	
	/**
	 * 服务器socket
	 */
	private ServerSocket server = null;
	
	/**
	 * 服务器是否打开的标志
	 */
	private volatile boolean flag = false;
	
	/**
	 * 在线用户列表
	 */
	private volatile Map<String, Socket> onlineClient = new HashMap<String, Socket>();
	
	/**
	 * 标记用户上线数量的标签
	 */
	private JLabel onlineCountLabel;
	
	/**
	 * 初始化服务器
	 */
	public TCPServer(JLabel label) {
		try {
			server = new ServerSocket(8888); //开启8888端口进行监听
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.onlineCountLabel = label;
	}
	
	@Override
	public void run() {
		Socket client = null;
		InputStream in = null;
		flag = true;
		System.out.println("服务器成功启动，准备接受用户的请求...");
		while (flag) {
			try {
				//阻塞等待客户端连接
				client = server.accept();
				in = client.getInputStream();
				Request clientMessage = (Request)IOUtility.getObject(in);
				
				switch (clientMessage.getType()) {
					case Request.LOGIN_MESSAGE:   //登录操作
						userLogin(client, clientMessage, onlineCountLabel);
						
						break;
					case Request.REGISTER_MESSAGE://注册操作
						userRegister(client, clientMessage);
						break;
					case Request.OFFLINE_MESSAGE: //离线操作
						onlineClient.get(clientMessage).close();          //关闭客户端连接
						onlineClient.remove(clientMessage.getUsername()); //从在线列表中移除
						break;
					case Request.SEARCH_MESSAGE:  //检索操作
						searchGoodsByName(client, clientMessage);
						break;
					case Request.PAY_MESSAGE:     //结账操作
						;
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 按名称检索商品
	 * @param client 客户的Socket对象
	 * @param clientMessage 从客户端发来的请求消息
	 * @throws Exception 给客户端发送信息失败抛出异常
	 */
	private void searchGoodsByName(Socket client, Request clientMessage)
			throws IOException {
		String goodsName = clientMessage.getGoodsname();
		List<Goods> goodslist = IOUtility.getGoodsByName(goodsName);
		Response response = new Response();
		response.setResult(true);
		response.setMessageType(Response.SEARCH_MESSAGE);
		response.setObj(goodslist);
		IOUtility.persistObject(response, client.getOutputStream());
	}

	/**
	 * 用户注册
	 * @param client 客户的Socket对象
	 * @param clientMessage 从客户端发来的请求消息
	 * @throws Exception 给客户端发送信息失败抛出异常
	 */
	private void userRegister(Socket client, Request clientMessage)
			throws Exception {
		User user = new User();
		user.setUserid(IOUtility.getRegisteredUserCount() + 1);
		user.setUsername(clientMessage.getUsername());
		user.setPassword(clientMessage.getPassword());
		IOUtility.writeUserToFile(user);
		Response response = new Response();
		response.setResult(true);
		response.setMessageType(Response.REGISTER_MESSAGE);
		IOUtility.persistObject(response, client.getOutputStream());
	}

	/**
	 * 用户登录
	 * @param client 客户的Socket对象
	 * @param clientMessage 从客户端发来的请求消息
	 * @throws Exception 给客户端发送信息失败抛出异常
	 */
	private void userLogin(Socket client, Request clientMessage, JLabel label)
			throws Exception{
		boolean flag = LoginRegisterService.userLoginValidate(
				clientMessage.getUsername(), clientMessage.getPassword());
		Response response = new Response();
		if (flag == true) { //验证通过
			onlineClient.put(clientMessage.getUsername(), client); //加入在线用户列表
			label.setText("当前在线用户数: " + onlineClient.size());
			label.updateUI();  //更新界面显示
		}
		response.setResult(flag);
		response.setMessageType(Response.LOGIN_MESSAGE);
		IOUtility.persistObject(response, client.getOutputStream());
	}
	
	/**
	 * 获得当前在线的用户数
	 * @return 当前在线的用户数
	 */
	public int getOnlineUserCount() {
		return onlineClient.size();
	}
	
	/**
	 * 停掉服务器
	 */
	public void stopServer() {
		flag = false;
	}
}
