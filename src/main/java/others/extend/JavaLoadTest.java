package others.extend;

/**
 * description
 *
 * @author fengjirong 2020/06/22 15:44
 */
public class JavaLoadTest {
	public static int v1= 1;
	public static int v2;
	private int v3=init();

	static {
		System.out.println("初始化静态变量时使用了静态代码块");
		System.out.println("v1的值："+v1);
		System.out.println("准备阶段v2的默认值："+v2);
		v2 = 2;
		System.out.println("初始化阶段v2的默认值："+v2);
	}

	int init(){
		System.out.println("给普通成员变量V3进行赋值了");
		return 3;
	}

	public JavaLoadTest(){
		System.out.println("调用到了JavaLoadTest构造方法");
		System.out.println("JavaLoadTest加载器为："+this.getClass().getClassLoader());
	}
}
