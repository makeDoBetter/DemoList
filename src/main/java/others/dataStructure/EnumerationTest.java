package others.dataStructure;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Enumeration接口中定义了一些方法，通过这些方法可以枚举（一次获得一个）对象集合中的元素。
 *
 * @author fengjirong 2020/06/04 14:27
 */
public class EnumerationTest {

	public static void main(String[] args) {
		Enumeration<String> days;
		//Vector类似ArrayList，不同在于其保证线程安全，效率较低
		Vector<String> vector = new Vector<String>();
		vector.add("Sunday");
		vector.add("Monday");
		vector.add("Tuesday");
		vector.add("Wednesday");
		vector.add("Thursday");
		vector.add("Friday");
		vector.add("Saturday");
		days = vector.elements();
		while (days.hasMoreElements()) {
			System.out.println(days.nextElement());
		}
	}

}
