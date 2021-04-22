package others.interfase.impl;

import others.interfase.Animal;

/**
 * description
 *
 * @author fengjirong 2020/06/04 11:49
 */
public class Dog implements Animal {

	@Override
	public void eat() {
		System.out.println("狗吃骨头");
	}

	@Override
	public void move() {
		System.out.println("狗四条腿跑");
	}

	public void print(){
		System.out.println(Animal.name);
	}
}
