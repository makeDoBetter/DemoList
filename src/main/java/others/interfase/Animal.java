package others.interfase;

/**
 * animal接口
 *
 * 接口不能用于实例化对象。
 * 接口没有构造方法。
 * 接口中所有的方法必须是抽象方法。
 * 接口不能包含成员变量，除了 static 和 final 变量。
 * 接口不是被类继承了，而是要被类实现。
 * 接口支持多继承。
 *
 * 特性：
 * 接口中每一个方法也是隐式抽象的,接口中的方法会被隐式的指定为 public abstract
 * （只能是 public abstract，其他修饰符都会报错）。
 * 接口中可以含有变量，但是接口中的变量会被隐式的指定为 public static final 变量
 * （并且只能是 public，用 private 修饰会报编译错误）。
 * 接口中的方法是不能在接口中实现的，只能由实现接口的类来实现接口中的方法
 *
 * @author fengjirong 2020/06/04 11:43
 */
public interface Animal {

	String name = "这是一个动物接口";

	/**
	 * 吃的方法
	 *
	 * @author fengjirong 2020-06-04 11:45
	 * @return
	 */
	void eat();

	/**
	 * 移动的方法
	 *
	 * @author fengjirong 2020-06-04 11:46
	 * @return
	 */
	void move();

	/**
	 * jdk1.8后接口允许静态方法
	 *
	 * @author fengjirong 2020-06-04 14:15
	 * @return
	 */
	static void staticPrint(){
		System.out.println("接口静态方法输出");
	}

}
