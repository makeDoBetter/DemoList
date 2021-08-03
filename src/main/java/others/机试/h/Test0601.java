package others.机试.h;

import java.util.Scanner;
import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 *
 * {[()]}
 * 123321
 * {[}()]
 * 121332
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/6/1 19:02
 * @since JDK 1.8
 */
public class Test0601 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String next = scanner.next();
            char[] array = next.toCharArray();

        }
    }

    private static boolean doMain(String str){
        String reStr = new StringBuilder(str).reverse().toString();
        Stack<Character> stack = new Stack<>();
        char[] array = reStr.toCharArray();
        for (char c : array) {
            stack.push(c);
        }
        while (!stack.empty()){
            Character ch = stack.pop();

        }
        return false;
    }
}
