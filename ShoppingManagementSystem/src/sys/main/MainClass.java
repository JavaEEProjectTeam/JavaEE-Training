package sys.main;

import sms.controller.MainController;
import sms.util.InitialTools;

/**
 * 主方法
 * @author 王凯
 *
 */
public class MainClass {

	public static void main(String[] args){
		//创建并启动核心控制器
		MainController mainController = new MainController();
		mainController.begin();
	}

}
