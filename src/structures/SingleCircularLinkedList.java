package src.structures;

/**
 * Created by Hu Wentao.
 * Email: hu.wentao@outlook.com
 * Date : 2019-11-27
 * Time : 19:15
 * <p>
 * 带头节点的单向循环链表
 */
public class SingleCircularLinkedList<T> {
    private Node<T> head = new Node<>();
    private int len = 0;

    /**
     * 构造方法
     *
     * @param data 传入初始值列表
     */
    SingleCircularLinkedList(T... data) {
        if (data != null)
            this.insert(data);
    }

    private static class Node<R> {
        R data;
        Node<R> next;

        private Node() {
        }

        private Node(R data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    /**
     * 插入元素
     *
     * @param data 元素
     */
    void insert(T... data) {
        if (data == null)
            return;
        for (T t : data) {
            Node<T> n = new Node<>(t);


            if (head.next == null) {
                head.next = n;
                n.next = head;
            } else {
                Node<T> tmp = head.next;
                head.next = n;
                n.next = tmp;
            }
            len++;
        }
    }

    /**
     * 移除指定元素
     *
     * @param data 元素
     */
    void remove(T... data) {
        if (data == null)
            return;
        for (T t : data) {
            Node<T> tmp = head;
            int cyc = len;
            while (cyc-- > 0) {
                if (tmp.next.data.equals(t)) {
                    tmp.next = tmp.next.next;
                    len--;
                }
            }
        }
    }

    /**
     * 返回链表长度
     *
     * @return 链表长度
     */
    int getLen() {
        return len;
    }

    @Override
    public String toString() {
        if (head.next == null) {
            return "[ ]";
        }
        StringBuilder sb = new StringBuilder("[ ");
        Node<T> tmp = head.next;
        int cyc = len;
        // 因为这是带有头节点的链表
        while (cyc-- > 1) {
            sb.append((tmp.data == null) ? "null, " : (tmp.data + ", "));
            tmp = tmp.next;
        }
        return sb.append((tmp.data == null) ? "null ]" : (tmp.data + " ]")).toString();
    }

    public static void main(String[] args) {
        SingleCircularLinkedList<Number> ss = new SingleCircularLinkedList<>();
        ss.insert(5, 1, 2, 3, 4);
        System.out.println("输出单向循环链表数据: " + ss + "  长度：" + ss.getLen());

        System.out.println("循环输出链表元素两次");
        int i = ss.getLen() * 3;
        Node t = ss.head.next;
        while (i-- > 0) {
            System.out.println(t.data);
            t = t.next;
        }
    }
}
