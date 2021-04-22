package others.java8New;

/**
 *
 * Lambda表达式的目的是为了简化匿名内部类的代码写法
 *
 * Lambda 规定接口中只能有一个需要被实现的方法，不是规定接口中只能有一个方法
 * jdk 8 中有另一个新特性：default， 被 default 修饰的方法会有默认实现，
 * 不是必须被实现的方法，所以不影响 Lambda 表达式的使用。
 * -> goes to
 *
 * Lambda表达式的省略写法
 * 1.如果Lambda表达式只有一行代码，可以省略大括号不写，同时省略分号；
 * 2.如果Lambda表达式只有一行return语句代码，可以省略大括号，分号，同时必须省略return；
 * 3.参数类型可以省略不写；
 * 4.如果只有一个参数，参数类型可以省略，()也可以省略
 *
 * Lambda表达式的方法引用
 * 示例 System.out::println
 * 作用，进一步简化代码写法
 * Lambda表达式的入参与方法形参参数类型，数量一致即可引用
 *
 * 引用类型
 * 1.成员方法的引用
 * 格式：对象::方法
 * 2.静态方法的引用
 * 格式：类名::方法
 * 3.特定类型方法引用
 * --特定类型，String等特定类型
 * --格式：特定类型::方法
 * --注意：如果第一个入参作为后面特定类型方法的调用者，而其他参数作为该方法的形参，那么就可以使用这种方法调用（s1.compare(s2)）
 * 4.构造方法的引用
 * 格式：类名::new
 * 示例：String::new
 *
 *
 * @author fengjirong 2020/06/11 21:56
 */
public class Java8NewTest {
	public static void main(String[] args) {
		MathDemo mathDemo = ((a, b) -> a+b);
		MathDemo mathDemo1 = ((a, b) -> a/b);
		MessageDemo messageDemo = message -> System.out.println("哈哈哈哈"+message);
		MessageDemo messageDemo1 = message -> System.out.println("呵呵呵呵"+message);

		System.out.println(mathDemo.info(1,2));
		System.out.println(mathDemo1.info(4,2));
		messageDemo.sayMessage("大帅哥");
		messageDemo.doMessage("大帅哥");
		messageDemo1.sayMessage("还是大帅哥");
		messageDemo1.doMessage("还是大帅哥");
	}

	/*@FunctionalInterface
	修饰函数式接口的，要求接口中的抽象方法只有一个。
	这个注解往往会和 lambda 表达式一起出现。*/
	@FunctionalInterface
	interface MathDemo{
		/**
		 * 计算接口
		 *
		 * @param a
		 * @param b
		 * @author fengjirong 2020-06-11 21:58
		 * @return
		 */
		int info(int a, int b);
	}

	@FunctionalInterface
	interface MessageDemo{
		/**
		 * 打印message
		 *
		 * @param message
		 * @author fengjirong 2020-06-11 22:02
		 * @return
		 */
		void sayMessage(String message);

		/**
		 * 验证默认输出
		 *
		 * @param message
		 * @author fengjirong 2020-06-11 22:10
		 * @return
		 */
		default void doMessage(String message){
			System.out.println("默认输出"+message);
		}
	}

}
