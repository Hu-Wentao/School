
package structures;

/**
 * Created by Hu Wentao.
 * Email: hu.wentao@outlook.com
 * Date : 2019/12/11
 * Time : 14:56
 * <p>
 * 使用链表实现的基本顺序栈
 *
 * @param <T> 元素泛型
 */
public class Stack<T> {
    private Node<T> popElem;

    Stack(T... elements) {
        for (T element : elements)
            this.push(element);
    }

    private static class Node<R> {
        R data;
        Node<R> pre;

        private Node(R data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    /**
     * 入栈
     *
     * @param data 数据
     */
    public void push(T data) {
        Node<T> tmp = new Node<>(data);
        tmp.pre = popElem;
        popElem = tmp;
    }

    /**
     * 出栈
     */
    public T pop() {
        if (popElem == null) return null;
        Node<T> tmp = popElem;
        popElem = popElem.pre;
        return tmp.data;
    }

    /**
     * 取栈顶元素
     * (不出栈)
     */
    public T peek() {
        if (popElem == null) return null;
        return popElem.data;
    }

    @Override
    public String toString() {
        if (popElem == null) {
            return "[ ]";
        }
        Node tmp = popElem;
        StringBuilder sb = new StringBuilder("[ ");
        while (tmp.pre != null) {
            sb.append(tmp.data).append(", ");
            tmp = tmp.pre;
        }
        sb.append(tmp.data).append(" ]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Stack<String> ss = new Stack<>("TT", "e", "s", "t");
        System.out.println("打印栈内元素:" + ss);
        System.out.println("抛出栈顶元素:" + ss.pop());
        System.out.println("打印栈内元素:" + ss);
        System.out.println("入栈 xxx ");
        ss.push("xxx");
        System.out.println("打印栈内元素:" + ss);
        System.out.println("当前栈顶元素是: " + ss.peek());
    }
}
