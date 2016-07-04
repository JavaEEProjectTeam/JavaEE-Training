package sms.controller.factory;

import java.util.Properties;

import sms.controller.AdminController;
import sms.controller.Controller;
import sms.controller.CustomerController;

/**
 * 控制器工厂
 * @author 王凯
 *
 */
public class ControllerFactory {
	
	private static Properties prop = new Properties();
	
	//使用静态块加载Properties文件
	static {
		try {
			prop.load(ControllerFactory.class
					.getResourceAsStream(
							"/sms/controller/factory/controller.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据用户选择获得控制器实例
	 * @param select 要获得控制器的类型
	 * @return 控制器实例
	 */
	public static Controller getInstance(int select) {
		try {
			return (Controller) Class.forName(
						prop.getProperty(Integer.toString(select)))
							.newInstance();
		} catch (ClassNotFoundException e) {
			System.out.println("(⊙o⊙)参数select对应的控制器类不存在");
			e.printStackTrace();
			return null;
		} catch (Exception e2) {
			e2.printStackTrace();
			return null;
		}
	}
}
