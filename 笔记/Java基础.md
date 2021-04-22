# Java基础

## 基础

### 基本概念

##### 封装

明确标识允许外部调用的方法及成员变量。内部方法对调用者透明，调用者无需关心内部实现。如bean向外暴露get、set方法，而成员变量本身是private修饰的；orm框架，用户只需要使用mybatis而不需要关注其是如何连接、操作sql。

##### 继承

继承基类的通用方法，子类做出继承或者拓展。

子类共性的方法不需重写，可复用父类的，只需要写拓展或者重写的方法

##### 多态

基于同一对象所属类不同，外部调用同一个方法执行逻辑不同。

多态三要素：继承、方法重写、父类引用指向子类对象。如下

```java
//编译看左边，运行看右边
Father son = New Son();
//子类对象调用重写方法
son.doFather();
```

##### 重载、重写

- 重载：同一个类中相同方法名，参数列表（数量、类型、顺序）不同。
- 重写：继承中，子类拓展父类相同方法，参数与方法名相同，返回类型可不同，但必须是父类的派生类，访问权限不可比父类低（父类private则子类不可重写），异常抛出范围小于等于父类。

##### JDK、JRE、JVM

jdk: Java Development Kit,java开发工具，其包含jre与jvm等。

jre: Java Runtime Exception，java运行环境。

jvm: Java虚拟机，是运行java字节码的虚拟机，根据运行环境不同有不同适配，实现Java语言相同代码不同环境运行关键。

##### ==与equals()

- ==：基本数据类型比较栈中的值，引用类型比较指向堆中的地址
- equals()：Object中内部通过==实现，而继承类一般会重写equals()

- - 

以String为例，比较的是每一个字符

```java
public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof String) {
            String anotherString = (String)anObject;
            int n = value.length;
            if (n == anotherString.value.length) {
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }
```

因此，如下会产生不同结果

```java
/**
     * description: 测试==与equals()差别
     *
     * @Author fengjirong
     * @Date   2021/3/15 22:55
     */
    @Test
    void doTestEquals(){
        //指向字符串常量池地址
        String s1 = "abc";
        //指向堆内存中开辟空间
        String s2 = new String("abc");
        //地址复制
        String s3 = s2;
        //false
        System.out.println(s1==s2);
        //false
        System.out.println(s1==s3);
        //true
        System.out.println(s2==s3);
        //true
        System.out.println(s1.equals(s2));
        System.out.println(s1.equals(s3));
        System.out.println(s2.equals(s3));
    }
```

##### final关键字

- 修饰类：表示类为最终类，不可被继承

- 修饰方法：表示方法不可被重写

- 修饰变量：表示变量不可更改值

  - 修饰成员变量：

    - 类变量：只可以在静态代码块或声明时指定值
    - 成员变量：非静态代码块、声明时或者构造方法中指定值

  - 修饰局部变量：

    系统不会为局部变量初始化，需要手动初始化，可以在声明局部变量的时候赋初值，也可申明时不赋值，使用前赋值（只可赋值一次）。

> 为何匿名内部类与局部内部类只可访问final局部变量

由于匿名内部类与局部内部类在编译的时候是与主类成同一层的，如Test()类中有一个匿名内部类，编译时将会编译为Test.class与Test01.class。

当类中的方法执行完毕时，方法中的局部变量将会销毁。而此时内部类可能还未执行完成，如果内部类调用了成员方法，由于局部变量已经销毁，因此内部类就相当调用了一个不存在的参数。为了解决这个问题，Java为内部类调用的参数复制了一个副本，而为了使副本与局部变量保持一致性，因此需要使用final修饰局部变量.

### String、StringBuilder、StringBuffer

由于 StringBuilder 相较于 StringBuffer 有速度优势，所以多数情况下建议使用 StringBuilder 类。然而在应用程序要求线程安全的情况下，则必须使用 StringBuffer 类。

#### String

String 字符串常量(final修饰，不可被继承)，String是常量，当创建之后即不能更改。

可以通过StringBuffer和StringBuilder创建String对象(常用的两个字符串操作类)。

#### StringBuffer

线程安全。

其也是final类别的，不允许被继承，其中的绝大多数方法都进行了同步处理，包括常用的Append方法也做了同步处理(synchronized修饰)。其自jdk1.0起就已经出现。其toString方法会进行对象缓存，以减少元素复制开销。

#### StringBuilder 

