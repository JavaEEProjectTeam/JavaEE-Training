package cn.edu.nuc.onlinestore.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;

import cn.edu.nuc.onlinestore.io.IOUtility;
import cn.edu.nuc.onlinestore.model.Cart;
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
	private volatile List<Socket> onlineClient = new ArrayList<Socket>();
	
	/**
	 * 标记用户上线数量的标签
	 */
	private JLabel onlineCountLabel;
	
	/**
	 * 初始化服务器
	 * @param label 标签
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
		flag = true;
		System.out.println("服务器成功启动，准备接受用户的请求...");
		while (flag) {
			try {
				//阻塞等待客户端连接
				client = server.accept();
				System.out.println("收到" + client + "的请求！");
				new ClientThread(client).start(); //new新线程处理客户端请求
			} catch(EOFException e) {
				//读到尾了，不管他
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * 用户结算购物车，执行支付操作
	 * @param client 客户socket
	 * @param request 客户端请求
	 * @throws Exception 向客户端发送消息失败
	 */
	private void userPay(Socket client, Request request) throws Exception {
		//检查库存量是否满足供给量
		StringBuffer message = new StringBuffer();
		boolean flag = true;
		Cart cart = request.getCart();
		Map<Goods, Integer> shoppingCart = cart.getShoppingCart();
		Set<Goods> keyset = shoppingCart.keySet();
		List<Goods> goodsList = IOUtility.getAllGoods();
		for (Goods servergoods : goodsList) {
			for (Goods usergoods : keyset) {
				if (usergoods.getGid() == servergoods.getGid()) {
					if (servergoods.getInventory() < shoppingCart.get(usergoods)) { //用库存量和购买量作比较
						flag = false;
						message.append("货品" + servergoods.getGoodsName() + "现在的库存为"
								+ servergoods.getInventory() + ",数量不足！");
					}
				}
			}
		}
		if (flag == false) { //库存不够，告知用户要求其减少数量
			message.append("购买失败，请调整购物车的商品数量后再试！");
		}
		else { //库存没问题，可以出货
			//减少库存
			for (Goods servergoods : goodsList) {
				for (Goods usergoods : keyset) {
					if (usergoods.getGid() == servergoods.getGid()) {  //库存减去购买数量
						servergoods.setInventory(
								servergoods.getInventory() - shoppingCart.get(usergoods));
						IOUtility.writeGoodsToFile(servergoods);  //将修改后的库存量同步到文件
					}
				}
			}
			
			message.append("购买成功，您本次消费" + cart.getTotalPrice() + "元！祝您购物愉快！");
		}

		//向客户端发消息
		Response response = new Response();
		response.setMessageType(Response.PAY_MESSAGE);
		response.setResult(flag);
		response.setMessage(message.toString());
		IOUtility.persistObjectNoClose(response, client.getOutputStream());
	}

	/**
	 * 用户下线
	 * @param request 向客户端发送的消息
	 * @throws Exception 向客户端发送消息的过程中出现异常
	 */
	private void userOffline(Socket client, Request request, JLabel label) 
			throws Exception {
		onlineClient.remove(client);
		Response response = new Response();
		response.setMessageType(Response.OFFLINE_MESSAGE);
		response.setResult(true);
		IOUtility.persistObjectNoClose(response, client.getOutputStream());
		client.close(); //关闭客户端连接
		label.setText("当前在线用户数: " + onlineClient.size());
		label.updateUI();  //更新界面显示
	}

	/**
	 * 按名称检索商品
	 * @param client 客户的Socket对象
	 * @param request 从客户端发来的请求消息
	 * @throws Exception 给客户端发送信息失败抛出异常
	 */
	private void searchGoodsByName(Socket client, Request request)
			throws IOException {
		String goodsName = request.getGoodsname();
		List<Goods> goodslist = IOUtility.getGoodsByName(goodsName);
		Response response = new Response();
		response.setResult(true);
		response.setMessageType(Response.SEARCH_MESSAGE);
		response.setObj(goodslist);
		IOUtility.persistObjectNoClose(response, client.getOutputStream());
	}

	/**
	 * 用户注册
	 * @param client 客户的Socket对象
	 * @param request 从客户端发来的请求消息
	 * @throws Exception 给客户端发送信息失败抛出异常
	 */
	private void userRegister(Socket client, Request request)
			throws Exception {
		User user = new User();
		user.setUserid(IOUtility.getRegisteredUserCount() + 1);
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		IOUtility.writeUserToFile(user);
	}

	/**
	 * 用户登录
	 * @param client 客户的Socket对象
	 * @param request 从客户端发来的请求消息
	 * @param label 标签
	 * @throws Exception 给客户端发送信息失败抛出异常
	 */
	private void userLogin(Socket client, Request request, JLabel label)
			throws Exception{
		boolean flag = LoginRegisterService.userLoginValidate(
				request.getUsername(), request.getPassword());
		Response response = new Response();
		if (flag == true) { //验证通过
			onlineClient.add(client); //加入在线用户列表
			label.setText("当前在线用户数: " + onlineClient.size());
			label.updateUI();  //更新界面显示
		}
		//设置对客户端的响应信息
		response.setResult(flag);
		response.setMessageType(Response.LOGIN_MESSAGE);
		response.setMessage(request.getUsername());
		response.setObj(IOUtility.getAllGoods());  //向客户发送所有商品信息
		IOUtility.persistObjectNoClose(response, client.getOutputStream());
	}
	
	/**
	 * 停掉服务器
	 */
	public void stopServer() {
		flag = false;
	}
	
	/**
	 * 处理用户请求的内部类
	 * @author 王凯
	 *
	 */
	private class ClientThread extends Thread {
		
		private Socket client;
		
		private InputStream in;
		
		private volatile boolean flag;
		
		public ClientThread(Socket client) {
			this.client = client;
			try {
				in = client.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			flag = true;
			try {
				while (flag) {	
					Request request = (Request)IOUtility.getObjectNoClose(in);
					if (request == null) {
						continue;
					}
					switch (request.getType()) {
						case Request.LOGIN_MESSAGE:   //登录操作
							userLogin(client, request, onlineCountLabel);
							break;
						case Request.REGISTER_MESSAGE://注册操作
							userRegister(client, request);
							break;
						case Request.OFFLINE_MESSAGE: //离线操作
							userOffline(client, request, onlineCountLabel);
							flag = false;
							break;
						case Request.SEARCH_MESSAGE:  //检索操作
							searchGoodsByName(client, request);
							break;
						case Request.PAY_MESSAGE:     //结账操作
							userPay(client, request);
							break;
					}
				
				}
			} catch (SocketException e) {
				System.out.println("客户端无故掉线或网络出现故障！");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (client != null && !client.isClosed()) {
						client.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
