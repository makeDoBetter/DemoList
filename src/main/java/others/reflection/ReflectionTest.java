package others.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射就是在运行时才知道要操作的类是什么，并且可以在运行时获取类的完整构造，并调用对应的方法。
 *
 * 常用api
 * 获得构造方法：getConstructor()
 * 获得成员方法：getMethod()
 * 获得成员变量：getField()
 * 当我们去获取类方法、类构造器时，如果要获取私有方法或私有构造器，则必须使用有 declared 关键字的方法。
 *
 * 创建类对象有两种方法
 * 1.通过构造函数对象创建类对象
 * 2.通过Class对象创建类对象
 *
 * 通过反射运行普通成员方法
 *  1.获得方法对象
 *  2.获得构造方法
 *  3.通过构造方法获得运行成员方法的类对象
 *  4.通过invoke()执行成员方法
 *
 *  getField 获得类的公共变量，即修饰符为public的变量
 *  getDeclaredFields 可以获得包括私有变量的反射类变量，但是不包括继承的变量
 *
 *  拓展
 *  Spring的Bean配置文件，spring在启动的时候会通过反射加载配置的类，当类不存在或发生启发异常时，异常堆栈便会将异常指向
 *  调用的 invoke 方法。
 * @author fengjirong
 * @date 2021/1/7 10:44
 */
public class ReflectionTest {
    public static void main(String[] args) {
        Class clazz;
        {
            try {
                clazz = Class.forName("others.reflection.Sample");
                //获得其父类
                Class superclass = clazz.getSuperclass();
                System.out.println(superclass.getName());
                //静态方法
                //获得类方法需要指定方法命与方法参数类型
                Method method01 = clazz.getDeclaredMethod("method01", int.class);
                //invoke()第一个参数为操作方法的对象，第二个参数为操作此方法的参数
                //如果方法为静态方法，则第一个参数可以为null
                method01.invoke(null, 1);
                //普通成员方法
                //1.获得方法对象
                //2.获得构造方法
                //3.通过构造方法获得运行成员方法的对象
                //执行成员方法
                Method method02 = clazz.getDeclaredMethod("method02", int.class, int.class);
                Constructor constructor = clazz.getDeclaredConstructor(int.class);
                //创建类对象有两种方法
                //1.通过构造函数对象创建类对象
                //2.通过Class对象创建类对象
                Object o = constructor.newInstance(3);
                //Object o1 = clazz.newInstance();
                method02.invoke(o,2, 3);
                Method[] methods = clazz.getMethods();
                for (Method method :methods) {
                    System.out.println(method.getName());
                }
                //getField 获得类的公共变量，即修饰符为public的变量
                Field i = clazz.getField("I");
                //get方法可以获得任意类型的变量值
                System.out.println(i.get(o));
                //getDeclaredFields 可以获得包括私有变量的反射类变量，但是不包括继承的变量
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    System.out.println(f.getName());
                }
                Field j = clazz.getField("j");
                System.out.println(j.get(o));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
