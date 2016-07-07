package cn.edu.nuc.onlinestore.service;

import java.net.Socket;

import cn.edu.nuc.onlinestore.io.IOUtility;
import cn.edu.nuc.onlinestore.model.Cart;
import cn.edu.nuc.onlinestore.vo.Request;

/**
 * 与商品有关的业务逻辑
 * @author 王凯
 *
 */
public class GoodsService {
	
	/**
	 * 客户端的Socket对象
	 */
	private Socket client;
	
	public GoodsService(Socket client) {
		this.client = client;
	}
	
	/**
	 * 用户按名称搜索商品信息或者检索全部商品信息
	 * @param name 商品名称
	 * @throws Exception 向服务器端发送消息失败
	 */
	public void searchGoods(String name) throws Exception {
		Request request = new Request();
		request.setType(Request.SEARCH_MESSAGE);  //说明请求的性质
		request.setGoodsname(name);
		IOUtility.persistObjectNoClose(request, client.getOutputStream());
	}
	
	/**
	 * 发送购物车信息到服务器
	 * @param cart  购物车
	 * @throws Exception 向服务器端发送消息失败
	 */
	public void sendPayRequest(Cart cart) throws Exception {
		Request request = new Request();
		request.setType(Request.PAY_MESSAGE);
		request.setCart(cart);
		IOUtility.persistObjectNoClose(request, client.getOutputStream());
	}
	
}
