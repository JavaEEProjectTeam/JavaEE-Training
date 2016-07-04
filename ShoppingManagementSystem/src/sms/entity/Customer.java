package sms.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 当当网会员类
 * 
 * @author 王凯
 *
 */
public class Customer implements Serializable{
	/**
	 * 会员号
	 */
	private int cid;
	
	/**
	 * 会员名
	 */
	private String cname;
	
	/**
	 * 登录密码
	 */
	private String cpass;
	
	/**
	 * 联系电话
	 */
	private String cphone;
	
	/**
	 * 购物车
	 */
	private Map<Integer, Integer> shopList = new HashMap<>();
	
	/**
	 * 账户余额
	 */
	private float accountBalance = 0.0f;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCpass() {
		return cpass;
	}

	public void setCpass(String cpass) {
		this.cpass = cpass;
	}

	public String getCphone() {
		return cphone;
	}

	public void setCphone(String cphone) {
		this.cphone = cphone;
	}

	public Map<Integer, Integer> getShopList() {
		return shopList;
	}

	public float getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "会员 [会员id=" + cid 
				    + ", 会员名=" + cname 
				    + ", 密码=" + cpass
					+ ", 电话=" + cphone 
					+ ", 购物车内容=" + shopList 
					+ ", 账户余额=" + accountBalance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) obj;
		if (cid != other.cid) {
			return false;
		}
		return true;
	}

}
