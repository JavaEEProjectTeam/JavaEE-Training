package cn.edu.nuc.onlinestore.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.edu.nuc.onlinestore.model.Admin;
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
	private static final String ROOT_DIRECTORY = "D:" + File.separator + "store" + File.separator;
	
	/**
	 * 商品目录(一件商品存为一个独立文件)
	 */
	private static final String GOODS_DIRECTORY = ROOT_DIRECTORY + "goods" + File.separator;
	
	/**
	 * 管理员帐号目录(一位管理员对应一独立文件) 
	 */
	private static final String ADMIN_DIRECTORY = ROOT_DIRECTORY + "admin" + File.separator;
	
	/**
	 * 客户帐号目录(一位客户对应一独立文件)
	 */
	private static final String USER_DIRECTORY = ROOT_DIRECTORY + "user" + File.separator;
	
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
	 * 判断文件或目录是否存在
	 */
	private static boolean isFileExists(String path) {
		File file = new File(path);
		if (file.exists()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 创建文件
	 */
	private static boolean createFile(String path) {
		if (!isFileExists(path)) {
			try {
				new File(path).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 得到已注册用户的数量
	 * @return 已注册用户的数量
	 */
	public static int getRegisteredUserCount() {
		return new File(USER_DIRECTORY).listFiles().length;
	}
	
	/**
	 * 得到已注册管理员的数量
	 * @return 已注册管理员的数量
	 */
	public static int getRegisteredAdminCount() {
		return new File(ADMIN_DIRECTORY).listFiles().length;
	}
	
	/**
	 * 将用户信息持久化到文件中
	 * @param user 用户信息
	 */
	public static void writeUserToFile(User user) {
		String path = USER_DIRECTORY + user.getUserid() + ".dat";
		createFile(path);
		File userfile = new File(path);
		OutputStream out = null;
		try {
			out = new FileOutputStream(userfile);
			persistObject(user, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据userid从文件中取得对应的user对象
	 * @param id 用户id
	 * @return User实例
	 */
	public static User readUserFromFile(int id){
		String path = USER_DIRECTORY + id + ".dat";
		if (!isFileExists(path)) {
			return null;
		}
		File userfile = new File(path);
		InputStream in = null;
		try {
			in = new FileInputStream(userfile);
			User user = (User)getObject(in);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将管理员信息持久化到文件中
	 */
	public static void writeAdminToFile(Admin admin) {
		String path = ADMIN_DIRECTORY + admin.getAdminid() + ".dat";
		createFile(path);
		File userfile = new File(path);
		OutputStream out = null;
		try {
			out = new FileOutputStream(userfile);
			persistObject(admin, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Admin> getAllAdmin() {
		List<Admin> list = new ArrayList<Admin>();
		File[] adminfiles = new File(ADMIN_DIRECTORY).listFiles();
		try {
			for (File file : adminfiles) {
				Admin admin = (Admin)getObject(new FileInputStream(file));
				list.add(admin);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据adminid从文件中取得对应的admin对象
	 * @param adminid 管理员id
	 */
	public static Admin readAdminFromFile(int adminid) {
		String path = ADMIN_DIRECTORY + adminid + ".dat";
		if (!isFileExists(path)) {
			return null;
		}
		File adminfile = new File(path);
		InputStream in = null;
		try {
			in = new FileInputStream(adminfile);
			Admin admin = (Admin)getObject(in);
			return admin;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 对象持久化
	 * @param object 要持久化的对象
	 * @param out 输出流
	 * @throws Exception 出现错误后抛出
	 */
	public static void persistObject(Object object, OutputStream out){
		if (object == null) {
			return ;
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(out);
			oos.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从输入流中反序列化对象
	 * @param in
	 * @return 对象
	 */
	public static Object getObject(InputStream in) {
		if (in == null) {
			return null;
		}
		ObjectInputStream ois = null;
		Object object = null;
		try {
			ois = new ObjectInputStream(in);
			object = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return object;
	}
}
