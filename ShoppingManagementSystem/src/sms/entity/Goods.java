package sms.entity;

import java.io.Serializable;

/**
 * 商品类
 * @author 王凯
 *
 */
public class Goods implements Serializable{
	/**
	 * 商品编号
	 */
	private int gid;
	
	/**
	 * 商品名称
	 */
	private String gname;
	
	/**
	 * 商品单价
	 */
	private float unitprice;
	
	/**
	 * 库存量
	 */
	private int inventory;

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public float getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(float unitprice) {
		this.unitprice = unitprice;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	@Override
	public String toString() {
		return "商品 [商品号=" + gid 
				+ ", 商品名=" + gname 
				+ ", 单价=" + unitprice 
				+ ", 库存=" + inventory + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gid;
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
		if (!(obj instanceof Goods)) {
			return false;
		}
		Goods other = (Goods) obj;
		if (gid != other.gid) {
			return false;
		}
		return true;
	}

}
