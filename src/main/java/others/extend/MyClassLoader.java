package others.extend;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * 自定义类加载器
 * 重新父类findClass()方法即可
 *
 * @author fengjirong 2020/06/22 14:58
 */
public class MyClassLoader extends ClassLoader {

	private String path = "d:\\";

	private final String fileType = ".class";

	//类加载器名字
	private String name = null;

	public MyClassLoader(String name){
		super();
		this.name = name;
	}

	public MyClassLoader(ClassLoader parent, String name){
		super(parent);
		this.name = name;
	}

	@Override
	public String toString(){
		return this.name;
	}

	public void setPath(String path){
		this.path = path;
	}

	@Override
	protected Class findClass(String name) throws ClassNotFoundException {
		byte[] data = loadClassData(name);
		return defineClass(name,data,0,data.length);
	}

	/**
	 * 获取文件数据
	 *
	 * @param name
	 * @author fengjirong 2020-06-22 16:05
	 * @return
	 */
	private byte[] loadClassData(String name) throws ClassNotFoundException{
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		byte[] data = null;
		try{
			// 读取文件内容
			name = name.replaceAll("\\.","\\\\");
			System.out.println("加载文件名："+name);
			// 将文件读取到数据流中
			fis = new FileInputStream(path+name+fileType);
			baos = new ByteArrayOutputStream();
			int ch = 0;
			while ((ch = fis.read()) != -1){
				baos.write(ch);
			}
			data = baos.toByteArray();
		}catch (Exception e){
			throw new ClassNotFoundException("Class is not found:"+name,e);
		}finally {
			// 关闭数据流
			try {
				fis.close();
				baos.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return data;
	}

	@Override
	protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
		return super.loadClass(name, resolve);
	}
}
