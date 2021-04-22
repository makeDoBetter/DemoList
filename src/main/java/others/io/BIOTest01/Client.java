package others.io.BIOTest01;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author fengjirong
 * @date 2020/12/18 16:03
 */
public class Client {

    public static void main(String[] args) {
        try {
            Socket  socket = new Socket("127.0.0.1", 1234);
            PrintStream ps = new PrintStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            while(true){
                System.out.print("请说：");
                ps.println(sc.nextLine());
                ps.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
