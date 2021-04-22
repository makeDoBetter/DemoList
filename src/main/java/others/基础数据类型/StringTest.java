package others.基础数据类型;

/**
 * String方法测试
 *
 * String创建后不能更改，日常中修改只是改变了引用指向的对象，原对象并没有发生改变
 * 查看源码可以注意到，拼接等修改方法，都是返回一个新的对象new String()，因此String对象是
 * 不可改变的；
 * 同时，String中无类似set公共方法，因此外部是无法对String对象进行修改的；
 * 多个引用会同时指向同一个方法区的运行时常量池中的对象，如果一个对象修改了对象，
 * 那么其他引用处也会发生改变，这是不安全的。
 *
 * tips：可通过反射修改引用指向的对象
 *
 * String s = "abc"
 * JVM首先在运行时常量池中查看是否存在String对象“abc”，如果已存在该对象，则不用创建新的String对象“abc”，
 * 而是将引用s直接指向运行时常量池中已存在的String对象“abc”；如果不存在该对象，则先在运行时常量池中创建一个
 * 新的String对象“abc”，然后将引用s指向运行时常量池中创建的新String对象
 * 
 * @author fengjirong
 * @date 2020/10/10 14:44
 */
public class StringTest {
    public static void main(String[] args) {
        String sou = "this is a world";
        String dst = "hello world";
        //相同对象，相同长度，每个字符
        sou.equals(dst);
        //忽略大小写
        sou.equalsIgnoreCase(dst);
        //相同索引下字符不同，返回相同索引下当前字符与目标字符的差值，否则返回0
        System.out.println(sou.compareTo(dst));
        //消除大小写格式
        sou.compareToIgnoreCase(dst);
        //去头尾空格，前后分别寻找空格，截取前后下标中间的字符串
        System.out.println("   12   3 4   ".trim());
        //拼接字符串到末尾，新增char数组，复制源数据，追加目标字符串
        System.out.println(sou.concat(dst));
        //字符串长度
        System.out.println(sou.length());
        //替换字符，如果相同返回原引用，否则返回新的替换引用
        System.out.println(sou.replace('i','I'));
        //替换字符串，正则表达式匹配，StringBuffer处理
        System.out.println(sou.replaceAll("is", "HE"));
        //返回引用对象的hash
        System.out.println(sou.hashCode());
        //判断是否为空字符串，底层是判断字符数组长度是否为0，无法判断null
        //sou = null;
        if (sou.isEmpty()) {
            System.out.println("null");
        }
        //返回当前对象的引用
        sou.toString();
        //返回指定下标的字符，判断返回，返回字符数组指定下标字符
        System.out.println(sou.charAt(2));
        //返回指定索引字符的Unicode
        System.out.println(sou.codePointAt(2));
        //返回指定索引上一个字符的Unicode
        System.out.println(sou.codePointBefore(2));
        //字符串长度，类似length，但是采用Unicode进行判断
        System.out.println(sou.codePointCount(1,10));
        //指定字符串是否存在
        if (sou.contains("is")) {
            System.out.println("here");
        }
        //字符串比较，
        // 与equals()差别：equals只能比较两个String对象，
        // 而contentEquals可以比较CharSequence接口，String,StringBuffer,StringBuild都实现这个接口
        if (sou.contentEquals("this is a world")) {
            System.out.println("here");
        }
        //是否以某个字符结尾
        if (sou.endsWith("d")) {
            System.out.println("here");
        }
    }
}
