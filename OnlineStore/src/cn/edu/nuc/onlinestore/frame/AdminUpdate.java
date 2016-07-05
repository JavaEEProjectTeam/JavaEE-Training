package cn.edu.nuc.onlinestore.frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Toolkit;

public class AdminUpdate extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -594504024620937054L;
	private JPanel contentPane;
	private JTextField goodsName;
	private JTextField unitPrice;
	private JTextField count;

	/**
	 * Create the frame.
	 */
	public AdminUpdate() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminUpdate.class.getResource("/img/admin_llogin_logo.png")));
		setTitle("修改商品");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 405);
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
		
		JLabel goodsname_label = new JLabel("商品名称:");
		goodsname_label.setBounds(23, 57, 66, 15);
		contentPane.add(goodsname_label);
		
		goodsName = new JTextField();
		goodsName.setBounds(99, 54, 269, 21);
		contentPane.add(goodsName);
		goodsName.setColumns(10);
		
		JLabel unitprice_label = new JLabel("单价:");
		unitprice_label.setBounds(35, 100, 54, 15);
		contentPane.add(unitprice_label);
		
		unitPrice = new JTextField();
		unitPrice.setBounds(99, 97, 212, 21);
		contentPane.add(unitPrice);
		unitPrice.setColumns(10);
		
		JLabel prickle = new JLabel("单位:元");
		prickle.setBounds(321, 100, 54, 15);
		contentPane.add(prickle);
		
		JLabel count_label = new JLabel("数量:");
		count_label.setBounds(35, 143, 54, 15);
		contentPane.add(count_label);
		
		count = new JTextField();
		count.setBounds(99, 140, 212, 21);
		contentPane.add(count);
		count.setColumns(10);
		
		JLabel note_label = new JLabel("简介:");
		note_label.setBounds(35, 188, 54, 15);
		contentPane.add(note_label);
		
		JTextArea note = new JTextArea();
		note.setBounds(99, 184, 269, 103);
		contentPane.add(note);
		
		JButton confirmButton = new JButton("确定修改");
		confirmButton.setBounds(275, 310, 93, 23);
		contentPane.add(confirmButton);
		
		JLabel label_5 = new JLabel(">=0");
		label_5.setBounds(321, 143, 43, 15);
		contentPane.add(label_5);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				((AdminUpdate)e.getSource()).setVisible(false);
			}
		});
	}
}
