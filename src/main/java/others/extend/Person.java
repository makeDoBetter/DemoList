package others.extend;

/**
 * description
 *
 * @author fengjirong 2020/06/03 16:29
 */
public class Person {

	private static int a=1;

	private String name;
	private int age;

	public Person(String name, int age){
		this.name = name;
		this.age = age;
	}

	public void print(){
		System.out.println("我的名字是："+this.name+"我"+this.age+"岁了");
	}
}
