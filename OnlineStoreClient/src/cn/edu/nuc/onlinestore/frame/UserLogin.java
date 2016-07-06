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

import cn.edu.nuc.onlinestore.network.TCPClient;
import cn.edu.nuc.onlinestore.service.LoginRegisterService;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3087959668950494137L;
	
	/**
	 * 主面板
	 */
	private JPanel contentPane;
	
	/**
	 * 用户名输入框
	 */
	private JTextField username;
	
	/**
	 * 密码框
	 */
	private JPasswordField password;
	
	/**
	 * 当前窗口
	 */
	private UserLogin thisFrame;
	
	/**
	 * 客户端线程
	 */
	private TCPClient client;

	/**
	 * Create the frame.
	 */
	public UserLogin() {
		
		//设置图标
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				UserLogin.class.getResource("/img/user_login_logo.png")));
		
		//设置标题
		setTitle("中北线在商场-登录");
		
		//设置默认的关闭行为
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//设置窗口大小
		setBounds(100, 100, 546, 445);
		
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
		
		//用户名标签
		JLabel username_label = new JLabel("用户名:");
		username_label.setBounds(129, 258, 54, 15);
		contentPane.add(username_label);
		
		//密码标签
		JLabel password_label = new JLabel("密  码:");
		password_label.setBounds(129, 306, 54, 15);
		contentPane.add(password_label);
		
		//用户名输入框
		username = new JTextField();
		username.setBounds(214, 255, 197, 21);
		contentPane.add(username);
		username.setColumns(10);
		
		//密码输入框
		password = new JPasswordField();
		password.setBounds(214, 303, 197, 21);
		contentPane.add(password);
		
		//登录按钮
		JButton loginButton = new JButton("登录");
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String uname = username.getText();
				String upass = new String(password.getPassword());
				if (uname == null || upass == null 
						|| uname.equals("") || upass.equals("")) {
					JOptionPane.showMessageDialog(null, "用户名或密码不能为空！", 
							"提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				LoginRegisterService lrs = new LoginRegisterService(client.getClient());
				try {
					lrs.userLogin(uname, upass);  //给服务器端发登录请求
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "额，网络不通！",
							"提示", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		loginButton.setBounds(318, 346, 93, 23);
		contentPane.add(loginButton);
		
		
		//主面板中央的大图片
		JLabel picture = new JLabel("");
		picture.setIcon(new ImageIcon(UserLogin.class.getResource("/img/user.png")));
		picture.setBounds(134, 10, 255, 232);
		contentPane.add(picture);
		
		//注册按钮
		JButton registerButton = new JButton("注册");
		registerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String uname = username.getText();
				String upass = new String(password.getPassword());
				if (uname == null || upass == null 
						|| uname.equals("") || upass.equals("")) {
					JOptionPane.showMessageDialog(null, "用户名或密码不能为空！", 
							"提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				LoginRegisterService lrs = new LoginRegisterService(client.getClient());
				try {
					lrs.userRegister(uname, upass); //给服务器端发注册请求
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "额，网络不通！",
							"提示", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		registerButton.setBounds(158, 346, 93, 23);
		contentPane.add(registerButton);
		
		//启动客户端线程
		thisFrame = this;
		client = new TCPClient(thisFrame);
		client.start();
		
		//在窗口关闭时通知服务器自己已经下线
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int select = JOptionPane.showConfirmDialog(null, "您要注销吗？", 
						"提示", JOptionPane.YES_NO_OPTION);
				if (select == 0) {  //用户选择了是
					thisFrame.setVisible(false);
					try {
						UserLogin.this.client.stopClient();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * 弹出对话框提示用户
	 * @param message 消息
	 */
	public void callUser(String message) {
		JOptionPane.showMessageDialog(null, message, "提示", JOptionPane.WARNING_MESSAGE);
	}
}
