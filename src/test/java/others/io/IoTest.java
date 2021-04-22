package others.io;

import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 节点流测试类
 * @author fengjirong
 * @date 2020/9/11 14:45
 */
public class IoTest {

    /**
     * 从文件中读取字符流，并且将其输出到控制台
     *
     * @return
     * @Author fengjirong
     * @Date   2020/9/11 14:46
     */
    @Test
    public void doRead() throws IOException {
        FileReader reader = null;
        //异常处理，防止意外的IOException抛出导致流没有关闭，浪费资源
        try {
            //实例化File对象
            File file = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\hello.txt");
            //创建字符输入流
            reader = new FileReader(file);
            //读取字符
            //read()方法返回一个字符或者-1，当读取文件结束时返回-1
            int data;
            while ((data = reader.read())!=-1){
                System.out.print((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            //因为jvm对物理连接的空闲资源无能为力，因此数据库连接，输入输出流等需要手动关闭
            try {
                if (reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * read()方法的重载
     * read(arr[])方法将每次读取一个字符，变成每次打包一个指定长度的数组字符读取
     *
     * @return
     * @Author fengjirong
     * @Date   2020/9/11 15:16
     */
    @Test
    public void doRead01() {
        FileReader fr = null;
        try {
            //File的实例化
            File file = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\hello.txt");
            //FileReader流的实例化
            fr = new FileReader(file);
            //读取数据
            //.read(数组)，每次返回的是写入数组的个数，如果读取到了文件末尾，则返回-1
            char[] cBuffer = new char[5];
            int len;
            while((len = fr.read(cBuffer)) != -1){
                //方法一
                /*
                这是错误写法，每次写入数组中的数据只是替换对应位置
                假如 倒数第二次 1 2 3 4 5，此时文件中剩下 6没有读，则最后一次数组中为 6 2 3 4 5
                因此for循环时不可以采用.length
                for (int i = 0; i < cBuffer.length; i++){
                    System.out.print(cBuffer[i]);
                }*/
                //正确写法
                //以每次读取的字符数量上限
                /*for (int i = 0; i < len; i++){
                    System.out.print(cBuffer[i]);
                }*/
                //方法二
                String s = new String(cBuffer,0, len);
                System.out.print(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * FileWriter测试操作
     *
     * 1. FileWriter实例化的File对象可以在硬盘中不存在，如果不存在则会创建；
     * 2.
     *      如果初始化流操作为：FileWriter(file)或FileWriter(file, false):写入操作会覆盖原文件中的字符；
     *      如果初始化流操作为：FileWriter(file, true)：写入操作会在原有文件的基础上追加新写入字符
     *
     *
     * @return
     * @Author fengjirong
     * @Date   2020/9/11 16:34
     */
    @Test
    public void doWriter(){
        FileReader fr = null;
        try {
            //File实例化
            File file = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\hello_01.txt");
            //FileWriter 实例化
            //FileWriter(file)或FileWriter(file, false):写入操作会覆盖原文件中的字符；
            //FileWriter(file, true)：写入操作会在原有文件的基础上追加新写入字符
            FileWriter fw = new FileWriter(file, true);
            //写数据操作
            fw.write("this is a FileWriter IO test;\n");
            fw.write("and it`s successful\n");
            //关闭流
            fw.close();

            //以下操作为将写入数据打印到控制台
            fr = null;
            //File的实例化
            File fileIn = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\hello_01.txt");
            //FileReader流的实例化
            fr = new FileReader(fileIn);
            //读取数据
            //.read(数组)，每次返回的是写入数组的个数，如果读取到了文件末尾，则返回-1
            char[] cBuffer = new char[5];
            int len;
            while((len = fr.read(cBuffer)) != -1){
                //方法一
                /*
                这是错误写法，每次写入数组中的数据只是替换对应位置
                假如 倒数第二次 1 2 3 4 5，此时文件中剩下 6没有读，则最后一次数组中为 6 2 3 4 5
                因此for循环时不可以采用.length
                for (int i = 0; i < cBuffer.length; i++){
                    System.out.print(cBuffer[i]);
                }*/
                //正确写法
                //以每次读取的字符数量上限
                /*for (int i = 0; i < len; i++){
                    System.out.print(cBuffer[i]);
                }*/
                //方法二
                String s = new String(cBuffer,0, len);
                System.out.print(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        //关闭流
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * IO文本复制
     *
     * @return
     * @Author fengjirong
     * @Date   2020/9/15 10:12
     */
    @Test
    public void doCopy(){
        FileReader  reader = null;
        FileWriter  writer = null;
        try {
            //初始化File对象
            File inFile = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\hello_01.txt");
            File outFile = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\hello_02.txt");
            //初始化IO对象
            reader = new FileReader(inFile);
            writer = new FileWriter(outFile);
            //操作IO
            int len;
            char[] cbuffer = new char[1024];
            while((len = reader.read(cbuffer))!=-1){
                writer.write(cbuffer, 0 , len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭IO
            try {
                if(reader != null){
                    writer.close();
                }
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 图片/视频的拷贝，非纯文本数据只能使用字节流进行数据的写入读出
     *
     * @return
     * @Author fengjirong
     * @Date   2020/9/15 10:17
     */
    @Test
    public void doCopyByteData() {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            //初始化File对象
            File in = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\picture03.jpg");
            File out = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\picture.jpg");
            //初始化IO对象
            inputStream = new FileInputStream(in);
            outputStream = new FileOutputStream(out);
            //操作IO
            int len;
            byte[] bbuffer = new byte[1024];
            while((len = inputStream.read(bbuffer)) != -1){
                //图片加密/解密
                //将获取的每一个字节与一个特殊数字进行异或运算，即可对数据进行加密，
                // 之后如果使用同样数字再进行一次异或运算，数据便可实现解密
                /*for (int i = 0; i < len; i++) {
                    bbuffer[i] = (byte) (bbuffer[i]^5);
                }*/
                outputStream.write(bbuffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭IO
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文本中的每个字符出现的次数，写入map对象中
     *
     * @return
     * @Author fengjirong
     * @Date   2020/9/15 11:37
     */
    @Test
    public void getCharCount(){
        FileReader reader = null;
        try {
            //初始化File对象
            File file = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\hello_01.txt");
            //初始化IO对象
            reader = new FileReader(file);
            //操作IO
            int len;
            char[] buff = new char[10];
            HashMap<Character, Integer> map = new HashMap<Character, Integer>();
            while ((len = reader.read(buff)) != -1){
                for (int i = 0; i < len; i++) {
                    if (map.containsKey(buff[i])) {
                        map.replace(buff[i], map.get(buff[i])+1);
                    }else {
                        map.put(buff[i],1);
                    }
                }
            }
            for (Map.Entry<Character, Integer> entry : map.entrySet()){
                System.out.println("key: "+entry.getKey()+"  value:"+entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭IO
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
