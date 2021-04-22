package others.Annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * java 注解测试类
 * @author fengjirong
 * @date 2021/1/12 16:08
 */
@MyAnnotation
public class AnnotationTest {
    public static void main(String[] args) {
        System.out.println("main");
    }
}

@Service
class AnnotationSon extends AnnotationTest{

    @MyAnnotation(name = "李四", age = 18)
    public void test01(){
        System.out.println("hello world");
    }

    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("others.Annotation.AnnotationSon");
            Method method = clazz.getDeclaredMethod("test01");
            Constructor constructor = clazz.getDeclaredConstructor();
            Object o = constructor.newInstance();
            method.invoke(o, null);
            //isAnnotationPresent() 是否存在注解
            boolean flag = method.isAnnotationPresent(MyAnnotation.class);
            //System.out.println(EnumTest.FALSE.value());
            if (flag){
                //获得注解的属性值
                MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
                System.out.println(annotation.toString());
                System.out.println(annotation.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * 验证 @Inherited 注解的继承性
 * */
class test{
    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("others.Annotation.AnnotationSon");
            boolean flag = clazz.isAnnotationPresent(MyAnnotation.class);
            if (flag){
                Annotation annotation = clazz.getAnnotation(MyAnnotation.class);
                System.out.println(annotation.toString());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
