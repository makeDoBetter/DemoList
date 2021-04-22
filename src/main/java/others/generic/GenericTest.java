package others.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author fengjirong 2020/06/05 14:58
 */
public class GenericTest {

	public static void main(String[] args) {
		Box<Integer> integerBox = new Box<Integer>();
		Box<String> stringBox = new Box<String>();
		integerBox.setT(10);
		stringBox.setT("这是泛型字符串");
		System.out.println(integerBox.getT());
		System.out.println(stringBox.getT());
		System.out.println("---------------泛型通配符--------------------");
		List<Integer> integer = new ArrayList<Integer>();
		List<Double> doubles = new ArrayList<Double>();
		List<String> strings = new ArrayList<String>();

		integer.add(10);
		doubles.add(3.14);
		strings.add("通配符字符串");

		print(integer);
		print(doubles);
		print(strings);
	}


	public static void print(List<?> data){
		System.out.println(data.get(0));
	}

}
