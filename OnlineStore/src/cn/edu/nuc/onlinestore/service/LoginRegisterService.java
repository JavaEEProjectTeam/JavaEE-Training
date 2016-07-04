package cn.edu.nuc.onlinestore.service;

import cn.edu.nuc.onlinestore.model.Admin;
import cn.edu.nuc.onlinestore.model.User;
import cn.edu.nuc.onlinestore.util.IOUtility;

/**
 * 登录注册逻辑
 * @author 王凯
 *
 */
public class LoginRegisterService {
	
	public static boolean userLoginValidate(User user) {
		return false;
	}
	
	/**
	 * 管理员登录
	 * @param admin 管理员登录
	 * @return 登录验证结果
	 */
	public static boolean adminLoginValidate(Admin temp) {
		Admin admin = IOUtility.readAdminFromFile(temp.getAdminid());
		if (admin != null) {
			if (admin.getAdminName().equals(temp.getAdminName())
					&& admin.getPassword().equals(temp.getPassword())) {
				return true;
			}
		}
		return false;
	}
}
