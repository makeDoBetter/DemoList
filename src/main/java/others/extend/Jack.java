package others.extend;

/**
 * description
 *
 * @author fengjirong 2020/06/03 16:35
 */
public class Jack extends Person{

	public Jack(String name, int age) {
		super(name, age);
	}

	@Override
	public void print(){
		System.out.println("就不做介绍");
	}
}
