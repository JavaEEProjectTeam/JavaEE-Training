package cn.edu.nuc.onlinestore.io;

import java.io.EOFException;
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
		try {
			persistObjectNoClose(object, out);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 对象持久化不关闭流
	 * @param object 要持久化的对象
	 * @param out 输出流
	 * @throws Exception 出现错误后抛出
	 */
	public static void persistObjectNoClose(Object object, OutputStream out){
		if (object == null) {
			return ;
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(out);
			oos.writeObject(object);
			//oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
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
		Object object = null;
		try {
			object = getObjectNoClose(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return object;
	}
	
	/**
	 * 从输入流中反序列化对象不关闭流
	 * @param in
	 * @return 对象
	 * @throws Exception 
	 */
	public static Object getObjectNoClose(InputStream in) {
		if (in == null) {
			return null;
		}
		Object object = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(in);
			object = ois.readObject();
		} catch(EOFException e) {
			//读到尾了，不管他
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}
