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
import cn.edu.nuc.onlinestore.model.Goods;
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
	private static final String ROOT_DIRECTORY = 
			"D:" + File.separator + "store" + File.separator;
	
	/**
	 * 商品目录(一件商品存为一个独立文件)
	 */
	private static final String GOODS_DIRECTORY = 
			ROOT_DIRECTORY + "goods" + File.separator;
	
	/**
	 * 管理员帐号目录(一位管理员对应一独立文件) 
	 */
	private static final String ADMIN_DIRECTORY = 
			ROOT_DIRECTORY + "admin" + File.separator;
	
	/**
	 * 客户帐号目录(一位客户对应一独立文件)
	 */
	private static final String USER_DIRECTORY = 
			ROOT_DIRECTORY + "user" + File.separator;
	
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
	 * 获得下一个商品编号
	 * @return
	 */
	public static int getNextGoodsId() {
		String[] goodsFileNames = new File(GOODS_DIRECTORY).list();
		int max = -1;
		for (String string : goodsFileNames) {
			string = string.substring(0, string.indexOf('.'));
			int num = Integer.parseInt(string);
			if (max < num) {
				max = num;
			}
		}
		return max + 1;
	}
	
	/**
	 * 根据id获得商品信息
	 * @param id 商品id
	 * @return
	 */
	public static Goods getGoodsById(int id) {
		List<Goods> list = getAllGoods();
		for (Goods goods : list) {
			if (goods.getGid() == id) {
				return goods;
			}
		}
		return null;
	}
	
	/**
	 * 删除商品信息
	 * @param id 商品id
	 */
	public static void deleteGoods(int id) {
		String path = GOODS_DIRECTORY + id + ".dat";
		File deleteFile = new File(path);
		if (deleteFile.exists()) {
			deleteFile.delete();
		}
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
	
	/**
	 * 将货物信息持久化到文件中
	 */
	public static void writeGoodsToFile(Goods goods) {
		String path = GOODS_DIRECTORY + goods.getGid() + ".dat";
		createFile(path);
		File goodsfile = new File(path);
		OutputStream out = null;
		try {
			out = new FileOutputStream(goodsfile);
			persistObject(goods, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到所有的管理员信息
	 * @return 所有管理员信息
	 */
	public static List<Admin> getAllAdmin() {
		List<Admin> list = new ArrayList<Admin>();
		File[] adminfiles = new File(ADMIN_DIRECTORY).listFiles();
		try {
			for (File file : adminfiles) {
				Admin admin = (Admin)getObject(
						new FileInputStream(file));
				list.add(admin);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 得到所有的客户信息
	 * @return 所有客户信息
	 */
	public static List<User> getAllUser() {
		List<User> list = new ArrayList<User>();
		File[] userfiles = new File(USER_DIRECTORY).listFiles();
		try {
			for (File file : userfiles) {
				User user = (User)getObject(
						new FileInputStream(file));
				list.add(user);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 得到所有的客户信息
	 * @return 所有客户信息
	 */
	public static List<Goods> getAllGoods() {
		List<Goods> list = new ArrayList<Goods>();
		File[] goodsfiles = new File(GOODS_DIRECTORY).listFiles();
		try {
			for (File file : goodsfiles) {
				Goods goods = (Goods)getObject(
						new FileInputStream(file));
				list.add(goods);
			}
			return list;
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
