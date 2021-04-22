package others.dataStructure;

import java.util.HashMap;

/**
 * 1.HashMap与HashTable大致相同，但是HashMap允许null value或者key;
 * 2.当HashMap实例中元素数量达到当前容量*负载因子时，当前实例将会扩容，初始容量：16，
 * 默认负载因子：0.75，过高或者过低的负载因子都会影响性能；
 * 3. HashMap没有同步锁，非线程安全，如需要保证数据线程安全（HashMap对象结构被线程修改【包括新增或者删除一个或多个元素】），
 * 需要在外部进行线程同步，或者可使用ConcurrentHashMap；
 * 4. HashMap的结构为：数组+链表+红黑树（jdk1.8），其中数组的容量一定是2的整次幂，2的整次幂设计在于put方法计算hash值在数组中下标时保证结果的均匀，
 * i = (n - 1) & hash为计算hash对应的数组下标，因为数组长度n为2的整次幂，因此n-1低位一定全为1，hash与操作得到下标不会越界，分布均匀
 * <p>
 * 5. HashMap put方法 首先判断数组是否存在，不存在新建数组；如果数组存在，则判断数组下标下是否有值，没有直接插入，数组使用+1，有则判断是否是相同key，
 * 为相同key则覆盖，返回原值；否则查看是否为红黑树结构，如果是则添加节点；如果不是红黑树则遍历链表，如果相同key则覆盖，返回原值；如果遍历到链表末尾，
 * 将新节点挂在最后，判断是否链表长度达到8，是则将链表转变成红黑树；最后，比较数组使用长度与阈值，超过则扩容；
 * 6. HashMap 扩容机制，判断是否满足扩容条件，即原数组容量大于1<<<30，修改当前阈值为int最大值，后续不再扩容；否则当前容量=原数组容量*2，
 * 当前阈值=原阈值*2，初始化新数组，HashMap对象table指向新数组，原数组元素根据与原容量与操作，每一个下标的元素分成下标不变跟下标+原容量两个链表，
 * 之后进行新的绑定。重新分配这一部很巧妙，新的容量为原容量*2的作用在此，省去了重新计算，降低重新分配的复杂度；
 * 7. HashMap get方法 此方法逻辑较简单，根据hash，key参数确定表不为空，对应数组下标不为空，第一个节点key相等则返回，否则判断是否红黑树，
 * 是则从红黑树中获取对应的节点返回，否则遍历链表，返回相同key的节点；否则返回null；最终返回节点的value
 */
public class HashMapTest {
    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put(null, 1);
        System.out.println(map.get(null));
        //待实现，并发下验证线程不安全

        //当put存在相同key时，会覆盖原值，返回原值
        System.out.println(map.put(null, 2));
        System.out.println(map.get(null));
    }
}


