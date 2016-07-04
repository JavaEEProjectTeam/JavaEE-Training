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

import cn.edu.nuc.onlinestore.model.Admin;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminStore extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6143120919875553327L;
	private JPanel contentPane;
	private JTextField goodsid;
	private JFrame perviousFrame;
	private Admin admin;
	
	public AdminStore(JFrame perviousFrame, Admin admin) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminStore.class.getResource("/img/admin_llogin_logo.png")));
		this.perviousFrame = perviousFrame;
		this.admin = admin;
		perviousFrame.setVisible(false);  //隐藏掉登录窗口
		this.setVisible(true);            //显示本窗口
		
		setTitle("中北商场后台管理系统--当前用户:" + admin.getAdminName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 732, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel content = new JPanel();
		content.setBounds(10, 78, 696, 341);
		content.setLayout(new GridLayout(1, 1, 0, 0));
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("商品编号");
		model.addColumn("名称");
		model.addColumn("单价(人民币)");
		model.addColumn("库存");
		
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
		
		content.add(pane);
		contentPane.add(content);
		
		JButton addGoodsButton = new JButton("添加商品");
		addGoodsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminAdd add = new AdminAdd();
				add.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				add.setVisible(true);
			}
		});
		addGoodsButton.setBounds(386, 45, 93, 23);
		contentPane.add(addGoodsButton);
		
		JButton modifyGoodsButton = new JButton("修改商品");
		modifyGoodsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUpdate u = new AdminUpdate();
				u.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				u.setVisible(true);
			}
		});
		modifyGoodsButton.setBounds(489, 45, 93, 23);
		contentPane.add(modifyGoodsButton);
		
		JButton deleteGoodsButton = new JButton("删除选中商品");
		deleteGoodsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//得到当前选中商品项
				JOptionPane.showConfirmDialog(null, "确定要删除\"水杯\"么?" );
			}
		});
		deleteGoodsButton.setBounds(587, 45, 119, 23);
		contentPane.add(deleteGoodsButton);
		
		JButton exitButton = new JButton("退出登录");
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		exitButton.setBounds(613, 6, 93, 23);
		contentPane.add(exitButton);
		
		JLabel onlineUserCount = new JLabel("当前在线用户数: 0");
		onlineUserCount.setBounds(10, 10, 162, 15);
		contentPane.add(onlineUserCount);
		
		JLabel goodsIdLabel = new JLabel("商品编号:");
		goodsIdLabel.setBounds(10, 53, 54, 15);
		contentPane.add(goodsIdLabel);
		
		goodsid = new JTextField();
		goodsid.setBounds(68, 50, 104, 21);
		contentPane.add(goodsid);
		goodsid.setColumns(10);
		
		JButton searchButton = new JButton("搜索");
		searchButton.setBounds(180, 49, 93, 23);
		contentPane.add(searchButton);
	}

}
