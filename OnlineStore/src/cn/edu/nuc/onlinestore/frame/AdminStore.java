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

import cn.edu.nuc.onlinestore.io.IOUtility;
import cn.edu.nuc.onlinestore.model.Admin;
import cn.edu.nuc.onlinestore.model.Goods;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理员主界面
 * @author 王凯
 *
 */
public class AdminStore extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6143120919875553327L;
	
	/**
	 * 主面板
	 */
	private JPanel contentPane;
	
	/**
	 * 商品编号输入框
	 */
	private JTextField goodsid;
	
	/**
	 * 上一个窗口
	 */
	private JFrame perviousFrame;
	
	/**
	 * 本窗口
	 */
	private JFrame thisFrame;
	
	/**
	 * 商品列表
	 */
	private JTable table;
	
	/**
	 * 商品列表内部的模型
	 */
	private DefaultTableModel model;
	
	public AdminStore(JFrame perviousFrame, Admin admin) {
		
		//设置图标
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				AdminStore.class.getResource("/img/admin_llogin_logo.png")));
		
		//设置标题
		setTitle("中北商场后台管理系统--当前用户:" + admin.getAdminName());
		
		//设置默认的关闭行为
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//设置窗口大小
		setBounds(100, 100, 732, 467);
		
		//设置窗体居中
		setLocationRelativeTo(null);
		
		//设置界面风格为操作系统默认风格
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);  
		
		//保存前一个窗口和当前窗口的实例，注销时用
		this.perviousFrame = perviousFrame; 
		thisFrame = this;
		
		//设置主面板及其布局
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//设置货物内容面板
		JPanel content = new JPanel();
		content.setBounds(10, 78, 696, 341);
		content.setLayout(new GridLayout(1, 1, 0, 0));
		
		//设置货物信息列表的表头
		model = new DefaultTableModel();
		model.addColumn("商品编号");
		model.addColumn("名称");
		model.addColumn("单价(人民币)");
		model.addColumn("库存");
		model.addColumn("简介");
		
		//将所有货物信息添加到列表中
		addGoodsToRows(IOUtility.getAllGoods(), model);
		
		//初始化表格并将其加入到主面板
		table = new JTable(model);
		JScrollPane pane = new JScrollPane(table);
		content.add(pane);
		contentPane.add(content);
		
		//添加商品按钮
		JButton addGoodsButton = new JButton("添加商品");
		addGoodsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminAdd add = new AdminAdd(table);
				add.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				add.setVisible(true);
			}
		});
		addGoodsButton.setBounds(386, 45, 93, 23);
		contentPane.add(addGoodsButton);
		
		//修改商品按钮
		JButton modifyGoodsButton = new JButton("修改商品");
		modifyGoodsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table.getSelectedRow();
				if (selectRow == -1) { //用户未做选择
					JOptionPane.showMessageDialog(null, "您没有选择商品，请您选中某个商品后再单击此按钮！", 
							"提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				int num = Integer.parseInt(
						table.getValueAt(table.getSelectedRow(),0).toString());
				AdminUpdate u = new AdminUpdate(
						IOUtility.getGoodsById(num),table);
				u.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				u.setVisible(true);
			}
		});
		modifyGoodsButton.setBounds(489, 45, 93, 23);
		contentPane.add(modifyGoodsButton);
		
		//删除选中商品
		JButton deleteGoodsButton = new JButton("删除选中商品");
		deleteGoodsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//得到当前选中商品项
				int selectRow = table.getSelectedRow();
				if (selectRow == -1) { //用户未做选择
					JOptionPane.showMessageDialog(null, "您没有选择商品，请您选中某个商品后再单击此按钮！", 
							"提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				int num = Integer.parseInt(
						table.getValueAt(table.getSelectedRow(),0).toString());
				Goods goods = IOUtility.getGoodsById(num);
				int select = JOptionPane.showConfirmDialog(null, "确定要删除" + goods.getGoodsName() + "么?", 
						"提示", JOptionPane.YES_NO_OPTION);
				if (select == 0) { //选择了是
					IOUtility.deleteGoods(goods.getGid());
				}
				updateGoodsList(IOUtility.getAllGoods(), model); //更新列表
				table.updateUI();  //刷新显示
			}
		});
		deleteGoodsButton.setBounds(587, 45, 119, 23);
		contentPane.add(deleteGoodsButton);
		
		//注销按钮
		JButton exitButton = new JButton("退出登录");
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int select = JOptionPane.showConfirmDialog(null, "您要注销吗？", 
						"提示", JOptionPane.YES_NO_OPTION);
				if (select == 0) {  //用户选择了是
					AdminStore.this.perviousFrame.setVisible(true);
					thisFrame.setVisible(false);
				}
			}
		});
		exitButton.setBounds(613, 6, 93, 23);
		contentPane.add(exitButton);
		
		//设置窗口关闭时的行为
		thisFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int select = JOptionPane.showConfirmDialog(
						null, "要关闭系统吗，服务器会随之停止运行", 
						"提示", JOptionPane.YES_NO_CANCEL_OPTION);
				if(select == 0) { //用户选择是
					System.exit(0);
				}
			}
		});
		
		//在线用户数
		JLabel onlineUserCount = new JLabel("当前在线用户数: 0");
		onlineUserCount.setBounds(10, 10, 162, 15);
		contentPane.add(onlineUserCount);
		
		//商品编号标签
		JLabel goodsIdLabel = new JLabel("商品编号:");
		goodsIdLabel.setBounds(10, 53, 59, 15);
		contentPane.add(goodsIdLabel);
		
		//商品编号输入框
		goodsid = new JTextField();
		goodsid.setBounds(79, 50, 104, 21);
		contentPane.add(goodsid);
		goodsid.setColumns(10);
		
		//搜索按钮
		JButton searchButton = new JButton("搜索");
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (goodsid.getText() == null || goodsid.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "您没有输入任何内容，默认显示全部商品信息！");
					updateGoodsList(IOUtility.getAllGoods(), model);
					table.updateUI();
					return;
				}
				int id = Integer.parseInt(goodsid.getText());
				Goods goods = IOUtility.getGoodsById(id);
				if (goods == null) {
					JOptionPane.showMessageDialog(null, "没有找到编号为" + id + "的商品！");
					updateGoodsList(IOUtility.getAllGoods(), model);
					table.updateUI();
					return;
				}
				List<Goods> gList = new ArrayList<Goods>();
				gList.add(goods);
				updateGoodsList(gList, model);
				table.updateUI();
			}
		});
		searchButton.setBounds(193, 49, 93, 23);
		contentPane.add(searchButton);
	}
	
	/**
	 * 更新货物列表
	 * @param goods 货物列表
	 * @param model 表格
	 */
	public static void updateGoodsList(List<Goods> goods, DefaultTableModel model) {
		removeAllRows(model);  //清空列表
		addGoodsToRows(goods, model);
	}
	
	/**
	 * 移除表中的所有行
	 * @param model
	 */
	public static void removeAllRows(DefaultTableModel model) {
		while(model.getRowCount()>0){
			model.removeRow(model.getRowCount()-1);
		}
	}

	/**
	 * 把货物添加到列表中
	 * @param goods 商品
	 * @param model 表格
	 */
	private static void addGoodsToRows(List<Goods> goods, DefaultTableModel model) {
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

}
