package sms.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sms.entity.Goods;
import sms.menu.Menu;
import sms.util.InitialTools;
import sms.util.ObjectPersistent;

/**
 * 商品业务逻辑
 * @author 王凯
 *
 */
public class GoodsService {
	
	/**
	 * 得到商品全部列表
	 * @return 商品列表
	 */
	public List<Goods> getAllGoods() {
		List<Goods> list = null;
		ObjectPersistent<List<Goods>> op = new ObjectPersistent<>();
		if (new File(InitialTools.goodsFilePath).length() == 0) {
			return list;  //空文件返回空集合
		}
		try {
			list = op.getObject(InitialTools.goodsFilePath);
		} catch (Exception e) {
			System.out.println("无法获取文件列表！");
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 商品上架
	 */
	public void addGoods() {
		System.out.println("-------------------商品上架----------------------");
		
		//获取商品列表
		ObjectPersistent<List<Goods>> op = new ObjectPersistent<>();
		List<Goods> list = null;
		if (new File(InitialTools.goodsFilePath).length() != 0) {
			try {
				list = op.getObject(InitialTools.goodsFilePath);
			} catch (Exception e) {
				System.out.println("获取商品列表失败！");
				e.printStackTrace();
			}
		}
		else {
			list = new ArrayList<>();
		}
		
		Scanner scanner = new Scanner(System.in);
		while (true) {
			Goods goods = new Goods();
			System.out.print("请输入商品名称(不超过10字)：");
			goods.setGname(scanner.next());
			
			if (goods.getGname().length() >= 20) {
				goods.setGname(goods.getGname().substring(0, 20));
			}
			
			System.out.println("请输入商品单价(可以是小数)：");
			goods.setUnitprice(scanner.nextFloat());
			
			System.out.println("请输入库存量");
			goods.setInventory(scanner.nextInt());
			
			goods.setGid(list.size() + 1);
			
			list.add(goods);
			
			System.out.println("是否要继续输入？（请输入Y/y或N/n）");
			String choice = scanner.next();
			if (choice.equalsIgnoreCase("N")) {
				break;
			}
		}
		
		try {
			op.persistObject(list, InitialTools.goodsFilePath);
		} catch (Exception e) {
			System.out.println("信息存储失败！");
			e.printStackTrace();
		}
		
		System.out.println("所有商品上架成功！信息如下");
		Menu.displayGoodsList(list);
	}
	
	/**
	 * 修改商品库存
	 */
	public void modifyGoodsInventory() {
		System.out.println("-------------------修改商品库存----------------------");
		boolean changed = false; //是否修改信息
		
		//获取商品列表
		ObjectPersistent<List<Goods>> op = new ObjectPersistent<>();
		List<Goods> list = null;
		
		if (new File(InitialTools.goodsFilePath).length() == 0) {
			System.out.println("没有商品可供修改!");
			return;
		}
		
		try {
			list = op.getObject(InitialTools.goodsFilePath);
		} catch (Exception e) {
			System.out.println("获取商品列表失败！");
			e.printStackTrace();
			return;
		}
		
		System.out.println("所有商品信息如下");
		Menu.displayGoodsList(list);
		
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("请输入待修改商品的编号：");
			int gid = scanner.nextInt();
			
			for (Goods goods : list) {
				if (goods.getGid() == gid) {
					System.out.print("请输入新的库存量：");
					int inventory = scanner.nextInt();
					if (inventory >= 0) {
						goods.setInventory(inventory);
						changed = true;
						break;
					}
					System.out.println("您输入的值有误，不予更新！");
				}
			}
			if (changed == false) {
				System.out.println("没有找到编号为" + gid 
						+ "的商品信息或者您的输入有误，请检查您的输入是否正确！");
			}
			System.out.println("是否要继续输入？（请输入Y/y或N/n）");
			String choice = scanner.next();
			if (choice.equalsIgnoreCase("N")) {
				break;
			}
		}
		
		if (changed == false) { //如果没有修改任何信息，就不需要进行持久化操作了
			return;
		}
		
		//将修改同步到磁盘上
		try {
			op.persistObject(list, InitialTools.goodsFilePath);
		} catch (Exception e) {
			System.out.println("信息同步失败！");
			e.printStackTrace();
			return;
		}
		
		//输出修改信息
		System.out.println("所有修改操作成功！所有商品信息如下");
		Menu.displayGoodsList(list);
	}
	
}
