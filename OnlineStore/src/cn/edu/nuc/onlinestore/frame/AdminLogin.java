package cn.edu.nuc.onlinestore.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cn.edu.nuc.onlinestore.service.LoginRegisterService;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminLogin extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 201596698169710930L;
	
	/**
	 * 主面板
	 */
	private JPanel contentPane;
	
	/**
	 * 管理员名输入框
	 */
	private JTextField adminName;
	
	/**
	 * 密码输入区
	 */
	private JPasswordField password;
	
	/**
	 * 当前窗体
	 */
	private JFrame thisFrame;

	/**
	 * Create the frame.
	 */
	public AdminLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminLogin.class.getResource("/img/admin_llogin_logo.png")));
		setTitle("中北线在商场管理系统-管理员登录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 534, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		thisFrame = this;
		
		//设置窗体居中
		setLocationRelativeTo(null);
		
		//设置界面风格为操作系统默认风格
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);  
		
		JLabel adminNameLabel = new JLabel("用户名:");
		adminNameLabel.setBounds(151, 245, 54, 15);
		contentPane.add(adminNameLabel);
		
		JLabel passwordLabel = new JLabel("密  码:");
		passwordLabel.setBounds(151, 276, 54, 15);
		contentPane.add(passwordLabel);
		
		adminName = new JTextField();
		adminName.setBounds(215, 242, 197, 21);
		contentPane.add(adminName);
		adminName.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(215, 273, 197, 21);
		contentPane.add(password);
		
		JButton loginButton = new JButton("登录系统");
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String adminname = adminName.getText();
				String adpassword = new String(password.getPassword());
				if (adminname.equals("") || adpassword.equals("")) {
					JOptionPane.showMessageDialog(null, "用户名、密码不能为空",
							"警告", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (LoginRegisterService.adminLoginValidate(adminname,adpassword)) {
					JFrame jf = new AdminStore(adminname);
					thisFrame.setVisible(false);
					jf.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "登录失败！用户名或密码错误！",
							"错误", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		loginButton.setBounds(252, 319, 93, 23);
		contentPane.add(loginButton);
		
		JLabel picture = new JLabel("");
		picture.setIcon(new ImageIcon(AdminLogin.class.getResource("/img/admin.png")));
		picture.setBounds(140, 10, 255, 235);
		contentPane.add(picture);
	}
}
