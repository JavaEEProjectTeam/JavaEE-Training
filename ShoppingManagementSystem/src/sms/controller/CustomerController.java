package sms.controller;

import sms.menu.Menu;
import sms.service.CustomerService;

public class CustomerController extends Controller{
	
	/**
	 * 客户控制器的核心控制流程
	 */
	@Override
	public void begin() {
		CustomerService service = new CustomerService();
		int cid = -1; //-1表示未登录
		while(true) {
			int max = Menu.showCustomerMenu();
			int select = selectOptions(max);
			switch (select) {
				case 0:  //退出
					return;
				case 1:
					cid = service.customerRegister();
					break;
				case 2:
					cid = service.customerLogin();
					break;
				case 3:
					if (cid == -1) {
						System.out.println("您还没登录，请先登录！");
						break;
					}
					service.selectGoods(cid);
					break;
				case 4:
					if (cid == -1) {
						System.out.println("您还没登录，请先登录！");
						break;
					}
					service.showShoppingCart(cid);
					break;
				case 5:
					if (cid == -1) {
						System.out.println("您还没登录，请先登录！");
						break;
					}
					service.checkOut(cid);
					break;
			}
			
		}
	}
	
}
