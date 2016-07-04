package sms.controller;

import java.util.Scanner;

import sms.controller.factory.ControllerFactory;
import sms.menu.Menu;
import sms.util.InitialTools;

/**
 * 主控制器，用于控制程序的核心执行流程
 * @author 王凯
 *
 */
public class MainController extends Controller{
	
	/**
	 * 执行核心控制流程
	 */
	@Override
	public void begin(){
		//判断环境是否被成功初始化，如没有，要先初始化
		if (!InitialTools.isEnvironmentInitialed()) {
			try {
				InitialTools.initEnvironment();
			} catch (Exception e) {
				System.out.println("初始化工具运行过程中发生问题！");
				e.printStackTrace();
			}
		}
		while(true){
			try {
				int max = Menu.showIdentityMenu(); //显示身份选择菜单
				int select = selectOptions(max);
				//如果要退出
				if (select == 0) {  
					System.out.println("谢谢使用，再见！");
					return;
				}
				//否则就进入子控制器
				Controller controller = ControllerFactory.getInstance(select);
				controller.begin();
			} catch (Exception e) {
				System.out.println("(⊙o⊙)出错了！");
				e.printStackTrace();
			}
		}
		
	}
}