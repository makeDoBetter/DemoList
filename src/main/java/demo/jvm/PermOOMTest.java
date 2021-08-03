package demo.jvm;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Description: 验证永久代OOM
 * 首先设置永久代内存 -XX:MetaspaceSize=16m -XX:MaxMetaspaceSize=16m
 * 使用cglib动态代理策略不断创建代理类
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/17 11:54
 * @since JDK 1.8
 */
public class PermOOMTest {
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            //无限创建动态代理，生成Class对象
            enhancer.create();
        }
    }

    static class OOMObject {

    }
}
