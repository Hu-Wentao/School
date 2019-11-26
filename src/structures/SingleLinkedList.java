package src.structures;


/**
 * Created by Hu Wentao.
 * Email: hu.wentao@outlook.com
 * Date : 2019-11-22
 * Time : 23:02
 * <p>
 * 单链表，实现插入和删除节点，去重
 *
 * @param <T> 节点存储的数据类型
 */
class SingleLinkedList<T> {
    private Node<T> head = new Node<T>();
    private int len = 0;


    /**
     * 尽量不要使用
     * <p>
     * 使用带有泛型的数组会导致堆污染，
     * 使得后面的去除重复值方法里的equals()失效
     *
     * @param data 传入初始值
     */
    SingleLinkedList(T... data) {
        if (data != null)
            for (T t : data)
                this.insert(t);
    }

    private static class Node<R> {
        R data;
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
        tmp.next = n;
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
     * 逆序链表
     */
    void reverse() {
        if (len <= 1) return;
        Node<T> a, b, c, x;
        b = head.next.next;
        x = head.next;
        do {
            a = head.next;
            c = b.next;
            head.next = b;
            b.next = a;
            x.next = c;
            b = c;
        } while (b != null);
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
        // 使用本方法构造初始值会产生堆污染，导致removeDuplicate()方法执行效果与预期不一致
//        SingleLinkedList<Number> ss = new SingleLinkedList<>(11, 2.2f, 3.33, 4.4d, 55,55,55);
        SingleLinkedList<Number> ss = new SingleLinkedList<>(55,55);
//        SingleLinkedList<Number> ss = new SingleLinkedList<>();
        ss.insert(55);
        System.out.println("原始数据: " + ss + "  长度：" + ss.getLen());

        ss.reverse();
        System.out.println("逆序元素后：" + ss);


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