非线程安全。	

与StringBuffer一样都继承和实现了同样的接口和类，方法除了没使用synch修饰以外基本一致，不同之处在于最后toString的时候，会直接返回一个新对象。

### hashMap

[1]: https://zhuanlan.zhihu.com/p/21673805	"参考文档"

#### put源码

`Node<K,V> p`表示的是Node<K,V>[]数组中的根节点，

`Node<K,V> e`表示的是子节点，

而`(p = tab[i = (n - 1) & hash])`根据算法找到hash索引

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        //未初始化数组，则初始化，并获取长度
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        //根据键值key计算hash值得到插入的数组索引i，如果没有就在该处直接添加节点
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        //已有值
        else {
            Node<K,V> e; K k;
            //检查第一个Node，判断当前添加的节点是否存在，如果存在就直接覆盖
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            //判断节点是否为红黑树
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                //开始遍历链表
                //遍历table[i]，判断链表长度是否大于8，大于8的话把链表转换为红黑树，
                //在红黑树中执行插入操作，否则进行链表的插入操作；
                //遍历过程中若发现key已经存在直接覆盖value即可；
                for (int binCount = 0; ; ++binCount) {
                    //找到链表的最后一个为null的节点把添加的值挂在后面
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        //如果冲突的节点数已经达到8个，看是否需要改变冲突节点的存储结构，
			            //treeifyBin首先判断当前hashMap的长度，如果不足64，只进行
			            //resize，扩容table，如果达到64，那么将冲突的存储结构为红黑树
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    //如果遍历到相同的节点就结束遍历，并覆盖
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //链表上有相同的key，返回存在的value值
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        //扩容
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }
```

如图

![](pictures\hashMap.png)

#### 扩容机制

扩容(resize)就是重新计算容量，向HashMap对象里不停的添加元素，而HashMap对象内部的数组无法装载更多的元素时，对象就需要扩大数组的长度，以便能装入更多的元素。当然Java里的数组是无法自动扩容的，方法是使用一个新的数组代替已有的容量小的数组，就像我们用一个小桶装水，如果想装更多的水，就得换大水桶。

我们分析下resize的源码，鉴于JDK1.8融入了红黑树，较复杂，为了便于理解我们仍然使用JDK1.7的代码，好理解一些。

```java
 void resize(int newCapacity) {   //传入新的容量
     Entry[] oldTable = table;    //引用扩容前的Entry数组
     int oldCapacity = oldTable.length;         
     if (oldCapacity == MAXIMUM_CAPACITY) {  //扩容前的数组大小如果已经达到最大(2^30)了
         threshold = Integer.MAX_VALUE; //修改阈值为int的最大值(2^31-1)，这样以后就不会扩容了
         return;
     }
     
     Entry[] newTable = new Entry[newCapacity];  //初始化一个新的Entry数组
     transfer(newTable);                         //！！将数据转移到新的Entry数组里
     table = newTable;                           //HashMap的table属性引用新的Entry数组
     threshold = (int)(newCapacity * loadFactor);//修改阈值
 }
```

这里就是使用一个容量更大的数组来代替已有的容量小的数组，transfer()方法将原有Entry数组的元素拷贝到新的Entry数组里。

```java
void transfer(Entry[] newTable) {
     Entry[] src = table;                   //src引用了旧的Entry数组
     int newCapacity = newTable.length;
     for (int j = 0; j < src.length; j++) { //遍历旧的Entry数组
         Entry<K,V> e = src[j];             //取得旧Entry数组的每个元素
         if (e != null) {
             src[j] = null;//释放旧Entry数组的对象引用（for循环后，旧的Entry数组不再引用任何对象）
             do {
                 Entry<K,V> next = e.next;
                 int i = indexFor(e.hash, newCapacity); //！！重新计算每个元素在数组中的位置
                 e.next = newTable[i]; //标记[1]
                 newTable[i] = e;      //将元素放在数组上
                 e = next;             //访问下一个Entry链上的元素
             } while (e != null);
         }
     }
 }
```

newTable[i]的引用赋给了e.next，也就是使用了单链表的**头插入**方式，同一位置上新元素总会被放在链表的头部位置；这样先放在一个索引上的元素终会被放到Entry链的尾部(如果发生了hash冲突的话），这一点和Jdk1.8有区别，下文详解。在旧数组中同一条Entry链上的元素，通过重新计算索引位置后，有可能被放到了新数组的不同位置上。

下面举个例子说明下扩容过程。假设了我们的hash算法就是简单的用key mod 一下表的大小（也就是数组的长度）。其中的哈希桶数组table的size=2， 所以key = 3、7、5，put顺序依次为 5、7、3。在mod 2以后都冲突在table[1]这里了。这里假设负载因子 loadFactor=1，即当键值对的实际大小size 大于 table的实际大小时进行扩容。接下来的三个步骤是哈希桶数组 resize成4，然后所有的Node重新rehash的过程。

![](pictures\扩容实例.jpg)

下面我们讲解下JDK1.8做了哪些优化。经过观测可以发现，我们使用的是2次幂的扩展(指长度扩为原来2倍)，所以，元素的位置要么是在原位置，要么是在原位置再移动2次幂的位置。看下图可以明白这句话的意思，n为table的长度，图（a）表示扩容前的key1和key2两种key确定索引位置的示例，图（b）表示扩容后key1和key2两种key确定索引位置的示例，其中hash1是key1对应的哈希与高位运算结果。

![](pictures\扩容8.jpg)

元素在重新计算hash之后，因为n变为2倍，那么n-1的mask范围在高位多1bit(红色)，因此新的index就会发生这样的变化：

![](pictures\扩容82.png)

因此，我们在扩充HashMap的时候，不需要像JDK1.7的实现那样重新计算hash，只需要看看原来的hash值新增的那个bit是1还是0就好了，是0的话索引没变，是1的话索引变成“原索引+oldCap”，可以看看下图为16扩充为32的resize示意图：

![](pictures\扩容83.jpg)

这个设计确实非常的巧妙，既省去了重新计算hash值的时间，而且同时，由于新增的1bit是0还是1可以认为是随机的，因此resize的过程，均匀的把之前的冲突的节点分散到新的bucket了。这一块就是JDK1.8新增的优化点。有一点注意区别，JDK1.7中rehash的时候，旧链表迁移新链表的时候，如果在新表的数组索引位置相同，则链表元素会倒置，但是从上图可以看出，JDK1.8不会倒置。如下：

```java
 final Node<K,V>[] resize() {
     Node<K,V>[] oldTab = table;
     int oldCap = (oldTab == null) ? 0 : oldTab.length;
     int oldThr = threshold;
     int newCap, newThr = 0;
     if (oldCap > 0) {
         // 超过最大值就不再扩充了，就只好随你碰撞去吧
         if (oldCap >= MAXIMUM_CAPACITY) {
             threshold = Integer.MAX_VALUE;
             return oldTab;
         }
         // 没超过最大值，就扩充为原来的2倍
         else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                  oldCap >= DEFAULT_INITIAL_CAPACITY)
             newThr = oldThr << 1; // double threshold
     }
     else if (oldThr > 0) // initial capacity was placed in threshold
         newCap = oldThr;
     else {               // zero initial threshold signifies using defaults
         newCap = DEFAULT_INITIAL_CAPACITY;
         newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
     }
     // 计算新的resize上限
     if (newThr == 0) {
 
         float ft = (float)newCap * loadFactor;
         newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                   (int)ft : Integer.MAX_VALUE);
     }
     threshold = newThr;
     @SuppressWarnings({"rawtypes"，"unchecked"})
         Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
     table = newTab;
     if (oldTab != null) {
         // 把每个bucket都移动到新的buckets中
         //如果原hash表不为空，循环将每个索引拆分为两个新的链表
         //一个是跟原索引相同的数据链表，另一个是原索引<<1的数据链表
         //最后将扩容后的新索引分别绑定这两个链表的头节点
         for (int j = 0; j < oldCap; ++j) {
             Node<K,V> e;
             if ((e = oldTab[j]) != null) {
                 oldTab[j] = null;
                 if (e.next == null)
                     newTab[e.hash & (newCap - 1)] = e;
                 else if (e instanceof TreeNode)
                     ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                 else { // 链表优化重hash的代码块
                     Node<K,V> loHead = null, loTail = null;
                     Node<K,V> hiHead = null, hiTail = null;
                     Node<K,V> next;
                     do {
                         next = e.next;
                         // 原索引
                         if ((e.hash & oldCap) == 0) {
                             if (loTail == null)
                                 loHead = e;
                             else
                                 loTail.next = e;
                             loTail = e;
                         }
                         // 原索引+oldCap
                         else {
                             if (hiTail == null)
                                 hiHead = e;
                             else
                                 hiTail.next = e;
                             hiTail = e;
                         }
                     } while ((e = next) != null);
                     // 原索引放到bucket里
                     if (loTail != null) {
                         loTail.next = null;
                         newTab[j] = loHead;
                     }
                     // 原索引+oldCap放到bucket里
                     if (hiTail != null) {
                         hiTail.next = null;
                         newTab[j + oldCap] = hiHead;
                     }
                 }
             }
         }
     }
     return newTab;
 }
