package cn.edu.nuc.onlinestore.vo;

import java.io.Serializable;

public class Result<T> implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1301418053396888480L;
	private int code;
	private String msg;
	
	
	private T obj;
	
	//getter setter

}
