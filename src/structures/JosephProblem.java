package src.structures;

import java.util.Arrays;

/**
 * Created by Hu Wentao.
 * Email: hu.wentao@outlook.com
 * Date : 2019-11-27
 * Time : 21:48
 * <p>
 * 约瑟夫问题
 * 使用不带头节点的单向链表
 */
public class JosephProblem {
    private Node head = new Node();
    private int len = 0;

    JosephProblem(int... data) {
        if (data != null)
            this.insert(data);
    }

    private class Node {
        int data;
        private Node next;

        Node getNext(){
            if(next == null)
                return head.next;
            return next;
        }

        private Node() {
        }

        private Node(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return String.valueOf(data);
        }
    }

    /**
     * 尾插入元素
     *
     * @param data 元素
     */
    void insert(int... data) {
        if (data == null)
            return;
        Node tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        for (int t : data) {
            tmp.next = new Node(t);
            tmp = tmp.next;
            len++;
        }
    }

    /**
     * 移除指定元素
     *
     * @param data 元素
     */
    void remove(int... data) {
        if (data == null)
            return;
        for (int t : data) {
            Node tmp = head;
            int cyc = len;
            while (cyc-- > 0) {
                if (tmp.next.data == t) {
                    tmp.next = tmp.next.next;
                    len--;
                }
            }
        }
    }

    /**
     * 获取答案
     *
     * @param m 报 m 的人出列
     * @return 出列人数的编号序列
     */
    private int[] getAns(int m) {
        int[] ans = new int[len];

        Node cur = head.getNext();
        for (int i = 0; i < ans.length; i++) {
            int cyc = m;
            while (cyc-- > 1) {
                cur = cur.getNext();
            }
            ans[i] = cur.data;
            remove(cur.data);
            System.out.println("移除" + cur.data);
        }
        return ans;
    }

    @Override
    public String toString() {
        if (head.next == null) {
            return "[ ]";
        }
        StringBuilder sb = new StringBuilder("[ ");
        Node tmp = head.next;
        int cyc = len;
        while (cyc-- > 1) {
            sb.append(tmp.data).append(", ");
            tmp = tmp.next;
        }
        return sb.append(tmp.data).append(" ]").toString();
    }

    public static void main(String[] args) {

        JosephProblem j = new JosephProblem(3, 1, 7, 2, 4, 8, 4);
        System.out.println("答案是：" + Arrays.toString(j.getAns(20)));
    }

}
