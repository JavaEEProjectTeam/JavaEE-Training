package cn.edu.nuc.onlinestore.model;

import java.io.Serializable;

/**
 * 管理员类
 * @author 王凯
 *
 */
public class Admin implements Serializable{
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5461656029989540791L;

	/**
	 * 管理员id
	 */
	private int adminid;
	
	/**
	 * 管理员姓名
	 */
	private String adminName;
	
	/**
	 * 管理员密码
	 */
	private String password;
	
	/**
	 * 无参构造
	 */
	public Admin(){
		
	}

	/**
	 * 全参构造
	 * @param adminid 管理员id
	 * @param adminName 管理员名
	 * @param password 管理员密码
	 */
	public Admin(int adminid, String adminName, String password) {
		this.adminid = adminid;
		this.adminName = adminName;
		this.password = password;
	}

	public int getAdminid() {
		return adminid;
	}
	
	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}
	
	public String getAdminName() {
		return adminName;
	}
	
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + adminid;
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
		Admin other = (Admin) obj;
		if (adminid != other.adminid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Admin [adminid=" + adminid + ", adminName=" + adminName
				+ ", password=" + password + "]";
	}
}
