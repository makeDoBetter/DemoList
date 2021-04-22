package others.thread.ThreadNoLook;

/**
 *
 * 单例模式下，DCL(Double Check Lock)处理多线程下创建单例的问题
 *
 * 单例模式：
 * 保证一个类仅有一个实例，并提供一个访问它的全局访问点。
 * 单例模式是一种常用的软件设计模式之一，其目的是保证整个应用中只存在类的唯一个实例。
 *
 * 参考：https://www.cnblogs.com/codingmengmeng/p/9846131.html
 *
 * 场景，单例模式下
 * @author fengjirong
 * @date 2020/9/25 14:34
 */
public class DCLTest {
}

/**
 * 假设一个这样的场景，存在两个线程
 * 线程1判断singleton == null将要执行new时，cpu被线程2抢占，线程2执行new；
 * 接下来线程1继续执行new，那么这将不是单例模式
 *
 * 因此当多个线程同时访问getInstance()时，可能会产生多个实例
 */
class Singleton01{
    private static Singleton01 singleton = null;

    public static Singleton01 getSingleton(){
        if (singleton == null){
            new Singleton01();
        }
        return singleton;
    }
}

/**
 *
 * 代码块保持线程同步后，还需要保证jvm对指令的重排，导致初始化过程中指令操作的乱序
 *
 * 假设线程一执行到instance = new Singleton()这句，这里看起来是一句话，但实际上其被编译后在JVM执行的对应会变代码就发现，
 * 这句话被编译成8条汇编指令，大致做了三件事情：
 * 　　1）给instance实例分配内存；
 * 　　2）初始化instance的构造器；
 * 　　3）将instance对象指向分配的内存空间（注意到这步时instance就非null了）
 * 如果指令按照顺序执行倒也无妨，但JVM为了优化指令，提高程序运行效率，允许指令重排序。
 * 如此，在程序真正运行时以上指令执行顺序可能是这样的：
 * 　　a）给instance实例分配内存；
 * 　　b）将instance对象指向分配的内存空间；
 * 　　c）初始化instance的构造器；
 * 这时候，当线程一执行b）完毕，在执行c）之前，被切换到线程二上，这时候instance判断为非空，
 * 此时线程二直接来到return instance语句，拿走instance然后使用，接着就顺理成章地报错（对象尚未初始化）。
 *具体来说就是synchronized虽然保证了线程的原子性（即synchronized块中的语句要么全部执行，要么一条也不执行），
 * 但单条语句编译后形成的指令并不是一个原子操作（即可能该条语句的部分指令未得到执行，就被切换到另一个线程了）。
 *
 * 根据以上分析可知，解决这个问题的方法是：禁止指令重排序优化，即使用volatile变量。
 */
class Singleton02{
    private volatile static Singleton02 singleton = null;

    public static Singleton02 getSingleton(){
        if (null == singleton){
            synchronized(Singleton02.class){
                if (null == singleton){
                    new Singleton02();
                }
            }
        }
        return singleton;
    }
}
