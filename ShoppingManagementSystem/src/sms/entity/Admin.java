package sms.entity;

import java.io.Serializable;

/**
 * 管理员类
 * 
 * @author 王凯
 *
 */
public class Admin implements Serializable{
	private int aid;
	private String aname;
	private String apass;

	public Admin() {
		
	}

	public Admin(int aid, String aname, String apass) {
		this.aid = aid;
		this.aname = aname;
		this.apass = apass;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getApass() {
		return apass;
	}

	public void setApass(String apass) {
		this.apass = apass;
	}

	@Override
	public String toString() {
		return "管理员 [管理员编号=" + aid 
				+ ", 管理员名字=" + aname 
				+ ", 管理员密码=" + apass + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + aid;
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
		if (!(obj instanceof Admin)) {
			return false;
		}
		Admin other = (Admin) obj;
		if (aid != other.aid) {
			return false;
		}
		return true;
	}

	
}
