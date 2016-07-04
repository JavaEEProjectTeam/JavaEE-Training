package sms.service;

import java.util.List;
import java.util.Scanner;

import sms.entity.Admin;
import sms.util.InitialTools;
import sms.util.ObjectPersistent;

/**
 * 管理员的相关业务
 * @author 王凯
 *
 */
public class AdminService {
	
	

	/**
	 * 管理员登录
	 * @return 登录验证结果
	 */
	public boolean adminLogin(){
		System.out.println("-----------------管理员登录-------------------");
		
		String name = null;
		String pass = null;
		
		//输入用户名和密码
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("请输入用户名：");
			name = scanner.next();
			System.out.print("请输入密码：");
			pass = scanner.next();
			
			//用户名或密码为空
			if (name == null || pass == null 
					|| "".equals(name) || "".equals(pass)) {
				System.out.println("您输入用户名或密码为空，请重输！");
				continue;
			}
			
			break;  //直到输对为止
		}
		
		//获取密码文件
		ObjectPersistent<List<Admin>> op = new ObjectPersistent<>();
		List<Admin> list = null;
		try {
			list = op.getObject(InitialTools.adminFilePath);
		} catch (Exception e) {
			System.out.println("获取管理员信息列表失败！登录失败！");
			e.printStackTrace();
			return false;
		}
		
		//挨个进行匹配
		for (Admin admin : list) {
			if (admin.getAname().equals(name) 
					&& admin.getApass().equals(pass)) {
				System.out.println("管理员：" + admin.getAname() + "登录成功！");
				return true;
			}
		}
		
		System.out.println("登录失败，用户名或密码不正确！");
		return false;
	}
	
}
