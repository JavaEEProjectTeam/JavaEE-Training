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
 * 管理员添加商品窗口
 * @author 王凯
 *
 */
public class AdminAdd extends JFrame {

	/**
	 * 序列化Id
	 */
	private static final long serialVersionUID = -9082084528551688796L;
	
	/**
	 * 当前窗口
	 */
	private JFrame thisFrame;
	
	/**
	 * 主面板
	 */
	private JPanel contentPane;
	
	/**
	 * 商品名称
	 */
	private JTextField goodsName;
	
	/**
	 * 商品单价
	 */
	private JTextField unitPrice;
	
	/**
	 * 库存量
	 */
	private JTextField count;
	
	/**
	 * 商品简介
	 */
	private JTextArea note;
	
	/**
	 * 表格
	 */
	private JTable table;

	/**
	 * 创建窗体
	 */
	public AdminAdd(JTable table) {
		
		//设置图标
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				AdminAdd.class.getResource("/img/admin_llogin_logo.png")));
		
		//设置标题
		setTitle("添加商品");
		
		//设置默认的关闭行为
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//设置窗体默认的关闭行为
		setBounds(100, 100, 450, 405);
		
		//保存当前窗口的实例
		thisFrame = this;
		
		//保存表格对象，用于刷新
		this.table = table;
		
		//设置主窗体
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
		
		//商品名称输入提示语
		JLabel goodsname_label = new JLabel("商品名称:");
		goodsname_label.setBounds(35, 57, 67, 15);
		contentPane.add(goodsname_label);
		
		//商品名称输入框
		goodsName = new JTextField();
		goodsName.setBounds(99, 54, 269, 21);
		contentPane.add(goodsName);
		goodsName.setColumns(10);
		
		//商品单价输入提示语
		JLabel unitprice_label = new JLabel("单价:");
		unitprice_label.setBounds(35, 100, 54, 15);
		contentPane.add(unitprice_label);
		
		//商品单价输入框
		unitPrice = new JTextField();
		unitPrice.setBounds(99, 97, 212, 21);
		contentPane.add(unitPrice);
		unitPrice.setColumns(10);
		
		//单价的计量单位
		JLabel prickle = new JLabel("单位:元");
		prickle.setBounds(321, 100, 54, 15);
		contentPane.add(prickle);
		
		//商品库存输入提示语
		JLabel count_label = new JLabel("数量:");
		count_label.setBounds(35, 143, 54, 15);
		contentPane.add(count_label);
		
		//商品库存输入框
		count = new JTextField();
		count.setBounds(99, 140, 269, 21);
		contentPane.add(count);
		count.setColumns(10);
		
		//商品简介输入提示语
		JLabel note_label = new JLabel("简介:");
		note_label.setBounds(35, 188, 54, 15);
		contentPane.add(note_label);
		
		//商品简介输入框
		note = new JTextArea();
		note.setBounds(99, 184, 269, 103);
		contentPane.add(note);
		
		//确认添加按钮
		JButton confirmButton = new JButton("确定添加");
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
				
				//全部通过，开始执行业务逻辑
				Goods newgoods = new Goods();
				newgoods.setGid(IOUtility.getNextGoodsId());
				newgoods.setGoodsName(goodsName.getText());
				newgoods.setPrice(Float.parseFloat(unitPrice.getText()));
				newgoods.setInventory(Integer.parseInt(count.getText()));
				newgoods.setNote(note.getText());
				IOUtility.writeGoodsToFile(newgoods);
				thisFrame.setVisible(false);
				AdminStore.updateGoodsList(
						IOUtility.getAllGoods(), 
						(DefaultTableModel)AdminAdd.this.table.getModel());
				AdminAdd.this.table.updateUI();
			}
		});
		confirmButton.setBounds(275, 310, 93, 23);
		contentPane.add(confirmButton);
		
		//关闭窗口时的行为
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				((AdminAdd)e.getSource()).setVisible(false);
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
