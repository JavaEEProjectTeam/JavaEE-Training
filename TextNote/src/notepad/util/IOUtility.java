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
 * 读写工具类，用于向文本文件中写入和读出
 * @author 王凯
 *
 */
public class IOUtility {
	
	/**
	 * 读取文件
	 * @return 读取的字符串
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
			JOptionPane.showMessageDialog(null, "找不到指定文件！"
					, "出错", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "读取文件出错！"
					, "出错", JOptionPane.WARNING_MESSAGE);
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
	 * 写入文件
	 * @param file 要保存的文件
	 * @content 写入的内容
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
			JOptionPane.showMessageDialog(null, "保存文件失败！"
					, "出错", JOptionPane.WARNING_MESSAGE);
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
