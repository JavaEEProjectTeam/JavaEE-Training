package sms.menu;

import java.util.List;
import java.util.Map;
import java.util.Set;

import sms.entity.Goods;

/**
 * 菜单类，用于显示所有的菜单
 * @author 王凯
 *
 */
public class Menu {
	
	/**
	 * 显示选择身份菜单
	 * @return 菜单项的最大值
	 */
	public static int showIdentityMenu(){
		System.out.println("* * * * * * * * * * 当当网购物管理系统  * * * * * * * * * *");
		System.out.println("*          请选择您的身份 (输入对应的数字编号即可)         *");
		System.out.println("*                    1.当当网会员                                              *");
		System.out.println("*                    2.当当网管理人员                                       *");
		System.out.println("*                    0.退出系统                                                  *");
		System.out.println("* * * * * * * * * * * * * * * * * *  * * * * * * * * * *");
		System.out.print("您选择：");
		return 2;
	}
	
	/**
	 * 显示当当网会员菜单
	 */
	public static int showCustomerMenu() {
		System.out.println("* * * * * * * * * * 当当网购物管理系统（会员）  * * * * * * * * * *");
		System.out.println("*                   请输入您的操作编号 (数字)                    *");
		System.out.println("*                    1.注册当当网会员                                                      *");
		System.out.println("*                    2.会员登录                                                                 *");
		System.out.println("*                    3.选购商品                                                                 *");
		System.out.println("*                    4.查看购物车                                                             *");
		System.out.println("*                    5.结账                                                                        *");
		System.out.println("*                    0.退出系统                                                                 *");
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.print("您选择：");
		return 5;
	}
	
	/**
	 * 显示管理员菜单
	 */
	public static int showAdminMenu() {
		System.out.println("* * * * * * * * * 当当网购物管理系统（管理员）  * * * * * * * * * *");
		System.out.println("*                   请输入您的操作编号 (数字)                    *");
		System.out.println("*                    1.管理员登录                                                             *");
		System.out.println("*                    2.商品上架                                                                 *");
		System.out.println("*                    3.修改商品库存                                                          *");
		System.out.println("*                    0.退出系统                                                                 *");
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.print("您选择：");
		return 3;
	}
	
	/**
	 * 显示商品列表
	 * @param list 商品列表
	 */
	public static void displayGoodsList(List<Goods> list) {
		if (list == null) {
			System.out.println("还没有商品上架哦！");
			return;
		}
		System.out.println("+--------------------------------------------------------------+");
		System.out.println("|                          商品信息列表                                                  |");
		System.out.println("+---------+------------------------+--------------+------------+");
		System.out.println("| 商品编号  |         商品名称               |    商品单价      |   库存量      |");
		System.out.println("+---------+------------------------+--------------+------------+");
		for (Goods goods : list) {
			System.out.printf("|%9d|%21s|%15f|%12d|\n",goods.getGid(),goods.getGname(),
						goods.getUnitprice(),goods.getInventory());
			System.out.println("+---------+------------------------+--------------+------------+");
		}
	}
	
	/**
	 * 显示购物车的内容
	 * @param shoppingCart 购物车
	 * @param list 全部商品列表
	 */
	public static float displayShoppingCart(
			Map<Integer, Integer> shoppingCart, List<Goods> list) {
		if (shoppingCart.size() == 0) {
			System.out.println("您的购物车空空如也，快去购物填满它！");
			return 0;
		}
		if (list == null) {
			System.out.println("还没有商品上架哦！");
			return 0;
		}
		System.out.println("+----------------------------------------------------------------------------------------+");
		System.out.println("|                                         我的购物车                                                                            |");
		System.out.println("+---------+------------------------+--------------+------------+------------+------------+");
		System.out.println("| 商品编号  |         商品名称               |    库存量         |   购买数量    |    总价        |     状态       |");
		System.out.println("+---------+------------------------+--------------+------------+------------+------------+");
		float totalprice = 0.0f;  //所有商品总价格
		Set<Integer> goodsIdSet = shoppingCart.keySet();
		for (Goods goods : list) {
			for (Integer gid : goodsIdSet) {
				if (gid == goods.getGid()) {
					int usercount = shoppingCart.get(gid);         //用户要买多少
					int count = goods.getInventory() >= usercount  //商家能卖给用户多少
							? usercount : goods.getInventory();
					float subprice = goods.getUnitprice() * count;
					String state = count > 0 ? "有货" : "缺货";
					System.out.printf("|%9d|%21s|%14d|%12d|%12.2f|%11s|\n",
							goods.getGid(),goods.getGname(),goods.getInventory(),
							count,subprice,state);
					totalprice += subprice;  //累加价格
					System.out.println("+---------+------------------------+--------------+------------+------------+------------+");
				}
			}
		}
		System.out.printf("|      所有商品总价格：               |                      %12.2f                         |\n",totalprice);
		System.out.println("+----------------------------+-----------------------------------------------------------+");
		return totalprice;
	}
	
	
}
