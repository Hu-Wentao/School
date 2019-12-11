package structures.linked.list;

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
    private int len = 0;
    private Node<T> head = new Node<T>();
    private Node<T> trail;

    DoubleLinkedList(T... data) {
        if (data != null)
            this.insert(data);
    }

    private static class Node<R> {
        R data;
        Node<R> previous;
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
            tmp.next = new Node<>(t);
            tmp.next.previous = tmp;
            tmp = tmp.next;
            len++;
        }
        trail = tmp;
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
        Node<T> n = new Node<T>(data);

        Node<T> tmp = head;
        while (index-- > 0 && tmp.next != null) {
            tmp = tmp.next;
            if (tmp.next == null)
                trail = n;
        }
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
        boolean needResetTrail = false;
        for (T t : data) {
            needResetTrail = t.equals(trail.data);
            Node<T> tmp = head;
            if (tmp.next == null) break;
            while (!tmp.next.data.equals(t)) {
                tmp = tmp.next;
                if (tmp.next == null)
                    return;
            }
            tmp.next = tmp.next.next;
            if (tmp.next != null)
                tmp.next.previous = tmp;
            len--;
        }
        // 移除元素之后，需要重新扫描末尾节点，赋给trail
        if (needResetTrail)
            trail = getTrail();
    }

    private Node<T> getTrail() {
        Node<T> tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        return tmp;
    }

    /**
     * 清空链表中的元素
     */
    void removeAll() {
        head.next = null;
        trail = null;
        len = 0;
    }

    /**
     * 去除链表中的重复元素
     */
    void removeDuplicate() {
        if (len <= 1) return;
        // 开始的节点
        Node<T> select = head.next;
        while (select != null) {
            // 用 tmp.next 来表示 与select进行比较 的节点
            Node<T> tmp = select;
            while (tmp.next != null) {
                if (select.data.equals(tmp.next.data)) {
                    tmp.next = tmp.next.next;
                } else {
                    tmp = tmp.next;
                }
            }
            select = select.next;
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
        ss.insert(55, 55, 23, 22, 12);
        ss.insert(99);
        ss.remove(99);
        System.out.println("原始数据: " + ss + "  长度：" + ss.getLen());

        System.out.print("手动逆向输出: ");
        Node t = ss.trail;
        while (t.previous != null) {
            System.out.printf(" %s, ", t.data);
            t = t.previous;
        }
        System.out.println("\nfoot: " + ss.trail.data);

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
