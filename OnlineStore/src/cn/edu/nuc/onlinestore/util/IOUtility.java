package cn.edu.nuc.onlinestore.util;

import java.io.File;
import java.io.IOException;

import cn.edu.nuc.onlinestore.model.User;

/**
 * IO工具类
 * @author 王凯
 *
 */
public class IOUtility {
	
	/**
	 * 应用根目录
	 */
	private static final String ROOT_DIRECTORY = "D://store// ";
	
	/**
	 * 商品目录(一件商品存为一个独立文件)
	 */
	private static final String GOODS_DIRECTORY = "D://store//goods//";
	
	/**
	 * 管理员帐号目录(一位管理员对应一独立文件) 
	 */
	private static final String ADMIN_DIRECTORY = "D://store//admin//";
	
	/**
	 * 客户帐号目录(一位客户对应一独立文件)
	 */
	private static final String USER_DIRECTORY = "D://store//user//";
	
	/**
	 * 检测并创建所有工作目录
	 */
	public static void checkAndCreateDirectory() {
		File rootDir = new File(ROOT_DIRECTORY);
		File adminDir = new File(ADMIN_DIRECTORY);
		File goodsDir = new File(GOODS_DIRECTORY);
		File userDir = new File(USER_DIRECTORY);
		if (!rootDir.exists()) {
			rootDir.mkdir();
		}
		if (!adminDir.exists()) {
			adminDir.mkdir();
		}
		if (!goodsDir.exists()) {
			goodsDir.mkdir();
		}
		if (!userDir.exists()) {
			userDir.mkdir();
		}
	}
	
	/**
	 * 判断用户目录是否存在
	 */
	public static boolean isUserFileExists(User user) {
		File userfile = new File(USER_DIRECTORY + user.getUserid() + ".dat");
		if (userfile.exists()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 创建用户对应的文件
	 */
	public static boolean createUserFile(User user) {
		if (!isUserFileExists(user)) {
			try {
				new File(USER_DIRECTORY + user.getUserid() + ".dat").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
	
	public Object 
}
