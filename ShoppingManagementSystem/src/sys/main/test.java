package sys.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sms.entity.Goods;
import sms.menu.Menu;

public class test {
	public static void main(String[] args) {
		List<Goods> list = new ArrayList<>();
		Goods goods = new Goods();
		goods.setGid(1);
		goods.setGname("明信片1");
		goods.setInventory(13);
		goods.setUnitprice(10.0f);
		list.add(goods);
		
		Goods goods1 = new Goods();
		goods1.setGid(2);
		goods1.setGname("明信片2");
		goods1.setInventory(13);
		goods1.setUnitprice(15.0f);
		list.add(goods1);
		
		Map<Integer, Integer> shoppingCart = new HashMap<>();
		shoppingCart.put(1, 10);
		shoppingCart.put(2, 3);
		
		Menu.displayShoppingCart(shoppingCart, list);
	}
}
