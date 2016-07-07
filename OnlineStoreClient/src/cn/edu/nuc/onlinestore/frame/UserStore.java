package cn.edu.nuc.onlinestore.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cn.edu.nuc.onlinestore.model.Cart;
import cn.edu.nuc.onlinestore.model.Goods;
import cn.edu.nuc.onlinestore.network.TCPClient;
import cn.edu.nuc.onlinestore.service.GoodsService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 用户登陆后的商店主界面
 * @author 王凯
 *
 */
public class UserStore extends JFrame {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 9145645020798165316L;
	
	/**
	 * 主面板
	 */
	private JPanel contentPane;
	
	/**
	 * 输入商品名称的文本框
	 */
	private JTextField goodsNameText;
	
	/**
	 * 商品列表
	 */
	private List<Goods> goodsList;
	
	/**
	 * 当前窗体
	 */
	private JFrame thisFrame;
	
	/**
	 * 客户端线程
	 */
	private TCPClient client;
	
	/**
	 * 表格
	 */
	private JTable table;
	
	/**
	 * 购物车
	 */
	private Cart cart = new Cart();
	
	/**
	 * 购物车标签
	 */
	private JLabel shoppingcart_label;

	/**
	 * 创建窗体
	 */
	public UserStore(TCPClient client, String username
			, List<Goods> goodsList) {
		
		//设置图标
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				UserStore.class.getResource("/img/user_login_logo.png")));
		
		//设置标题
		setTitle("中北在线商场--当前用户:" + username);
		
		//设置默认的关闭行为
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//设置窗口大小
		setBounds(100, 100, 732, 467);
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
		
		//保存当前窗体
		thisFrame = this;
		
		//创建并设置主面板
		JPanel panel = new JPanel();
		panel.setBounds(10, 78, 696, 341);
		panel.setLayout(new GridLayout(1, 1, 0, 0));
		
		//更新商品清单
		this.goodsList = goodsList;
		
		//设置表格内容
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("商品编号");
		model.addColumn("名称");
		model.addColumn("单价(人民币)");
		model.addColumn("库存");
		model.addColumn("商品描述");
		table = new JTable(model);
		updateGoodsList(goodsList);	
		JScrollPane pane = new JScrollPane( table );
		panel.add(pane);
		contentPane.add(panel);
		
		//查看商品详情按钮
		JButton viewGoodsInfo = new JButton("查看商品详细信息(或双单击商品列)");
		viewGoodsInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//先检查是否选中列
				int selectRow = table.getSelectedRow();
				if (selectRow == -1) { //用户未做选择
					JOptionPane.showMessageDialog(null, 
							"您没有选择商品，请您选中某个商品后再单击此按钮！", 
							"提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//找出id对应的商品并更新显示
				int num = Integer.parseInt(
						table.getValueAt(table.getSelectedRow(),0).toString());
				Goods goods = getGoodsById(UserStore.this.goodsList, num);
				UserGoods d = new UserGoods(goods, cart, shoppingcart_label);
				d.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				d.setVisible(true);
			}
		});
		viewGoodsInfo.setBounds(407, 45, 299, 23);
		contentPane.add(viewGoodsInfo);
		
		//退出登录按钮
		JButton exitButton = new JButton("退出登录");
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int select = JOptionPane.showConfirmDialog(null, "您要退出吗？", 
						"提示", JOptionPane.YES_NO_OPTION);
				if (select == 0) {  //用户选择了是
					thisFrame.setVisible(false);
					try {
						UserStore.this.client.stopClient();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					System.exit(0);
				}
			}
		});
		exitButton.setBounds(613, 6, 93, 23);
		contentPane.add(exitButton);
		
		//商品名称标签
		JLabel goodsNameLabel = new JLabel("商品名称:");
		goodsNameLabel.setBounds(10, 53, 65, 15);
		contentPane.add(goodsNameLabel);
		
		//商品名称输入框
		goodsNameText = new JTextField();
		goodsNameText.setBounds(85, 46, 104, 21);
		contentPane.add(goodsNameText);
		goodsNameText.setColumns(10);
		
		//搜索按钮
		JButton searchButtton = new JButton("搜索");
		searchButtton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String text = goodsNameText.getText().trim();
				GoodsService goodsService = new GoodsService(
						UserStore.this.client.getClient());
				try {
					goodsService.searchGoods(text);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		searchButtton.setBounds(197, 45, 93, 23);
		contentPane.add(searchButtton);
		
		//购物车信息标签
		this.shoppingcart_label = shoppingcart_label;
		shoppingcart_label = new JLabel("购物车: " 
					+ cart.getShoppingCart().size() + "件商品");
		shoppingcart_label.setBounds(10, 10, 124, 15);
		contentPane.add(shoppingcart_label);
		
		//查看购物车按钮
		JButton viewShoppingCartButton = new JButton("查看购物车");
		viewShoppingCartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserCartFrame cf = new UserCartFrame(
						cart, UserStore.this.client);
				cf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				cf.setVisible(true);
			}
		});
		viewShoppingCartButton.setBounds(116, 6, 110, 23);
		contentPane.add(viewShoppingCartButton);
		
		//保存客户端线程对象
		this.client = client;
		
		//在窗口关闭时通知服务器自己已经下线
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int select = JOptionPane.showConfirmDialog(null, 
						"您要退出吗？", "提示", JOptionPane.YES_NO_OPTION);
				if (select == 0) {  //用户选择了是
					thisFrame.setVisible(false);
					try {
						UserStore.this.client.stopClient();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				System.exit(0);
			}
		});
	}
	
	/**
	 * 按照商品id从商品列表中取出商品信息
	 * @param goodsList 商品列表
	 * @param gid 商品id
	 * @return 商品信息
	 */
	private Goods getGoodsById(List<Goods> goodsList, int gid) {
		for (Goods goods : goodsList) {
			if (goods.getGid() == gid) {
				return goods;
			}
		}
		return null;
	}
	
	/**
	 * 更新货物列表
	 * @param goods 货物列表
	 * @param model 表格
	 */
	public void updateGoodsList(List<Goods> goods) {
		DefaultTableModel model = (DefaultTableModel)UserStore.this.table.getModel();
		removeAllRows(model);  //清空列表
		addGoodsToRows(goods, model);
		table.updateUI();
	}
	
	/**
	 * 移除表中的所有行
	 * @param model 表格内容
	 */
	public void removeAllRows(DefaultTableModel model) {
		while(model.getRowCount() > 0){
			model.removeRow(model.getRowCount() - 1);
		}
	}

	/**
	 * 把货物添加到列表中
	 * @param goods 商品
	 * @param model 表格
	 */
	private void addGoodsToRows(List<Goods> goods, 
			DefaultTableModel model) {
		for (Goods g : goods) {
			model.addRow(
				new String[]{
						Integer.toString(g.getGid()),      //商品id
						g.getGoodsName(),                  //商品名称
						Float.toString(g.getPrice()),      //单价
						Integer.toString(g.getInventory()),//库存
						g.getNote()                        //简介
				}
			);
		}
	}
	
	/**
	 * 弹出对话框提示用户
	 * @param message 消息
	 */
	public static void callUser(String message) {
		JOptionPane.showMessageDialog(null, message, "提示"
				, JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * 清空购物车并刷新显示
	 */
	public void cleanCart() {
		cart.getShoppingCart().clear();
		shoppingcart_label.setText("购物车: " 
				+ cart.getShoppingCart().size() + "件商品");
		shoppingcart_label.updateUI();
	}
}
