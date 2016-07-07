package cn.edu.nuc.onlinestore.network;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import cn.edu.nuc.onlinestore.frame.UserLogin;
import cn.edu.nuc.onlinestore.frame.UserStore;
import cn.edu.nuc.onlinestore.io.IOUtility;
import cn.edu.nuc.onlinestore.model.Goods;
import cn.edu.nuc.onlinestore.vo.Request;
import cn.edu.nuc.onlinestore.vo.Response;

/**
 * 客户端线程
 * @author 王凯
 *
 */
public class TCPClient extends Thread{

	/**
	 * 客户端Socket对象
	 */
	private Socket client;
	
	/**
	 * 旗标，用于控制客户端网络进程的关停
	 */
	private volatile boolean flag;
	
	/**
	 * 登录界面
	 */
	private UserLogin loginframe;
	
	/**
	 * 用户主界面
	 */
	private UserStore userStore;
	
	public TCPClient(UserLogin loginframe) {
		this.loginframe = loginframe;
		try {
			client = new Socket("127.0.0.1", 8888);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			flag = true;
			while (flag) {  //不停地从服务器读取数据
				Response response = (Response)IOUtility.getObjectNoClose(client.getInputStream());
				if (response == null) {
					continue;
				}
				
				//根据响应的性质执行相关操作
				switch (response.getMessageType()) {
					case Response.LOGIN_MESSAGE:   //登录操作
						if (response.getResult() == true) { //服务器验证通过
							List<Goods> goodsList = (List<Goods>)response.getObj();
							userStore = new UserStore(this, response.getMessage(),goodsList);
							userStore.setVisible(true);
							loginframe.setVisible(false);
							break;
						}
						loginframe.callUser("登录失败,错误的用户名和密码！");
						break;
					case Response.REGISTER_MESSAGE://注册操作
						break;
					case Response.OFFLINE_MESSAGE: //离线操作
						loginframe.setVisible(true);
						break;
					case Response.SEARCH_MESSAGE:  //检索操作
						List<Goods> goodsList = (List<Goods>)response.getObj();  //得到结果
						userStore.updateGoodsList(goodsList);  //通知相关窗口更新信息显示
						break;
					case Response.PAY_MESSAGE:     //结账操作
						if (response.getResult() == true) {
							userStore.cleanCart();  //清空购物车
						}
						List<Goods> newList = (List<Goods>)response.getObj();
						userStore.updateGoodsList(newList);
						UserStore.callUser(response.getMessage());
						break;
					default:
						break;
				}
			}
		} catch(EOFException e) {
			//读到结尾了，不管他
		} catch(SocketException e) {
			System.out.println("客户端无故掉线或网络出现故障！");
			try {
				if( client != null && ! client.isClosed() )
					client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 外部获取客户端Socket，方便进行写操作
	 * @return 客户端socket对象
	 */
	public Socket getClient() {
		return client;
	}

	/**
	 * 停掉网络进程
	 * @throws Exception 停止进程时出现异常 
	 */
	public void stopClient() throws Exception {
		Request request = new Request();
		request.setType(Request.OFFLINE_MESSAGE);
		IOUtility.persistObjectNoClose(request, TCPClient.this.client.getOutputStream());
		flag = false;
	}
}
