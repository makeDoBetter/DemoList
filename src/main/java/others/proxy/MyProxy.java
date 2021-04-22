package others.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description:jdk动态代理实现
 * 通过传入的对象，或者这个对象对应的类加载器、实现接口，由这两个参数创建一个类，加上回调函数实现代理增强
 * 其最终生成的对象是由接口修饰的一个类
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/3/26 14:45
 * @since JDK 1.8
 */
public class MyProxy<T> implements InvocationHandler {
    private T myObject;

    public Object getObject(T myObject){
        this.myObject = myObject;
        return Proxy.newProxyInstance(myObject.getClass().getClassLoader(), myObject.getClass().getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("首先开始预销售");
        Object invoke = method.invoke(myObject, args);
        System.out.println("之后开始付款");
        return invoke;
    }
}
