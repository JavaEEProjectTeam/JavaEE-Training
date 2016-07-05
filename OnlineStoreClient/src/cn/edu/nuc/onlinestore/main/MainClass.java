package cn.edu.nuc.onlinestore.main;

import java.awt.EventQueue;

import cn.edu.nuc.onlinestore.frame.UserLogin;

public class MainClass {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserLogin frame = new UserLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
