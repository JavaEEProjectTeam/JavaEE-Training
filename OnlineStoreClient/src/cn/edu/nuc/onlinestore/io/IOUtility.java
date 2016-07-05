package cn.edu.nuc.onlinestore.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * IO工具类(客户端已简化)
 * @author 王凯
 *
 */
public class IOUtility {
	/**
	 * 对象持久化
	 * @param object 要持久化的对象
	 * @param out 输出流
	 * @throws Exception 出现错误后抛出
	 */
	public static void persistObject(Object object, OutputStream out){
		if (object == null) {
			return ;
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(out);
			oos.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从输入流中反序列化对象
	 * @param in
	 * @return 对象
	 */
	public static Object getObject(InputStream in) {
		if (in == null) {
			return null;
		}
		ObjectInputStream ois = null;
		Object object = null;
		try {
			ois = new ObjectInputStream(in);
			object = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return object;
	}
}
