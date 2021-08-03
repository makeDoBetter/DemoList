package others.机试.h;


import java.util.Scanner;
import java.util.Stack;

public class H48 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            //输入节点数
            int nodeNum = in.nextInt();
            //输入头节点的值
            int headVal = in.nextInt();
            //构造头节点
            Node pHead = new Node(headVal);
            for (int i = 0; i < nodeNum - 1; i++) {
                //插入节点值
                int nextVal = in.nextInt();
                //前一节点值
                int preVal = in.nextInt();
                //插入节点
                insert(pHead, preVal, nextVal);
            }
            int deleteVal = in.nextInt();
            pHead = removeNode(pHead, deleteVal);
            while (pHead.next != null) {
                System.out.print(pHead.getVal() + " ");
                pHead = pHead.next;
            }
            System.out.println(pHead.getVal() + " ");
        }
    }

    /*
     参数 pHead 头节点
     preVal 上一节点值
     nextVal 插入节点值
    */
    private static void insert(Node pHead, int preVal, int nextVal) {
        Node pNext = new Node(nextVal);
        Node pCurrent = pHead;
        while (pCurrent != null) {
            if (pCurrent.getVal() == preVal) {
                pNext.next = pCurrent.next;
                pCurrent.next = pNext;
                break;
            }
            pCurrent = pCurrent.next;
        }
    }

    private static Node removeNode(Node pHead, int val) {
        Stack<Node> stack = new Stack<Node>();
        while (pHead != null) {
            if (pHead.getVal() != val) {
                stack.push(pHead);
            }
            pHead = pHead.next;
        }
        while (!stack.isEmpty()) {
            stack.peek().next = pHead;
            pHead = stack.pop();
        }
        return pHead;
    }
}

class Node {
    private int val;
    public Node next;

    Node(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
