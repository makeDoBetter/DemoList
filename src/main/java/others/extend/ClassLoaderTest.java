package others.extend;

import java.lang.reflect.Field;

/**
 * 准备、初始化的主动使用
 * 	1.创建类的实例
 * 	2.访问某个类或接口的静态变量，或者对该静态 变量赋值
 * 	3.调用类的静态方法
 * 	4.反射（如 Class.forName(“com.shengsiyuan.Test”) ）
 * 	5.初始化一个类的子类
 * 	6.Java虚拟机启动时被标明为启动类的类（Java Test）
 * 除了上述六种情形，其他使用Java类的方式都被看作是被动使用，不会导致类的初始化
 *
 * 当Java虚拟机初始化一个类时，要求他的所有父类都已经被初始化，但是这条规则并不适用于接口
 * 在初始化一个类时，并不会初始化它所实现的接口。
 * 在初始化一个接口时，并不会先初始化它的父接口。
 *
 * 定义类加载器：若一个类加载器能成功加载Sample类，那么这个类加载器被称为定义类加载器。
 * 初始类加载器：所有能返回Class对象的引用的类加载器（包括定义类加载器）都被称为初始类加载器。
 *
 * 父类委托机制的优点是能够提高软件系统的安全性。因为在此机制下，用户自定义的类加载器不可能加载
 * 应该由父类加载器加载的可靠类，从而防止不可靠或者恶意的代码代替由父类加载器加载的可靠代码。
 *
 * 由同一类加载器加载的属于同一个包订单类组成了运行时包。
 * 两个类属于同一运行时包=包名相同+定义类加载器相同
 * 这样可以避免用户自定义的类访问核心类库包可见成员
 *
 * @author fengjirong 2020/06/19 14:38
 */
public class ClassLoaderTest {
	public static void main(String[] args) throws Exception {
		//子类初始化
		//son sons=new son();
		//调用类的静态变量
		System.out.println("输出="+parent.a);
		System.out.println("===================自定义类加载器验证=======================");
		MyClassLoader loader1 = new MyClassLoader("loader1");
		// 获取MyClassLoader加载器
		System.out.println("MyClassLoader 加载器：" + MyClassLoader.class.getClassLoader());
		// 设置加载类查找文件路径
		loader1.setPath("D:\\Mine\\source\\java_maven\\target\\classes\\others\\extend\\");
		loader(loader1);
	}

	private static void loader(MyClassLoader loader) throws Exception {
		// MyClassLoader 由系统加载器加载，跟test是不同的加载器，会出现NOClassDefFoundError
		// 如果类中有package，则加载类名时，需要写全，不然找不到该类,会出现NOClassDefFoundError
		Class JavaLoadTest = loader.loadClass("others.extend.JavaLoadTest", true);
		Object obj = JavaLoadTest.newInstance();
		// 如果MyClassLoader与test非同一个加载器，访问时，需要用到反射机制
		// java反射机制,取JavaLoadTest中的静态变量
		// 静态变量的初始化只有第一次类加载的时候会进行
		Field v1 = JavaLoadTest.getField("v1");
		System.out.println("获取的静态变量v1："+v1.getInt(obj));
		System.out.println("JavaLoadTest："+JavaLoadTest.hashCode());
		System.out.println("obj hashcode:"+obj.hashCode());
		// 卸载，将引用置空
		JavaLoadTest = null;
		obj = null;
		// 重新加载
		JavaLoadTest = loader.loadClass("others.extend.JavaLoadTest", true);
		obj = JavaLoadTest.newInstance();
		Field v2 = JavaLoadTest.getField("v2");
		System.out.println("获取的静态变量v2："+v2.getInt(obj));
		System.out.println("JavaLoadTest："+JavaLoadTest.hashCode());
		System.out.println("obj hashcode:"+obj.hashCode());
	}
}

class parent{
	public static int a=1;
	public static int b;
	private int c=initc();
	static {
		b=1;
		System.out.println("1.父类静态代码块：赋值b成功");
		System.out.println("1.父类静态代码块：a的值"+a);
	}
	int initc(){
		System.out.println("3.父类成员变量赋值：---> c的值"+c);
		this.c=12;
		System.out.println("3.父类成员变量赋值：---> c的值"+c);
		return c;
	}
	public parent(){
		System.out.println("4.父类构造方式开始执行---> a:"+a+",b:"+b);
		System.out.println("4.父类构造方式开始执行---> c:"+c);
	}
}

class son extends parent{
	private static int sa=1;
	private static int sb;
	private int sc=initc2();
	static {
		sb=1;
		System.out.println("2.子类静态代码块：赋值sb成功");
		System.out.println("2.子类静态代码块：sa的值"+sa);
	}
	int initc2(){
		System.out.println("5.子类成员变量赋值--->：sc的值"+sc);
		this.sc=12;
		return sc;
	}
	public son(){
		System.out.println("6.子类构造方式开始执行---> sa:"+sa+",sb:"+sb);
		System.out.println("6.子类构造方式开始执行---> sc:"+sc);
	}
}