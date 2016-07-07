package cn.edu.nuc.onlinestore.main;

import java.awt.EventQueue;

import cn.edu.nuc.onlinestore.frame.AdminLogin;
import cn.edu.nuc.onlinestore.io.IOUtility;
import cn.edu.nuc.onlinestore.util.AdminInitTools;

/**
 * 主类
 * @author 王凯
 *
 */
public class MainClass {

	public static void main(String[] args) {
		initEnvironment(); //初始化运行时环境
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				AdminLogin frame = null;
				try {
					frame = new AdminLogin();  //启动登录界面
				} catch (Exception e) {
					e.printStackTrace();
				}
				frame.setVisible(true);
			}
		});
	}
	
	public static void initEnvironment() {
		IOUtility.checkAndCreateDirectory();
		AdminInitTools.initAdmin();
	}

}
