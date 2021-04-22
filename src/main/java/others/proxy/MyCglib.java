package others.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/3/26 15:18
 * @since JDK 1.8
 */
public class MyCglib {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SellCglib.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object invoke = null;
                if ("doOther".equals(method.getName())){
                    System.out.println("销售前该做的事情");
                    invoke = methodProxy.invokeSuper(o, args);
                    System.out.println("做完了");
                    System.out.println(o.getClass().getName());
                    System.out.println(method.toString());
                    System.out.println(methodProxy.toString());

                }
                return invoke;
            }
        });
        SellCglib c = (SellCglib)enhancer.create();
        c.print();
        c.doOther();
    }
}
