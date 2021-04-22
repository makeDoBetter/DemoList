package others.io;

import java.io.*;

/**
 * Java IO
 * 1. 流的分类
 * 		按数据类型：字节流、字符流
 * 		按数据流向：输入流、输出流
 * 		按操作角色：节点流、处理流
 *
 * 	2. 流的体系结构
 * 	基本流				节点流（文件流）			缓冲流（属于处理流）
 * 	InputStream			FileInputStream				BufferedInputStream
 * 	OutputStream		FileOutputStream			BufferedOutputStream
 * 	Reader				FileReader					BufferedReader
 * 	Writer				FileWriter					BufferedWriter
 *
 * 更多测试方法在测试目录下的IO文件夹下
 * @author fengjirong 2020/06/02 16:00
 */
public class IoTest {

	public static void main(String[] args) throws IOException {
		//对对象进行写操作
		OutputStream outputStream = new FileOutputStream("C:\\Users\\fengjirong\\Desktop\\新建文件夹\\1.txt");
		char[] arr = {'a', 'b', 'c'};
		for(int i = 0; i < arr.length; i++) {
			outputStream.write(arr[i]);
		}
		outputStream.close();
		//对对象进行读操作
		InputStream inputStream = new FileInputStream("C:\\Users\\fengjirong\\Desktop\\新建文件夹\\1.txt");
		int available = inputStream.available();
		for (int j = 0; j < available; j++){
			System.out.println((char) inputStream.read());
		}
		inputStream.close();

		File f = new File("C:\\Users\\fengjirong\\Desktop\\新建文件夹\\2.txt");
		FileOutputStream ou = new FileOutputStream(f);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ou, "utf-8");
		outputStreamWriter.append("这是一个文本");
		outputStreamWriter.append("\r\n");
		outputStreamWriter.append("this is English");
		outputStreamWriter.close();
		ou.close();

		FileInputStream in = new FileInputStream(f);
		InputStreamReader inputStreamReader = new InputStreamReader(in);
		StringBuilder s = new StringBuilder();
		while (inputStreamReader.ready()) {
			s.append((char) inputStreamReader.read());
		}
		System.out.println(s);
		inputStreamReader.close();
		in.close();
	}
}
