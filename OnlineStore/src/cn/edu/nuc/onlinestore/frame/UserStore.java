package cn.edu.nuc.onlinestore.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserStore extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserStore frame = new UserStore();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserStore() {
		setTitle("中北在线商场--当前用户:李四");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 732, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 78, 696, 341);
		panel.setLayout(new GridLayout(1, 1, 0, 0));
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("商品编号");
		model.addColumn("名称");
		model.addColumn("单价(人民币)");
		model.addColumn("库存");
		//model.addColumn("操作");
		
		model.addRow(new String[]{"1","水杯","15.00","200"});
		model.addRow(new String[]{"2","水瓶","35.00","200"});
		
		model.addRow(new String[]{"3","天堂伞","55.00","200"});
		model.addRow(new String[]{"4","男袜","8.00","200"});
		model.addRow(new String[]{"5","农夫山泉","2.00","200"});
		model.addRow(new String[]{"6","毛巾","9.90","200"});
		model.addRow(new String[]{"7","牙刷","15.00","200"});
		model.addRow(new String[]{"8","洗发水","15.00","200"});
		model.addRow(new String[]{"9","牙膏","15.00","200"});
		model.addRow(new String[]{"10","海尔全自动洗衣机","2,699.00","200"});
		JTable table = new JTable( model );
		
		JScrollPane pane = new JScrollPane( table );
		
		panel.add(pane);
		contentPane.add(panel);
		
		JButton button_2 = new JButton("查看商品详细信息(或双单击商品列)");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserGoods d = new UserGoods();
				d.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				d.setVisible(true);
			}
		});
		button_2.setBounds(407, 45, 299, 23);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("退出登录");
		button_3.setBounds(613, 6, 93, 23);
		contentPane.add(button_3);
		
		JLabel lblid = new JLabel("商品名称:");
		lblid.setBounds(10, 53, 65, 15);
		contentPane.add(lblid);
		
		textField = new JTextField();
		textField.setBounds(85, 46, 104, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button_4 = new JButton("搜索");
		button_4.setBounds(197, 45, 93, 23);
		contentPane.add(button_4);
		
		JLabel label = new JLabel("购物车: 8 件商品");
		label.setBounds(10, 10, 124, 15);
		contentPane.add(label);
		
		JButton button_5 = new JButton("查看购物车");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				UserCartFrame cf = new UserCartFrame();
				cf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				cf.setVisible(true);
			}
		});
		button_5.setBounds(116, 6, 110, 23);
		contentPane.add(button_5);
	}
}
