package cn.edu.nuc.onlinestore.model;

import java.io.Serializable;

/**
 * 商品类
 * 
 * @author 王凯
 *
 */
public class Goods implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3565587646030892291L;

	/**
	 * 商品编号
	 */
	private int gid;

	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 商品单价
	 */
	private float price;

	/**
	 * 库存（加）
	 */
	private int inventory;

	/**
	 * 商品简介
	 */
	private String note;

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	// 根据需要重写 hashCode() 及 equals()
	// 此处以商品id为判定是否为同一商品标准,可根据需要自行修改
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Goods other = (Goods) obj;
		if (gid != other.gid)
			return false;
		return true;
	}

	// 根据需要重写toString()
	@Override
	public String toString() {
		return "Goods [gid=" + gid + ", goodsName=" + goodsName + ", price="
				+ price + ", inventory=" + inventory + ", note=" + note + "]";
	}

}
