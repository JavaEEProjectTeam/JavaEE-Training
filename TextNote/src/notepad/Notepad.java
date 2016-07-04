package notepad;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Component;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import notepad.listener.ExitListener;
import notepad.listener.OpenFileListener;
import notepad.listener.SaveFileListener;

import java.awt.event.InputEvent;

/**
 * 记事本应用
 * @author 王凯
 *
 */
public class Notepad extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	/**
	 * 创建面板
	 * @throws Exception 
	 */
	public Notepad() throws Exception {
		//设置图标，从Classpath中加载
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Notepad.class.getResource("/img/icon.png")));
		
		//设置窗口标题
		setTitle("\u8BB0\u4E8B\u672C");
		
		//设置默认的关闭操作
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//设置窗口大小
		setBounds(100, 100, 1230, 672);
		
		//设置窗体居中
		setLocationRelativeTo(null);
		
		//创建面板
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		//创建菜单栏并添加到面板
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//文件菜单
		JMenu file = new JMenu("    \u6587\u4EF6(F)    ");
		menuBar.add(file);
		
		//新建菜单项
		JMenuItem newFile = new JMenuItem("\u65B0\u5EFA(N)         ");
		
		//设置新建文件快捷键Ctrl+N
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		
		file.add(newFile);
		
		//打开菜单项
		JMenuItem openFile = new JMenuItem("\u6253\u5F00(O)...         ");
		
		//打开文件的事件处理
		openFile.addActionListener(new OpenFileListener(contentPane));
		
		//设置打开文件快捷键Ctrl+O
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Event.CTRL_MASK));
		
		file.add(openFile);
		
		//保存菜单项
		JMenuItem saveFile = new JMenuItem("\u4FDD\u5B58(S)        ");
		
		//添加保存的事件处理
		saveFile.addActionListener(new SaveFileListener(contentPane));
		
		//设置保存文件快捷键Ctrl+S
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK));
		
		file.add(saveFile);
		
		//另存为菜单项
		JMenuItem saveTo = new JMenuItem("\u53E6\u5B58\u4E3A(A)");
		file.add(saveTo);
		
		//退出菜单项
		JMenuItem exit = new JMenuItem("\u9000\u51FA(X)");
		exit.addActionListener(new ExitListener());
		file.add(exit);
		
		//其他几个菜单
		JMenu edit = new JMenu("   \u7F16\u8F91(E)   ");
		menuBar.add(edit);
		
		JMenu format = new JMenu("   \u683C\u5F0F(O)   ");
		menuBar.add(format);
		
		JMenu view = new JMenu("   \u67E5\u770B(V)   ");
		menuBar.add(view);
		
		JMenu help = new JMenu("   \u5E2E\u52A9(H)   ");
		
		menuBar.add(help);

		//文本输入区
		JTextArea textArea = new JTextArea();
		textArea.setTabSize(4);
		textArea.setFont(new Font("宋体", Font.PLAIN, 20));
		textArea.setWrapStyleWord(true);       // 激活断行不断字功能
		
		//把定义的JTextArea放到JScrollPane里面去 
		JScrollPane jScrollPane = new JScrollPane(textArea);
		
		//分别设置水平滚动条自动出现,垂直滚动条总是出现 
		jScrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		contentPane.add(jScrollPane);
		
		//设置界面风格为操作系统默认风格
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtilities.updateComponentTreeUI(this);  
	}
	
	/**
	 * 从面板中获取文本框的实例
	 */
	public static JTextArea getTextArea(JPanel panel) {
		JTextArea textArea = null;
		System.out.println();
		for(int i = 0;i < panel.getComponentCount(); i++) {
			 Component comp = panel.getComponent(i);
			 if (comp instanceof JScrollPane) {
				 JScrollPane jScrollPane = (JScrollPane)comp;
				 textArea = (JTextArea)jScrollPane.getViewport().getView();
			}
		}
		return textArea;
	}

}
