package main;

import java.awt.EventQueue;

import notepad.Notepad;

/**
 * ������
 * @author ����
 *
 */
public class MainClass {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				Notepad frame = null;
				try {
					frame = new Notepad();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				frame.setVisible(true);
			}
		});
	}
}
