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
 * ���±�Ӧ��
 * @author ����
 *
 */
public class Notepad extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	/**
	 * �������
	 * @throws Exception 
	 */
	public Notepad() throws Exception {
		//����ͼ�꣬��Classpath�м���
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Notepad.class.getResource("/img/icon.png")));
		
		//���ô��ڱ���
		setTitle("\u8BB0\u4E8B\u672C");
		
		//����Ĭ�ϵĹرղ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//���ô��ڴ�С
		setBounds(100, 100, 1230, 672);
		
		//���ô������
		setLocationRelativeTo(null);
		
		//�������
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		//�����˵�������ӵ����
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//�ļ��˵�
		JMenu file = new JMenu("    \u6587\u4EF6(F)    ");
		menuBar.add(file);
		
		//�½��˵���
		JMenuItem newFile = new JMenuItem("\u65B0\u5EFA(N)         ");
		
		//�����½��ļ���ݼ�Ctrl+N
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		
		file.add(newFile);
		
		//�򿪲˵���
		JMenuItem openFile = new JMenuItem("\u6253\u5F00(O)...         ");
		
		//���ļ����¼�����
		openFile.addActionListener(new OpenFileListener(contentPane));
		
		//���ô��ļ���ݼ�Ctrl+O
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Event.CTRL_MASK));
		
		file.add(openFile);
		
		//����˵���
		JMenuItem saveFile = new JMenuItem("\u4FDD\u5B58(S)        ");
		
		//��ӱ�����¼�����
		saveFile.addActionListener(new SaveFileListener(contentPane));
		
		//���ñ����ļ���ݼ�Ctrl+S
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK));
		
		file.add(saveFile);
		
		//���Ϊ�˵���
		JMenuItem saveTo = new JMenuItem("\u53E6\u5B58\u4E3A(A)");
		file.add(saveTo);
		
		//�˳��˵���
		JMenuItem exit = new JMenuItem("\u9000\u51FA(X)");
		exit.addActionListener(new ExitListener());
		file.add(exit);
		
		//���������˵�
		JMenu edit = new JMenu("   \u7F16\u8F91(E)   ");
		menuBar.add(edit);
		
		JMenu format = new JMenu("   \u683C\u5F0F(O)   ");
		menuBar.add(format);
		
		JMenu view = new JMenu("   \u67E5\u770B(V)   ");
		menuBar.add(view);
		
		JMenu help = new JMenu("   \u5E2E\u52A9(H)   ");
		
		menuBar.add(help);

		//�ı�������
		JTextArea textArea = new JTextArea();
		textArea.setTabSize(4);
		textArea.setFont(new Font("����", Font.PLAIN, 20));
		textArea.setWrapStyleWord(true);       // ������в����ֹ���
		
		//�Ѷ����JTextArea�ŵ�JScrollPane����ȥ 
		JScrollPane jScrollPane = new JScrollPane(textArea);
		
		//�ֱ�����ˮƽ�������Զ�����,��ֱ���������ǳ��� 
		jScrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		contentPane.add(jScrollPane);
		
		//���ý�����Ϊ����ϵͳĬ�Ϸ��
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtilities.updateComponentTreeUI(this);  
	}
	
	/**
	 * ������л�ȡ�ı����ʵ��
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
