package notepad.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import notepad.Notepad;
import notepad.util.IOUtility;

/**
 * 打开文件监听器
 * @author 王凯
 *
 */
public class OpenFileListener implements ActionListener {
	
	private JPanel contentPane;
	
	public OpenFileListener(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser newfileChooser = new JFileChooser();
		newfileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		newfileChooser.showDialog(new JLabel(), "打开文件");  
        File file = newfileChooser.getSelectedFile();  
		JTextArea textArea = Notepad.getTextArea(contentPane); //从面板中获取文本框的实例
		String content = "";
		content = IOUtility.readFile(file);
		if (content != null && content.length() != 0) {
			textArea.setText(content);
		}
		
	}

}
