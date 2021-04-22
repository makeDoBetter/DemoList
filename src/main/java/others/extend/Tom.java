package others.extend;

/**
 * description
 *
 * @author fengjirong 2020/06/03 16:35
 */
public class Tom extends Person{

	//函数重写
	public Tom(String name, int age) {
		super(name, age);
	}

	public Tom(String name){
		super(name, 0);
	}

	public Tom(int age, String name){
		super(name, age);
	}
}
