package cn.edu.nuc.onlinestore.frame;

import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cn.edu.nuc.onlinestore.model.Cart;
import cn.edu.nuc.onlinestore.model.Goods;
import cn.edu.nuc.onlinestore.network.TCPClient;
import cn.edu.nuc.onlinestore.service.GoodsService;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 用户购物车详情窗体
 * @author 王凯
 *
 */
public class UserCartFrame extends JFrame {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2347670394073573576L;
	
	/**
	 * 主面板
	 */
	private JPanel contentPane;
	
	/**
	 * 表格
	 */
	private JTable table;
	
	/**
	 * 线程类的对象
	 */
	private TCPClient client;
	
	/**
	 * 购物车
	 */
	private Cart cart;
	
	/**
	 * 当前窗口
	 */
	private JFrame thisFrame;

	/**
	 * 创建窗体
	 * @param cart 购物车
	 * @param client 客户端线程对象
	 */
	public UserCartFrame(Cart cart, TCPClient client) {
		//设置图标
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				UserCartFrame.class.getResource("/img/user_login_logo.png")));
		
		//设置标题
		setTitle("我的购物车详情");
		
		//设置默认的关闭行为
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//设置窗口大小
		setBounds(100, 100, 591, 377);
		
		//设置主面板
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//设置窗体居中
		setLocationRelativeTo(null);
		
		//设置界面风格为操作系统默认风格
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);  
		
		//保存客户端线程类对象
		this.client = client;
		
		//保存购物车
		this.cart = cart;
		
		//保存当前窗口
		this.thisFrame = this;
		
		//设置表头
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("商品编号");
		model.addColumn("名称");
		model.addColumn("单价(￥)");
		model.addColumn("数量");
		model.addColumn("总价格(￥)");
		model.addColumn("商品详情");
		
		//创建表格并将其添加到主面板
		table = new JTable( model );
		table.setBounds(10, 38, 543, 184);
		contentPane.add( table );
		
		//设置表格内容
		updateGoodsList(cart, table);		
		
		//设置总商品数量标签
		JLabel total_count_label = new JLabel("总商品数量: " + cart.getTotalQuantity());
		total_count_label.setBounds(195, 232, 116, 15);
		contentPane.add(total_count_label);
		
		//设置总金额标签
		JLabel total_price_label = new JLabel("总金额: " + cart.getTotalPrice());
		total_price_label.setBounds(342, 232, 211, 15);
		contentPane.add(total_price_label);
		
		//结账按钮
		JButton pay_button = new JButton("结账");
		pay_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					GoodsService goodsService = new GoodsService(
							UserCartFrame.this.client.getClient());
					goodsService.sendPayRequest(UserCartFrame.this.cart);
					UserCartFrame.this.thisFrame.setVisible(false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		pay_button.setBounds(456, 283, 93, 23);
		contentPane.add(pay_button);
	}
	
	/**
	 * 更新购物车列表
	 * @param goods 货物列表
	 * @param model 表格
	 */
	public void updateGoodsList(Cart cart, JTable table) {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		removeAllRows(model);  //清空列表
		addInforToRows(cart,model);
		table.updateUI();
	}
	
	/**
	 * 移除表中的所有行
	 * @param model 表格内容
	 */
	public static void removeAllRows(DefaultTableModel model) {
		while(model.getRowCount()>0){
			model.removeRow(model.getRowCount()-1);
		}
	}
	
	/**
	 * 把购物车信息加到列表中
	 * @param cart 购物车
	 * @param model 表格
	 */
	private void addInforToRows(Cart cart,DefaultTableModel model) {
		Map<Goods, Integer> shoppingCart = cart.getShoppingCart();
		Set<Goods> goodsSet = shoppingCart.keySet();
		for (Goods goods : goodsSet) {
			int count = shoppingCart.get(goods).intValue();
			model.addRow(
				new String[]{
					Integer.toString(goods.getGid()),          //商品id
					goods.getGoodsName(),                      //商品名称
					Float.toString(goods.getPrice()),          //单价
					Integer.toString(count) ,                  //购买数量
					Float.toString(count * goods.getPrice()),  //单项总价
					goods.getNote()                            //详情
				}
			);
		}
	}
}