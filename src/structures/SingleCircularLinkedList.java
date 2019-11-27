package src.structures;

/**
 * Created by Hu Wentao.
 * Email: hu.wentao@outlook.com
 * Date : 2019-11-27
 * Time : 19:15
 * <p>
 * 单向循环链表
 */
public class SingleCircularLinkedList<T> {
    private Node<T> head = new Node<T>();
    private Node<T> trail;
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
        Node<T> tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        for (T t : data) {
            tmp.next = new Node<T>(t);
            tmp = tmp.next;
            len++;
        }
        trail = tmp;
        trail.next = head.next;
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
        while (tmp.next != null && tmp.next.hashCode() != trail.hashCode()) {
            sb.append((tmp.data == null) ? "null, " : (tmp.data + ", "));
            tmp = tmp.next;
        }
        return sb.append((tmp.data == null) ? "null ]" : (tmp.data + " ]")).toString();
    }

    public static void main(String[] args) {
        SingleCircularLinkedList<Number> ss = new SingleCircularLinkedList<>();
        ss.insert(55, 55, 23, 22, 12);
        System.out.println("输出单向循环链表数据: " + ss + "  长度：" + ss.getLen());

        System.out.println("循环输出链表元素两次");
        int i = ss.getLen() * 2;
        Node t = ss.head.next;
        while (i-- > 0) {
            System.out.println(t.data);
            t = t.next;
        }
    }
}
