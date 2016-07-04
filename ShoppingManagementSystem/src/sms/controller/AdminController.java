package sms.controller;

import java.util.Scanner;

import sms.menu.Menu;
import sms.service.AdminService;
import sms.service.GoodsService;

public class AdminController extends Controller{
	
	private boolean isLogin = false;
	
	/**
	 * 管理员的核心控制流程
	 * @throws Exception 
	 */
	@Override
	public void begin() {
		while(true) {
			int max = Menu.showAdminMenu();
			int select = selectOptions(max);
			switch (select) {
				case 0:
					return;
				case 1:
					isLogin = new AdminService().adminLogin();
					break;
				case 2:
					if (!isLogin) {
						System.out.println("您还未登陆，请登录");
						break;
					}
					new GoodsService().addGoods();
					break;
				case 3:
					if (!isLogin) {
						System.out.println("您还未登陆，请登录");
						break;
					}
					new GoodsService().modifyGoodsInventory();
			}
			
		}
	}
	
}
