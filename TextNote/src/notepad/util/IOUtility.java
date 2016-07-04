package notepad.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.swing.JOptionPane;

/**
 * ��д�����࣬�������ı��ļ���д��Ͷ���
 * @author ����
 *
 */
public class IOUtility {
	
	/**
	 * ��ȡ�ļ�
	 * @return ��ȡ���ַ���
	 */
	public static String readFile(File file){
		if (file == null || !file.exists()) {
			return "";
		}
		StringBuilder content = new StringBuilder();
		int len = 0;
		char buf[] = new char[1024 * 2];
		Reader in = null;
		try {
			in = new FileReader(file);
			while((len = in.read(buf)) != -1){
				content.append(buf, 0, len);
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "�Ҳ���ָ���ļ���"
					, "����", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "��ȡ�ļ�����"
					, "����", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content.toString();
	}
	
	/**
	 * д���ļ�
	 * @param file Ҫ������ļ�
	 * @content д�������
	 */
	public static void writeFile(File file, String content) {
		if (file == null) {
			return ;
		}
		Writer out = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileWriter(file);
			out.write(content);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "�����ļ�ʧ�ܣ�"
					, "����", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
