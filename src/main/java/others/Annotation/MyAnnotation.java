package others.Annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 元注解
 * 元注解顾名思义我们可以理解为注解的注解，它是作用在注解中，方便我们使用注解实现想要的功能。
 * @Retention 表示注解存在阶段： SOURCE 源码； CLASS 编译； RUNTIME 运行时
 *
 * @Target 表示我们的注解作用的范围
 * @Target(ElementType.TYPE) 作用接口、类、枚举、注解
 * @Target(ElementType.FIELD) 作用属性字段、枚举的常量
 * @Target(ElementType.METHOD) 作用方法
 * @Target(ElementType.PARAMETER) 作用方法参数
 * @Target(ElementType.CONSTRUCTOR) 作用构造函数
 * @Target(ElementType.LOCAL_VARIABLE) 作用局部变量
 * @Target(ElementType.ANNOTATION_TYPE) 作用于注解（@Retention注解中就使用该属性）
 * @Target(ElementType.PACKAGE) 作用于包
 * @Target(ElementType.TYPE_PARAMETER) 作用于类型泛型，即泛型方法、泛型类、泛型接口 （jdk1.8加入）
 * @Target(ElementType.TYPE_USE) 类型使用.可以用于标注任意类型除了 class （jdk1.8加入）
 * 一般比较常用的是ElementType.TYPE类型
 *
 * @Documented 作用是能够将注解中的元素包含到 Javadoc 中去
 *
 * @Inherited 继承，使用此元注解的注解的类，将会继承这个注解
 *
 * 注解属性
 * 注解的属性其实和类中定义的变量有异曲同工之处，只是注解中的变量都是成员变量（属性），并且注解中是没有方法的，
 * 只有成员变量，变量名就是使用注解括号中对应的参数名，变量返回值注解括号中对应参数类型。
 * 注解属性类型可以有以下列出的类型
 * 1.基本数据类型
 * 2.String
 * 3.枚举类型
 * 4.注解类型
 * 5.Class类型
 * 6.以上类型的一维数组类型
 *
 *
 * @author fengjirong
 * @date 2021/1/12 16:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface MyAnnotation {
    //String
    String name() default "张三";

    //基础数据类型
    int age() default  -1;

    /**
     * 枚举类型
     * */
    //enum EnumTest {TRUE};

    //Class类型
    //Class clazz() default Retention.class;

    //一维数组
    //String[] arr() default {"hello", "world"};

}
