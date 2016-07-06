package cn.edu.nuc.onlinestore.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class UserCartFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2347670394073573576L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public UserCartFrame() {
		setTitle("购物车详情");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 591, 377);
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

		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("商品编号");
		model.addColumn("名称");
		model.addColumn("单价(￥)");
		model.addColumn("数量");
		model.addColumn("总价格(￥)");
		//model.addColumn("操作");
		
		model.addRow(new String[]{"1","水杯","15.00","2","30.00"});
		model.addRow(new String[]{"5","农夫山泉","2.00","5","10"});
		model.addRow(new String[]{"6","毛巾","9.90","1","9.90"});
		table = new JTable( model );
		table.setBounds(10, 38, 543, 184);
		
		//JScrollPane pane = new JScrollPane( table );
		
		contentPane.add( table );
		
		JLabel label = new JLabel("总商品数量: 8");
		label.setBounds(195, 232, 116, 15);
		contentPane.add(label);
		
		JLabel lblNewLabel = new JLabel("总金额: 49.90");
		lblNewLabel.setBounds(342, 232, 211, 15);
		contentPane.add(lblNewLabel);
		
		JButton button = new JButton("结账");
		button.setBounds(456, 283, 93, 23);
		contentPane.add(button);
		
		
	}

}
