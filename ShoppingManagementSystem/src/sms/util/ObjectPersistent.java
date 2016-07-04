package sms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import sms.entity.Admin;

/**
 * 对象持久化（主要负责将对象序列化后写入文件以及反序列化）
 * @author 王凯
 *
 */
public class ObjectPersistent<T> {
	
	/**
	 * 将对象持久化到指定目录下的的文件中
	 * @param t 被持久化的对象T
	 * @param path 文件所在完整路径名（含文件名）
	 * @throws Exception 若无法访问文件所在路径或待持久化对象为空时抛出异常
	 */
	public void persistObject(T t,String path) throws Exception{
		if (t == null) {
			throw new Exception("您想要持久化的对象为空，请检查目标对象！");
		}
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		ObjectOutputStream oos = null;
		OutputStream out = new FileOutputStream(file); // 文件输出流
		oos = new ObjectOutputStream(out);
		oos.writeObject(t);
		oos.close();
	}
	
	/**
	 * 给定一个文件所在的路径，从指定文件中读取对象
	 * @param path 文件所在路径
	 * @return 目标对象，若无对象可读取时返回null
	 * @throws Exception 文件路径不存在或无法访问时抛出异常
	 */
	public T getObject(String path) throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			throw new Exception("您指定的文件不存在！请检查文件路径：\n"
					+ path + "\n是否拼写正确！");
		}
		ObjectInputStream ois = null;
		InputStream is = new FileInputStream(file);
		ois = new ObjectInputStream(is);
		Object object = ois.readObject();
		ois.close();
		return (T)object;
	}
}
