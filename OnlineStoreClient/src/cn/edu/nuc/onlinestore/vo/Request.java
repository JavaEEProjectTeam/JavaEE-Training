package cn.edu.nuc.onlinestore.vo;

import java.io.Serializable;

import cn.edu.nuc.onlinestore.model.Cart;

/**
 * 客户端给服务器的传输信息类
 * @author 王凯
 *
 */
public class Request implements Serializable{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7684290259100817956L;

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
	private int type;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 商品名称
	 */
	private String goodsname;
	
	/**
	 * 购物车
	 */
	private Cart cart;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		if (type == LOGIN_MESSAGE) {
			return "登录用户名：[" + username + "],密码：[" + password + "]";
		}
		else if (type == REGISTER_MESSAGE) {
			return "注册用户名：[" + username + "],密码：[" + password + "]";
		}
		else if (type == SEARCH_MESSAGE) {
			return "想要查找的商品名称：[" + goodsname + "]";
		}
		else if (type == OFFLINE_MESSAGE) {
			return "我要下线了";
		}
		else {
			return "购物车为：" + cart;
		}
	}

}
