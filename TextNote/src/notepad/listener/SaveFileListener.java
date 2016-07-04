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

public class SaveFileListener implements ActionListener {

	private JPanel contentPane;
	
	public SaveFileListener(JPanel contentPane) {
		this.contentPane = contentPane;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser newfileChooser = new JFileChooser();
		newfileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		newfileChooser.showDialog(new JLabel(), "�����ļ�");  
        File file = newfileChooser.getSelectedFile();  
		JTextArea textArea = Notepad.getTextArea(contentPane); //������л�ȡ�ı����ʵ��
		String content = textArea.getText();
		IOUtility.writeFile(file, content);
	}

}
