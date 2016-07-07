package cn.edu.nuc.onlinestore.frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cn.edu.nuc.onlinestore.model.Cart;
import cn.edu.nuc.onlinestore.model.Goods;

import java.awt.Toolkit;
import java.util.Map;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 查看商品详情并添加到购物车窗体
 * @author 王凯
 *
 */
public class UserGoods extends JFrame {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6285952423541502980L;
	
	/**
	 * 主窗体
	 */
	private JPanel contentPane;
	
	/**
	 * 商品数量输入框
	 */
	private JTextField quantity;
	
	/**
	 * 购物车
	 */
	private Cart cart;
	
	/**
	 * 商品信息
	 */
	private Goods goods;
	
	/**
	 * 
	 */
	private JLabel label;
	
	/**
	 * 当前窗口
	 */
	private JFrame thisFrame;

	/**
	 * 创建窗体
	 * @param goods 商品信息
	 * @param cart 购物车
	 * @param label 标签
	 */
	public UserGoods(Goods goods, Cart cart, JLabel label) {
		
		//设置标题
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				UserGoods.class.getResource("/img/user_login_logo.png")));
		
		//设置标题
		setTitle("商品详情");
		
		//设置默认的关闭行为
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//设置窗口大小
		setBounds(100, 100, 450, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//保存当前窗口
		thisFrame = this;
		
		//保存购物车
		this.cart = cart;
		
		//保存商品信息
		this.goods = goods;
		
		//标签
		this.label = label;
		
		//设置窗体居中
		setLocationRelativeTo(null);
		
		//设置界面风格为操作系统默认风格
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);  
		
		//商品名称标签
		JLabel goodsname_label = new JLabel("商品名称:");
		goodsname_label.setBounds(23, 57, 66, 15);
		contentPane.add(goodsname_label);
		
		//单价标签
		JLabel unitprice_label = new JLabel("单价:");
		unitprice_label.setBounds(35, 100, 54, 15);
		contentPane.add(unitprice_label);
		
		//单价计量单位标签
		JLabel prickle = new JLabel("单位:元");
		prickle.setBounds(321, 100, 54, 15);
		contentPane.add(prickle);
		
		//简介标签
		JLabel note_label = new JLabel("简介:");
		note_label.setBounds(35, 141, 44, 46);
		contentPane.add(note_label);
		
		//加入购物车标签
		JButton addButton = new JButton("加入购物车");
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Map<Goods, Integer> shoppingCart = UserGoods.this.cart.getShoppingCart();
				int count = Integer.parseInt(UserGoods.this.quantity.getText());
				UserGoods.this.cart.add(UserGoods.this.goods, count);
				UserGoods.this.label.setText("购物车: " + shoppingCart.size() + "件商品");
				UserGoods.this.label.updateUI();
				UserGoods.this.thisFrame.setVisible(false); //隐藏窗口
			}
		});
		addButton.setBounds(175, 243, 126, 23);
		contentPane.add(addButton);
		
		//商品名称显示处
		JLabel goodsName = new JLabel(goods.getGoodsName());
		goodsName.setBounds(99, 57, 162, 15);
		contentPane.add(goodsName);
		
		//单价显示处
		JLabel unitprice = new JLabel(Float.toString(goods.getPrice()));
		unitprice.setBounds(99, 100, 54, 15);
		contentPane.add(unitprice);
		
		//商品详情显示处
		JLabel note = new JLabel(goods.getNote());
		note.setBounds(99, 141, 251, 46);
		contentPane.add(note);
		
		//购买数量输入框
		quantity = new JTextField();
		quantity.setText("0");
		quantity.setBounds(99, 244, 66, 21);
		contentPane.add(quantity);
		quantity.setColumns(10);
		
		//购买数量标签
		JLabel quantity_label = new JLabel("购买数量:");
		quantity_label.setBounds(35, 247, 71, 15);
		contentPane.add(quantity_label);
		
		//库存显示处
		JLabel inventory_label = new JLabel("库存: " + goods.getInventory());
		inventory_label.setBounds(99, 273, 135, 15);
		contentPane.add(inventory_label);
		
		//设置窗口关闭时的触发操作
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				((UserGoods)e.getSource()).setVisible(false);
			}
		});
	}
}