```

#### get源码

```java
final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        //哈希表存在
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
            //找到跟第一个节点相同则返回第一个节点
            if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            //存在下一个节点
            if ((e = first.next) != null) {
                //如果是红黑树节点则处理
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                //循环遍历
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
```

#### 红黑树

[1]: https://zhuanlan.zhihu.com/p/79980618	"参考文档"
[2]: https://www.cs.usfca.edu/~galles/visualization/RedBlack.html	"红黑树在线工具"

红黑树，Red-Black Tree 「RBT」是一个自平衡(不是绝对的平衡)的二叉查找树(BST)，树上的每个节点都遵循下面的规则:

1. 每个节点都有红色或黑色
2. 树的根始终是黑色的 (黑土地孕育黑树根， )
3. 没有两个相邻的红色节点（红色节点不能有红色父节点或红色子节点，**并没有说不能出现连续的黑色节点**）
4. 从节点（包括根）到其任何后代NULL节点(叶子结点下方挂的两个空节点，并且认为他们是黑色的)的每条路径都具有相同数量的黑色节点

红黑树有两大操作:

1. recolor (重新标记黑色或红色)
2. rotation (旋转，这是树达到平衡的关键)

假设我们插入的新节点为 X

1. 将新插入的节点标记为红色
2. 如果 X 是根结点(root)，则标记为黑色
3. 如果 X 的 parent 不是黑色，同时 X 也不是 root:

- 3.1 如果 X 的 uncle (叔叔) 是红色

  - 3.1.1 将 parent 和 uncle 标记为黑色

  - 3.1.2 将 grand parent (祖父) 标记为红色
  - 3.1.3 让 X 节点的颜色与 X 祖父的颜色相同，然后重复步骤 2、3


看下图

![](pictures\红黑树1.jpg)

跟着上面的公式走:

1. 将新插入的 X 节点标记为红色
2. 发现 X 的 parent (P) 同样为红色，这违反了红黑树的第三条规则「不能有两个连续相邻的红色节点」
3. 发现 X 的 uncle (U) 同样为红色
4. 将 P 和 U 标记为黑色
5. 将 X 和 X 的 grand parent (G) 标记为相同的颜色，即红色，继续重复公式 2、3
6. 发现 G 是根结点，标记为黑色
7. 结束

刚刚说了 X 的 uncle 是红色的情况，接下来要说是黑色的情况

1. 如果 X 的 parent 不是黑色，同时 X 也不是 root:

- 3.2 如果 X 的 uncle (叔叔) 是黑色，我们要分四种情况处理

- - 3.2.1 左左 (P 是 G 的左孩子，并且 X 是 P 的左孩子)
  - 3.2.2 左右 (P 是 G 的左孩子，并且 X 是 P 的右孩子)
  - 3.2.3 右右 (和 3.2.1 镜像过来，恰好相反)
  - 3.2.4 右左 (和 3.2.2 镜像过来，恰好相反)

当出现 uncle 是黑色的时候我们第一步要考虑的是 **旋转** ，这里先请小伙伴**不要关注红黑树的第 4 条规则**，主要是为了演示如何旋转的，来一点点看，不要看图就慌，有解释的 :

左左情况

这种情况很简单，想象这是一根绳子，手提起 P 节点，然后变色即可

![](pictures\红黑树左左.jpg)

左右情况

左旋: 使 X 的父节点 P 被 X 取代，同时父节点 P 成为 X 的左孩子，然后再应用 **左左情况**

![](pictures\红黑树左右.jpg)

右右情况

与左左情况一样，想象成一根绳子

![](pictures\红黑树右右.jpg)

右左情况

右旋: 使 X 的父节点 P 被 X 取代，同时父节点 P 成为 X 的右孩子，然后再应用 **右右情况**

![](pictures\红黑树右左.jpg)