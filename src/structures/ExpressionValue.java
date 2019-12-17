package structures;


/**
 * Created by Hu Wentao.
 * Email: hu.wentao@outlook.com
 * Date : 2019/12/11
 * Time : 16:11
 */
public class ExpressionValue {
    private java.util.Stack<Double> operand;
    private java.util.Stack<String> operate;
//    private static Map<String, Integer> compareMap;

    public ExpressionValue() {
        operand = new java.util.Stack<>();
        operate = new java.util.Stack<>();
        operate.push("#");
//        compareMap = new HashMap<>();
//        compareMap.put("+", 1);
//        compareMap.put("-", 1);
//        compareMap.put("*", 2);
//        compareMap.put("/", 2);
//        compareMap.put("(", 3);
//        compareMap.put(")", -1);
//        compareMap.put("#", 0);
    }

    /**
     * 表达式求值
     *
     * @param expression 简单表达式
     * @return 值
     */
    private String calcExp(String expression) {
        //        String s = "#1+43*12/(2+3)";
        // 去除表达式中的空格,然后在首尾添加起止符
        expression = expression.replace(" ", "");
        expression = expression + "#";
        // 使用正则表达式分割表达式字符串
        String[] arr = expression.split("((?<=\\+|-|\\*|/|\\(|\\)|#)|(?=\\+|-|\\*|/|\\(|\\)|#))");
        // 操作数 与 操作符 入栈

//        System.out.println(Arrays.toString(arr));
        for (String s : arr) {
            //test
//            System.out.println("1 " + operand.toString());
//            System.out.println("2 " + operate.toString());
//            System.out.println("3 " + s);
            if ("#".equals(s) && "#".equals(operate.peek())) {
                return operand.pop().toString();
            } else if (s.matches("\\d+")) {
                operand.push(Double.valueOf(s));
//            } else if (")".equals(s)) {
//                while (!operate.peek().equals("(")) {
//                    operand.push(calc(operand.pop(), operate.pop(), operand.pop()));
//                }
//                operate.pop();
            } else {
                switch (compare(operate.peek(), s)) {
                    case -1:
                        operate.push(s);
                        break;
                    case 0:
                        operate.pop();
                        break;
                    case 1:
                        operand.push(calc(operand.pop(), operate.pop(), operand.pop()));
                        //test
                        System.out.println("数值栈: " + operand.toString());
                        break;
                }
            }
        }
//        while (operate.peek()!=null) {
//            if(operate.peek().equals("#")){
//                operate.pop();
//            }
//            operand.push(calc(operand.pop(), operate.pop(), operand.pop()));
//        }
        return operand.pop().toString();
    }

    private static Double calc(Double para2, String operate, Double para1) {
        // test
        System.out.println("calc: " + para1 + " " + operate + " " + para2);

        switch (operate) {
            case "+":
                return para1 + para2;
            case "-":
                return para1 - para2;
            case "*":
                return para1 * para2;
            case "/":
                return para1 / para2;
        }
        throw new RuntimeException();
    }

    /**
     * 比较两个操作符的大小
     *
     * @param peek 栈顶的操作符
     * @param s    即将入栈的操作符
     * @return 比较大小
     */
    private int compare(String peek, String s) {
        System.out.println(peek + " " + s);

        if ("+".equals(peek) || "-".equals(peek)) {
            if ("*".equals(s) || "/".equals(s) || "(".equals(s)) {
                return -1;
            }
            return 1;
        } else if ("*".equals(peek) || "/".equals(peek)) {
            if ("(".equals(s)) {
                return -1;
            }
            return 1;
        } else if ("(".equals(peek)) {
            if (")".equals(s)) {
                return 0;
            }
            return -1;
        } else if (")".equals(peek)) {
            return 1;
        } else {
            if ("#".equals(s)) return 0;
            return -1;
        }
    }


    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("请输入算数表达式: \n 仅支持 + - * / 与 ( ) \n");
//        String expression = sc.nextLine();
//        System.out.println(new ExpressionValue().calcExp(expression));

//        String s = "2*(3+1)";
        String s = "12+6/3-1";
//        String s = "1*(12+6/3-1)";
//        String s = "1+2*3+43*(12+6/3-1)";
        String[] arr = s.split("((?<=\\+|-|\\*|/|\\(|\\)|#)|(?=\\+|-|\\*|/|\\(|\\)|#))");
        System.out.println(java.util.Arrays.toString(arr));
        System.out.println(new ExpressionValue().calcExp(s));

    }


}
