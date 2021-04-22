package others.dataStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ArrayList自动扩容底层采用数组复制方法
 * 	若所需的最小容量大于默认长度 10
 * 	则扩容，首先判断当前数组1.5倍是否大于扩容量，
 * 		小于则使用所需的最小容量做实际扩容量
 * 		否则，判断1.5倍原容量是否大于数组最大容量
 * 			是的话
 * 				如果小于整数限定值，则容量为整数限定值，
 * 				否则为数组最大容量
 * 			否则为1.5倍原容量
 * elementData = Arrays.copyOf(elementData, newCapacity);
 * Arrays.copyOf()方法返回的数组是新的数组对象，
 * 原数组对象仍是原数组对象，不变，该拷贝不会影响原来的数组。
 *
 * ArrayList底层由数组实现
 * LinkedList 底层由双向链表实现
 * @author fengjirong 2020/06/16 13:10
 */
public class ListTest {

	public static void main(String[] args) {
		System.out.println("-----------------------ArrayList-------------------------");
		ArrayList<Integer> arrayList = new ArrayList<>(16);
		arrayList.add(1);
		arrayList.add(2);
		arrayList.add(3);
		System.out.println(arrayList.size());
		List<Integer> collect = arrayList.stream().map(integer ->
				integer * integer).collect(Collectors.toList());
		collect.stream().forEach(System.out::println);
		System.out.println("-----------------------LinkedList-------------------------");
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(1);
		linkedList.add(2);
		linkedList.add(3);
		linkedList.addFirst(4);
		linkedList.addLast(5);
		linkedList.stream().map(i -> i * i).forEach(System.out::println);
		System.out.println("-----------------------LinkedList2-------------------------");
		LinkedList<Integer> linkedList2 = new LinkedList<>();
		linkedList2.addAll(collect);
		collect.stream().forEach(System.out::println);
		linkedList2.stream().forEach(System.out::println);

		//list去重处理，使用Set非重复特性与LinkedHashSet不会改变顺序
		List<String> list = new ArrayList<>();
		list.add("zhangsan");
		list.add("lisi");
		list.add("wangwu");
		list.add("zhangsan");
		List list1 = new ArrayList<>(new LinkedHashSet<>(list));
		System.out.println(list.toString());
		System.out.println(list1.toString());


	}

}
