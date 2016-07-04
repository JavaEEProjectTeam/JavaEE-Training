package sms.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import sms.entity.Admin;
import sms.entity.Customer;
import sms.entity.Goods;
import sms.menu.Menu;
import sms.util.InitialTools;
import sms.util.ObjectPersistent;

/**
 * 会员相关业务
 * @author 王凯
 *
 */
public class CustomerService {
	
	/**
	 * 会员注册
	 * @return 注册用户的id
	 */
	public int customerRegister() {
		System.out.println("--------------------------当当网会员注册-----------------------------");
		
		//获取用户列表，用于判断用户是否已经注册过
		ObjectPersistent<List<Customer>> op = new ObjectPersistent<>();
		List<Customer> list = null;
		if (new File(InitialTools.customerFilePath).length() != 0) {
			try {
				list = op.getObject(InitialTools.customerFilePath);
			} catch (Exception e) {
				System.out.println("无法获取用户列表！列表所在目录可能无法访问！");
				e.printStackTrace();
				System.out.println("注册失败！");
				return -1;
			}
		} else {
			list = new ArrayList<>();
		}
		
		Scanner scanner = new Scanner(System.in);
		Customer customer = new Customer();
		
		while (true) {
			System.out.print("请输入会员名：");
			customer.setCname(scanner.next());
			if (isRegistrated(customer.getCname(), list)) {
				System.out.println("用户已注册过！换个名字吧！");
				continue;
			}
			break;
		}
		
		customer.setCid(list.size() + 1); //客户号自增1
		
		System.out.print("请输入密码：");
		customer.setCpass(scanner.next());
		
		System.out.print("请输入电话（不多于11位）");
		customer.setCphone(scanner.next());
		
		if(customer.getCphone().length() > 11){
			customer.setCphone(customer.getCphone().substring(0, 11));
		}
		
		list.add(customer);

		try {
			op.persistObject(list, InitialTools.customerFilePath);
		} catch (Exception e) {
			System.out.println("注册过程中出错");
			e.printStackTrace();
			return -1;
		}
		
		System.out.println("恭喜您，注册成功！");
		return customer.getCid();
	}
	
