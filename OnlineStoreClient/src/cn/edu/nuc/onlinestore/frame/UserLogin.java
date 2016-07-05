package cn.edu.nuc.onlinestore.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class UserLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3087959668950494137L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public UserLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserLogin.class.getResource("/img/user_login_logo.png")));
		setTitle("中北线在商场-登录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 546, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("用户名:");
		label.setBounds(129, 258, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("密  码:");
		label_1.setBounds(129, 306, 54, 15);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.setBounds(214, 255, 197, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(214, 303, 197, 21);
		contentPane.add(passwordField);
		
		JButton button = new JButton("登录系统");
		button.setBounds(318, 346, 93, 23);
		contentPane.add(button);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(UserLogin.class.getResource("/img/user.png")));
		label_2.setBounds(134, 10, 255, 232);
		contentPane.add(label_2);
	}
}
