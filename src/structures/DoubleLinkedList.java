package src.structures;

/**
 * Created by Hu Wentao.
 * Email: hu.wentao@outlook.com
 * Date : 2019-11-26
 * Time : 21:04
 * <p>
 * 双链表，实现插入和删除节点，输出方法
 *
 * @param <T> 节点存储的数据类型
 */
public class DoubleLinkedList<T> {
    private Node<T> head = new Node<T>();
    private int len = 0;

    private static class Node<R> {
        R data;
        Node<R> previous;
        Node<R> next;

        private Node() {
        }

        private Node(R data) {
            this.data = data;
        }
    }

    /**
     * 插入元素
     *
     * @param data 元素
     */
    void insert(T data) {
        if (data == null)
            return;
        Node<T> tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = new Node<T>(data);
        tmp.next.previous = tmp;
        len++;
    }

    /**
     * 在指定位置的前面插入元素
     *
     * @param index 位置索引号，从0开始
     * @param data  元素
     */
    void insert(int index, T data) {
        if (index < 0 || data == null)
            return;

        Node<T> tmp = head;
        while (index-- > 0 && tmp.next != null) {
            tmp = tmp.next;
        }
        Node<T> n = new Node<T>(data);
        n.next = tmp.next;
        if (tmp.next != null)
            tmp.next.previous = n;
        tmp.next = n;
        n.previous = tmp;
        len++;
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
            while (!tmp.next.data.equals(t)) {
                tmp = tmp.next;
                if (tmp.next == null)
                    return;
            }
            tmp.next = tmp.next.next;
        }
        len--;
    }

    /**
     * 清空链表中的元素
     */
    void removeAll() {
        head.next = null;
        len = 0;
    }

    /**
     * 去除链表中的重复元素
     */
    void removeDuplicate() {
        if (len <= 1) return;
        // 开始的节点
        Node<T> start = head.next;
        while (start != null) {
            Node<T> select = start;
            Node<T> tmp = select;
            // 用 tmp.next 来表示 与select进行比较 的节点
            while (tmp.next != null) {
                if (select.data.equals(tmp.next.data)) {
                    tmp.next = tmp.next.next;
                }
                if (tmp.next == null)
                    break;
                else
                    tmp = tmp.next;
            }
            start = start.next;
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
        while (tmp.next != null) {
            sb.append((tmp.data == null) ? "null, " : (tmp.data + ", "));
            tmp = tmp.next;
        }
        return sb.append((tmp.data == null) ? "null ]" : (tmp.data + " ]")).toString();
    }

    public static void main(String[] args) {
        DoubleLinkedList<Number> ss = new DoubleLinkedList<>();
        ss.insert(55);
        ss.insert(55);
        ss.insert(55);
        System.out.println("原始数据: " + ss + "  长度：" + ss.getLen());

        ss.remove(11, 3.33);
        System.out.println("移除数据项后: " + ss);

        ss.removeDuplicate();
        System.out.println("移除重复数据项后：" + ss);

        ss.insert(1, 6666);
        System.out.println("插入新的数据项后: " + ss);

        ss.removeAll();
        System.out.println("删除所有元素后：" + ss);


    }
}
