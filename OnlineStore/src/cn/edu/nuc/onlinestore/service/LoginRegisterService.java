package cn.edu.nuc.onlinestore.service;

import java.util.List;

import cn.edu.nuc.onlinestore.io.IOUtility;
import cn.edu.nuc.onlinestore.model.Admin;
import cn.edu.nuc.onlinestore.model.User;

/**
 * 登录注册逻辑
 * @author 王凯
 *
 */
public class LoginRegisterService {
	
	/**
	 * 普通用户登录验证
	 * @param temp
	 * @return
	 */
	public static boolean userLoginValidate(User temp) {
		List<User> list = IOUtility.getAllUser();
		for (User user : list) {
			if (user.getUsername().equals(temp.getUsername()) 
				&& user.getPassword().equals(temp.getPassword())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 用户注册
	 * @param user 用户对象
	 */
	public static void userRegist(User user) {
		user.setUserid(IOUtility.getRegisteredUserCount() + 1); //实现ID的自动增长
		IOUtility.writeUserToFile(user); //写入对应文件
	}
	
	/**
	 * 管理员登录
	 * @param admin 管理员登录
	 * @return 登录验证结果
	 */
	public static boolean adminLoginValidate(Admin temp) {
		List<Admin> list = IOUtility.getAllAdmin();
		for (Admin admin : list) {
			if (admin.getAdminName().equals(temp.getAdminName())
					&& admin.getPassword().equals(temp.getPassword())) {
				return true;
			}
		}
		return false;
	}
}
