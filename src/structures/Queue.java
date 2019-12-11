package structures;

/**
 * Created by Hu Wentao.
 * Email: hu.wentao@outlook.com
 * Date : 2019/12/11
 * Time : 15:37
 * 基于链表实现的简单队列
 */
public class Queue<T> {
    private Node<T> head;
    private Node<T> trail;

    public Queue(T... elements) {
        for (T element : elements)
            this.enQueue(element);
    }

    private static class Node<R> {
        R data;
        Node<R> pre;
        Node<R> next;

        private Node(R data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    /**
     * 入队列
     *
     * @param data 数据
     */
    public void enQueue(T data) {
        Node<T> tmp = new Node<>(data);
        if (head == null) {
            head = trail = tmp;
        } else {
            tmp.next = head;
            head = tmp;
            head.next.pre = head;
        }
    }

    /**
     * 出队列
     *
     * @return 返回尾部
     */
    public T deQueue() {
        if (trail == null) return null;
        T r = trail.data;
        trail = trail.pre;
        trail.next = null;
        return r;
    }

    /**
     * 取队列头
     *
     * @return 队列头
     */
    public T getHead() {
        if (head == null) return null;
        return head.data;
    }

    @Override
    public String toString() {
        Node tmp = head;
        StringBuilder sb = new StringBuilder("[ ");
        while (tmp.next != null) {
            sb.append(tmp.data).append(", ");
            tmp = tmp.next;
        }
        return sb.append(tmp.data).append(" ]").toString();
    }

    public static void main(String[] args) {
        Queue<String> qq = new Queue<>("T", "e", "s", "tt");
        System.out.println("打印队列元素: " + qq);
        System.out.println("抛出队列尾: " + qq.deQueue());
        System.out.println("抛出队列尾: " + qq.deQueue());
        System.out.println("出队列后: " + qq);
        qq.enQueue("xxx");
        System.out.println("xxx 入队列后: " + qq);
        System.out.println("当前队列头: " + qq.getHead());
    }
}
