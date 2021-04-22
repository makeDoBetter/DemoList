package others.extend;

/**
 * description
 *
 * @author fengjirong 2020/06/04 10:30
 */
public class Son extends Father {

	public Son(String name, int age) {
		super(name, age);
	}

	@Override
	public void print() {
		System.out.println("子类对父类抽象方法的实现");
	}
}
