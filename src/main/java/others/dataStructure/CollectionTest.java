package others.dataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * HashMap扩容在jdk1.7时使用的是头插法，但是在jdk1.8时采用的是尾插法
 * jdk1.8扩容核心：
 * 先插入后扩容，采用尾插法
 * 如果原hash表不为空，循环将每个索引拆分为两个新的链表
 * 一个是跟原索引相同的数据链表，另一个是原索引<<1的数据链表
 * 最后将扩容后的新索引分别绑定这两个链表的头节点
 * jdk1.7扩容核心:
 * 先扩容后插入，采用头插法
 * jdk1.8put方法
 * 索引对应链表为空则新增节点，如果长度超过8则转换成红黑树，如果有相同的键则覆盖并返回原值。
 *
 * @author fengjirong 2020/06/05 10:01
 */
public class CollectionTest {

	public static void main(String[] args) {
		System.out.println("----------------------hashMap------------------------");
		HashMap hashMap = new HashMap();
		hashMap.put(null, "key为空1");
		hashMap.put(null, "key为空2");
		//如果存在相同的key则会覆盖，并且返回原值
		System.out.println(hashMap.put("1","这是1"));
		System.out.println(hashMap.put("1","这是新的1"));
		System.out.println(hashMap.get(null));
		System.out.println("--------------------hashTable------------------------");
		Hashtable hashtable = new Hashtable();
		hashtable.put(null, "key为空1");
		hashtable.put(null, "key为空2");
		System.out.println(hashtable.get(null));
		List<String> t = new ArrayList<String>();
		t.add("a");
	}

}