	/**
	 * 判断用户是否注册过（不允许同名用户注册）
	 * @param name 用户名
	 * @param list 用户列表
	 * @return 判断结果
	 */
	private boolean isRegistrated(String name, List<Customer> list) {
		//参数不正确或集合为空
		if (name == null || list == null || list.size() == 0) {
			return false;
		}
		
		for (Customer customer : list) {
			if (customer.getCname().equals(name)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 会员登录
	 * @return 登录用户的id
	 */
	public int customerLogin(){
		System.out.println("-----------------当当网会员登录-------------------");
		
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
		ObjectPersistent<List<Customer>> op = new ObjectPersistent<>();
		List<Customer> list = null;
		
		if (new File(InitialTools.customerFilePath).length() != 0) {
			try {
				list = op.getObject(InitialTools.customerFilePath);
			} catch (Exception e) {
				System.out.println("获取管理员信息列表失败！登录失败！");
				e.printStackTrace();
				return -1;
			}
		}
		else {
			list = new ArrayList<>();
		}

		//挨个进行匹配
		for (Customer customer : list) {
			if (customer.getCname().equals(name) 
					&& customer.getCpass().equals(pass)) {
				System.out.println("会员：" + customer.getCname() + "登录成功！");
				return customer.getCid();
			}
		}
		
		System.out.println("登录失败，用户名或密码不正确！");
		return -1;
	}
	
	/**
	 * 会员选购商品
	 * @param cid 会员编号
	 */
	public void selectGoods(int cid){
		System.out.println("--------------------会员选购商品----------------------");
		
		//加载并显示出全部商品信息
		GoodsService goodsService = new GoodsService();
		List<Goods> goodsList = goodsService.getAllGoods();
		if (goodsList == null) {
			System.out.println("还没有商品上架哦！");
			return;
		}
		Menu.displayGoodsList(goodsList);
		
		//加载出客户的购物车
		ObjectPersistent<List<Customer>> op = new ObjectPersistent<>();
		List<Customer> customers = null;
		try {
			customers = op.getObject(InitialTools.customerFilePath);
		} catch (Exception e) {
			System.out.println("加载客户信息列表失败！");
			e.printStackTrace();
			return;
		}
		Customer customer = getCustomer(customers, cid);
		Map<Integer, Integer> shoppingCart = customer.getShopList();
		
		//开始购物
		Scanner scanner = new Scanner(System.in);
		while(true) {
			Goods goods = null; //购买货物信息
			boolean isright = false;
			int count = 0; //购买数量
			do {
				System.out.print("请输入您想要的商品的编号：");
				int gid = scanner.nextInt();
				
				for (Goods good : goodsList) {
					if (good.getGid() == gid) {
						goods = good;
						isright = true;
					}
				}
				if (isright == false) {
					System.out.println("您输入的编号有误，请重输！");
				}
			} while(isright == false);

			isright = false;
			do {
				System.out.print("请输入您想要购买的数量(1-" + goods.getInventory() +")：");
				count = scanner.nextInt();
				
				//保证不出现负值，0值和超过库存的值
				if (count > 0 && count <= goods.getInventory()) { 
					isright = true;
				}
			} while (isright == false);
			
			shoppingCart.put(goods.getGid(), count);
			
			System.out.print("您还要继续购买吗？请输入Y/y或N/n");
			String choice = scanner.next();
			if (choice.equalsIgnoreCase("N")) {
				break;
			}
		}
		
		//将购物信息持久化
		try {
			op.persistObject(customers, InitialTools.customerFilePath);
		} catch (Exception e) {
			System.out.println("无法保存购物信息！");
			e.printStackTrace();
		}
		
		//打印购物车清单
		System.out.println("您的购物清单如下：");
		Menu.displayShoppingCart(shoppingCart, goodsList);
	}
	
	/**
	 * 查看购物车
	 */
	public void showShoppingCart(int cid) {
		ObjectPersistent<List<Customer>> op = new ObjectPersistent<>();
		List<Customer> customers = null;
		try {
			customers = op.getObject(InitialTools.customerFilePath);
		} catch (Exception e) {
			System.out.println("获取会员信息列表失败！");
			e.printStackTrace();
		}
		
		Customer cus = getCustomer(customers, cid);

		GoodsService goodsService = new GoodsService();
		List<Goods> goods = goodsService.getAllGoods();
		Menu.displayShoppingCart(cus.getShopList(), goods);
	}
	
	/**
	 * 顾客结账
	 */
	public void checkOut(int cid) {
		System.out.println("---------------客户结账-------------------");
		//获取客户信息
		ObjectPersistent<List<Customer>> opc = new ObjectPersistent<>();
		List<Customer> customers = null;
		try {
			customers = opc.getObject(InitialTools.customerFilePath);
		} catch (Exception e) {
			System.out.println("获取会员信息列表失败！");
			e.printStackTrace();
		}
		Customer customer = getCustomer(customers, cid);
		
		//得到购物车和账户余额信息
		Map<Integer, Integer> shoppingCart = customer.getShopList();
		
		//得到商品信息
		ObjectPersistent<List<Goods>> opg = new ObjectPersistent<>();
		List<Goods> goods = null;
		if (new File(InitialTools.goodsFilePath).length() == 0) {
			System.out.println("还没有商品上架哦！");
			return;
		}
		try {
			goods = opg.getObject(InitialTools.goodsFilePath);
		} catch (Exception e) {
			System.out.println("获取商品列表失败！");
			e.printStackTrace();
		}
		
		//看看购物车是否是空的
		if (shoppingCart.size() == 0) {
			System.out.println("您的购物车空空如也，快去购物填满它！");
			return;  //业务流程终止
		}
		
		//显示购物车和余额信息，并且计算出总金额
		float totalCost = Menu.displayShoppingCart(shoppingCart, goods);
		System.out.println("您的账户余额为：" + customer.getAccountBalance());
		
		//查看客户余额是否充足，如余额不足，提示用户进行充值
		if (totalCost > customer.getAccountBalance()) {
			System.out.println("您的账户余额不足，请先充值！");
			return; //结束业务流程
		}

		
		//如果余额充足，提示用户是否开始结算商品
		System.out.print("您要现在结算吗？(请输入Y/y或N/n)");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("N")) {
			System.out.println("谢谢惠顾！");
			return; //结束业务流程
		}
		
		//客户要结账了,开始结算业务流程
		customer.setAccountBalance(
				customer.getAccountBalance() - totalCost);  //扣减账户余额
		shoppingCart.clear();  //清空购物车
		goods = reduceInventory(shoppingCart, goods);       //扣减库存
		try {
			opc.persistObject(customers, InitialTools.customerFilePath);
		} catch (Exception e) {
			System.out.println("同步账户信息失败！");
			e.printStackTrace();
			return;
		}
		try {
			opg.persistObject(goods, InitialTools.goodsFilePath);
		} catch (Exception e) {
			System.out.println("同步库存信息失败！");
			e.printStackTrace();
			return;
		}
		
		//结算成功，输出后续信息
		System.out.println("恭喜您，购买成功！您本次消费" 
				+ totalCost + "元，账户余额为：" + customer.getAccountBalance()
				+ "。您的联系方式为：" + customer.getCphone() 
				+ "。\n我家使用圆通快递，下午5点之前下单的当天发货，否则次日发货。"
				+ "我们将在三个工作日内把货送到。等电话吧，亲！");
	}
	
	/**
	 * 扣减库存量
	 * @return 返回扣减库存量后的商品信息
	 */
	public List<Goods> reduceInventory(
			Map<Integer, Integer> shoppingCart, List<Goods> goods){
		Set<Integer> gids = shoppingCart.keySet();
		for (Goods good : goods) {
			for (Integer gid : gids) {
				//扣减库存量
				if (gid == good.getGid()) { //顾客购买了该商品
					int usercount = shoppingCart.get(gid); //用户想卖多少
					int sellcount =  good.getInventory() >= usercount  //商家能卖给用户多少
							? usercount : good.getInventory();
					int reduceCount = sellcount >= usercount   //库存扣减量
							? usercount : sellcount;
					good.setInventory(good.getInventory() - reduceCount);  //执行扣减
				}
			}
		}
		return goods;
	}
	
	/**
	 * 根据客户号从客户列表中查找客户信息
	 * @param customers 包含客户信息的集合
	 * @param cid 客户号
	 * @return 客户对象，如果没有找到客户信息或者传入参数非法返回null
	 */
	private Customer getCustomer(List<Customer> customers, int cid) {
		if (customers == null || cid <= 0) { //参数非法
			return null;
		}
		for (Customer customer : customers) {
			if (customer.getCid() == cid) {
				return customer;
			}
		}
		return null;
	}
}
