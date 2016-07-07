package cn.edu.nuc.onlinestore.util;

import cn.edu.nuc.onlinestore.io.IOUtility;
import cn.edu.nuc.onlinestore.model.Admin;

/**
 * 管理员初始化工具
 * @author 王凯
 *
 */
public class AdminInitTools {
	
	/**
	 * 管理员初始化
	 */
	public static void initAdmin() {
		if (IOUtility.getRegisteredAdminCount() == 0) {
			Admin zs = new Admin(IOUtility.getRegisteredAdminCount() + 1, "张三", "123");
			IOUtility.writeAdminToFile(zs);
			
			Admin ls = new Admin(IOUtility.getRegisteredAdminCount() + 1, "李四", "456");
			IOUtility.writeAdminToFile(ls);
			
			Admin ww = new Admin(IOUtility.getRegisteredAdminCount() + 1, "王五", "789");
			IOUtility.writeAdminToFile(ww);
			
			System.out.println("系统初始化了三个管理员，他们的用户名密码分别是：\n"
					+ " 张三,123      李四,456   王五,789");
		}
	}
	
}
