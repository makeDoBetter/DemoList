package others.extend;

import others.interfase.Animal;
import others.interfase.impl.Dog;
import others.utils.GetUnsafeInstance;
import sun.misc.Unsafe;

/**
 * description
 *
 * @author fengjirong 2020/06/03 16:36
 */
public class TestExtends {

	public static void main(String[] args) throws InstantiationException {
		Tom tom = new Tom("tom", 10);
		tom.print();
		Jack jack = new Jack("jack", 12);
		jack.print();
		Person jackson = new Jack("jackson", 12);
		/*编译找父类方法，执行找子类方法*/
		jackson.print();

		System.out.println("--------------------------测试抽象类--------------------------------");
		Father father = new Father("tom", 10) {
			@Override
			public void print() {
				System.out.println("抽象方法的实现");
			}
		};
		father.print();
		Father son = new Son("jack", 12);
		son.eat();
		son.print();
		son.work();
		System.out.println("--------------------------unsafe实例--------------------------------");
		Unsafe unsafeInstance = GetUnsafeInstance.getUnsafeInstance();
		Son allocateInstance = (Son) unsafeInstance.allocateInstance(Son.class);
		allocateInstance.eat();
		allocateInstance.work();
		allocateInstance.print();
		//即使使用unsafe实例还是无法创建抽象类对象，编译不会报异常，但是运行时异常
		/*Father allocateInstance1 = (Father)unsafeInstance.allocateInstance(Father.class);
		allocateInstance1.eat();
		allocateInstance1.work();
		allocateInstance1.print();*/
		System.out.println("--------------------------接口测试--------------------------------");
		Dog dog = new Dog();
		dog.eat();
		dog.move();
		dog.print();
		Animal.staticPrint();
	}

}
