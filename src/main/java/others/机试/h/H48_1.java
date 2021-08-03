package others.机试.h;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 输入一个单向链表和一个节点的值，从单向链表中删除等于该值的节点，删除后如果链表中无节点则返回空指针。
 * 链表的值不能重复。
 * <p>
 * 构造过程，例如输入一行数据为:
 * 6 2 1 2 3 2 5 1 4 5 7 2 2
 * 则第一个参数6表示输入总共6个节点，第二个参数2表示头节点值为2，剩下的2个一组表示第2个节点值后面插入第1个节点值，为以下表示:
 * 1 2 表示为
 * 2->1
 * 链表为2->1
 * <p>
 * 3 2表示为
 * 2->3
 * 链表为2->3->1
 * <p>
 * 5 1表示为
 * 1->5
 * 链表为2->3->1->5
 * <p>
 * 4 5表示为
 * 5->4
 * 链表为2->3->1->5->4
 * <p>
 * 7 2表示为
 * 2->7
 * 链表为2->7->3->1->5->4
 * <p>
 * 最后的链表的顺序为 2 7 3 1 5 4
 * <p>
 * 最后一个参数为2，表示要删掉节点为2的值
 * 删除 结点 2
 * 则结果为 7 3 1 5 4
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/10 23:08
 * @since JDK 1.8
 */
public class H48_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int headVal = scanner.nextInt();
            Node head = new Node(headVal);
            for (int i = 1; i < n; i++) {
                int next = scanner.nextInt();
                int pre = scanner.nextInt();
                insert(head, next, pre);
            }
            int target = scanner.nextInt();
            Node headDel = deleteQueue(head, target);
            while (headDel != null) {
                System.out.print(headDel.getVal() + " ");
                headDel = headDel.next;
            }
        }

    }

    private static void insert(Node head, int next, int pre) {
        Node current = head;
        Node node = new Node(next);
        while (current != null) {
            if (current.getVal() == pre) {
                node.next = current.next;
                current.next = node;
                break;
            } else {
                current = current.next;
            }
        }
    }

    private static Node deleteQueue(Node head, int target) {
        Queue<Node> queue = new LinkedList<>();
        Node tmp = null;
        while (head != null) {
            if (head.getVal() != target) {
                queue.offer(head);
            }
            head = head.next;
        }
        if (!queue.isEmpty()) {
            head = queue.poll();
            tmp = head;
        }
        while (!queue.isEmpty()) {
            tmp.next = queue.poll();
            tmp = tmp.next;
        }
        return head;
    }

    static class Node {
        private int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }
}
