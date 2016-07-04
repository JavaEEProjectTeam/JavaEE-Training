package sms.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import sms.controller.Controller;
import sms.controller.factory.ControllerFactory;
import sms.entity.Admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 初始化工具，添加管理员
 * 
 * @author 王凯
 *
 */
public class InitialTools {
	
	public static Properties prop = new Properties();
	public static String ROOT_PATH = "c:";
	public static String BASE_PATH = null;
	public static String ADMIN_PATH = null;
	public static String CUSTOMER_PATH = null;
	public static String GOODS_PATH = null;
	public static String adminFilePath = null;
	public static String customerFilePath = null;
	public static String goodsFilePath = null;
	
	//使用静态块加载环境资源文件
	static {
		try {
			prop.load(InitialTools.class.getResourceAsStream(
					"/init.properties"));
			ROOT_PATH = prop.getProperty("ROOT_PATH");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			BASE_PATH = ROOT_PATH + File.separator + "sms";
			ADMIN_PATH = BASE_PATH + File.separator + "admin";
			CUSTOMER_PATH = BASE_PATH + File.separator + "customer";
			GOODS_PATH = BASE_PATH + File.separator + "goods";
			adminFilePath = ADMIN_PATH + File.separator + "admin.dat";
			customerFilePath = CUSTOMER_PATH + File.separator + "customer.dat";
			goodsFilePath = GOODS_PATH + File.separator + "goods.dat";
		}
	}

	public static void initEnvironment() throws Exception {
		
		System.out.println("-----------------正在运行系统环境初始化工具--------------------");

		// 创建所有目录和文件
		new File(BASE_PATH).mkdir();
		new File(ADMIN_PATH).mkdir();
		new File(CUSTOMER_PATH).mkdir();
		new File(GOODS_PATH).mkdir();
		new File(adminFilePath).createNewFile();
		new File(customerFilePath).createNewFile();
		new File(goodsFilePath).createNewFile();

		// 创建目标集合
		List<Admin> list = new ArrayList<Admin>();
		int total = list.size(); // 获得集合中元素的个数，用于实现id自动增长
		list.add(new Admin(++total, "张三", "123"));
		list.add(new Admin(++total, "李四", "456"));
		list.add(new Admin(++total, "王五", "789"));

		//调用对象持久化工具完成管理员信息的持久化
		ObjectPersistent<List<Admin>> op = new ObjectPersistent<>();
		op.persistObject(list, adminFilePath);

		//检查持久化操作是否成功
		List<Admin> list2 = op.getObject(adminFilePath);
		System.out.println("--->该系统默认共有三个管理员，他们的信息如下：");
		for (Admin admin : list2) {
			System.out.println(admin);
		}
		
		System.out.println("------------------该系统初始化工作全部完成！---------------------");
	}
	
	/**
	 * 检查环境是否成功初始化
	 */
	public static boolean isEnvironmentInitialed(){
		if (!new File(ROOT_PATH).exists()) {
			return false;
		}
		if (!new File(BASE_PATH).exists()) {
			return false;
		}
		if (!new File(CUSTOMER_PATH).exists()) {
			return false;
		}
		if (!new File(ADMIN_PATH).exists()) {
			return false;
		}
		if (!new File(GOODS_PATH).exists()) {
			return false;
		}
		if (!new File(adminFilePath).exists()) {
			return false;
		}
		if (!new File(goodsFilePath).exists()) {
			return false;
		}
		if (!new File(customerFilePath).exists()) {
			return false;
		}
		return true;
	}

}
