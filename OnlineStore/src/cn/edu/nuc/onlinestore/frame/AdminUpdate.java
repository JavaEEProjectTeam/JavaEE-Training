package cn.edu.nuc.onlinestore.frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cn.edu.nuc.onlinestore.io.IOUtility;
import cn.edu.nuc.onlinestore.model.Goods;
import cn.edu.nuc.onlinestore.util.RegexUtility;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 管理员修改商品窗口
 * @author 王凯
 *
 */
public class AdminUpdate extends JFrame {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -594504024620937054L;
	
	/**
	 * 当前窗体
	 */
	private JFrame thisFrame;
	
	/**
	 * 主面板
	 */
	private JPanel contentPane;
	
	/**
	 * 商品名称输入框
	 */
	private JTextField goodsName;
	
	/**
	 * 商品单价输入框
	 */
	private JTextField unitPrice;
	
	/**
	 * 商品库存输入框
	 */
	private JTextField count;
	
	/**
	 * 商品简介输入区
	 */
	private JTextArea note;
	
	/**
	 * 主窗体的表格
	 */
	private JTable table;
	
	/**
	 * 保存商品信息，用于更新
	 */
	private Goods goods;

	/**
	 * 创建窗体
	 * @param goods 商品信息
	 * @param table 表格
	 */
	public AdminUpdate(Goods goods, JTable table) {
		//设置图标
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				AdminUpdate.class.getResource("/img/admin_llogin_logo.png")));
		
		//设置窗口标题
		setTitle("修改商品" + goods.getGoodsName());
		
		//设置默认的窗口关闭方式
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//设置窗口大小
		setBounds(100, 100, 450, 405);
		
		//设置主面板
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//设置窗体居中
		setLocationRelativeTo(null);
		
		//保存当前窗口
		thisFrame = this;
		
		//保存表格的实例
		this.table = table;
		
		//保存商品信息
		this.goods = goods;
		
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
		
		//商品名称输入框
		goodsName = new JTextField();
		goodsName.setBounds(99, 54, 269, 21);
		contentPane.add(goodsName);
		goodsName.setColumns(10);
		goodsName.setText(goods.getGoodsName());
		
		//商品单价标签
		JLabel unitprice_label = new JLabel("单价:");
		unitprice_label.setBounds(35, 100, 54, 15);
		contentPane.add(unitprice_label);
		
		//商品单价输入框
		unitPrice = new JTextField();
		unitPrice.setBounds(99, 97, 212, 21);
		contentPane.add(unitPrice);
		unitPrice.setColumns(10);
		unitPrice.setText(Float.toString(goods.getPrice()));
		
		//单价计量单位标签
		JLabel prickle = new JLabel("单位:元");
		prickle.setBounds(321, 100, 54, 15);
		contentPane.add(prickle);
		
		//商品数量标签
		JLabel count_label = new JLabel("数量:");
		count_label.setBounds(35, 143, 54, 15);
		contentPane.add(count_label);
		
		//商品数量输入框
		count = new JTextField();
		count.setBounds(99, 140, 212, 21);
		contentPane.add(count);
		count.setColumns(10);
		count.setText(Integer.toString(goods.getInventory()));
		
		//商品简介标签
		JLabel note_label = new JLabel("简介:");
		note_label.setBounds(35, 188, 54, 15);
		contentPane.add(note_label);
		
		//商品简介输入框
		note = new JTextArea();
		note.setBounds(99, 184, 269, 103);
		contentPane.add(note);
		note.setText(goods.getNote());
		
		//确认修改按钮
		JButton confirmButton = new JButton("确定修改");
		confirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String goodsname = goodsName.getText().trim();
				String unitprice = unitPrice.getText().trim();
				String goodscount = count.getText().trim();
				
				//空值判断
				if (!checkNull(goodsname) || !checkNull(unitprice) || !checkNull(goodscount)) {
					JOptionPane.showMessageDialog(null, "商品名称、商品单价、库存是必填项！", 
							"提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//数值判断
				if (!RegexUtility.isNumber(unitprice)) {
					JOptionPane.showMessageDialog(null, "商品单价只能填写数字（可以写小数）！", 
							"提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if (!RegexUtility.isInteger(goodscount)) {
					JOptionPane.showMessageDialog(null, "商品库存只能填写整数！", 
							"提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//修改实体类存储的信息
				AdminUpdate.this.goods.setGid(AdminUpdate.this.goods.getGid());
				AdminUpdate.this.goods.setGoodsName(goodsName.getText());
				AdminUpdate.this.goods.setPrice(Float.parseFloat(unitPrice.getText()));
				AdminUpdate.this.goods.setInventory(Integer.parseInt(count.getText()));
				AdminUpdate.this.goods.setNote(AdminUpdate.this.note.getText());
				
				//同步修改到文件
				IOUtility.writeGoodsToFile(AdminUpdate.this.goods);
				
				//隐藏当前窗体
				thisFrame.setVisible(false);
				
				//更新上一界面显示
				AdminStore.updateGoodsList(
						IOUtility.getAllGoods(), 
						(DefaultTableModel)AdminUpdate.this.table.getModel());
				AdminUpdate.this.table.updateUI();
			}
		});
		confirmButton.setBounds(275, 310, 93, 23);
		contentPane.add(confirmButton);
		
		//数量输入限制提示标签
		JLabel count_constraint_label = new JLabel(">=0");
		count_constraint_label.setBounds(321, 143, 43, 15);
		contentPane.add(count_constraint_label);
		
		//设置窗口关闭时的行为
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				((AdminUpdate)e.getSource()).setVisible(false);
			}
		});
	}
	
	/**
	 * 空值检验
	 * @param string 待检验字符串
	 * @return 检验结果
	 */
	private static boolean checkNull(String string) {
		if (string == null || "".equals(string)) {
			return false;
		}
		return true;
	}
}
