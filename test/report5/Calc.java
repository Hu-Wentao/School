package test.report5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

/**
 * 单目运算符未处理
 */
public class Calc extends JFrame {
    JLabel labelA;         // 已输入的算数式
    JLabel labelB;   // 当前输入的文字, 或运算结果

    String show = "";  // 公式
    String paraA = "0";  // 第一个操作数
    String op = "";     // 双目运算符

    String paraB = "0";   // 显示在下方label的内容   // 规定pB的初值为 "0"
    boolean isPBReset = true;                      // 用于判断pB是否为初值 (相当于 "".equals(paraB) )

    String paraBCache = "0";
    String paraACache = "0";


    public static void main(String[] args) {
        Calc c = new Calc();
        c.lunch();
        c.initPanel();

    }

    void lunch() {
        this.setTitle("Calculator");
        this.setBounds(300, 300, 420, 630);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void initPanel() {
        JPanel topPanel = initTopPanel();
        JPanel bottomPanel = initBottomPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, topPanel, bottomPanel);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerSize(7);

        this.setContentPane(splitPane);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        splitPane.setDividerLocation(0.3);  // 必须在splitPane已显示(已有宽高)后才能使该方法生效
    }

    JPanel initTopPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        JPanel panel = new JPanel(gridBagLayout);

        labelA = new JLabel();
        labelA.setSize(20, 400);
        labelA.setHorizontalAlignment(SwingConstants.RIGHT);
        labelA.setOpaque(true);
        labelA.setBackground(Color.LIGHT_GRAY);
        labelA.setFont(new Font("微软雅黑", Font.PLAIN, 25));

        labelB = new JLabel("0");
        labelB.setHorizontalAlignment(SwingConstants.RIGHT);
        labelB.setOpaque(true);
        labelB.setBackground(Color.WHITE);
        labelB.setFont(new Font("微软雅黑", Font.BOLD, 40));

        gridBagLayout.setConstraints(labelA, new GridBagConstraints(
                0, 0,
                4, 1,
                1.0, 1.0,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        gridBagLayout.setConstraints(labelB, new GridBagConstraints(
                0, 1,
                4, 2,
                1.0, 1.0,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        panel.add(labelA);
        panel.add(labelB);
        return panel;
    }

    JPanel initBottomPanel() {
        // 指定 行数 和 列数 的网格布局, 并指定 水平 和 竖直 网格间隙
        JPanel panel = new JPanel(new GridLayout(6, 4, 2, 2));

        ButtonListener buttonListener = new ButtonListener();
        final String[] btnName = ("√, x², 1/x, %, CE, C, ←, ÷, 7, 8, 9, ×, 4, 5, 6, -, 1, 2, 3, ＋, ±, 0, ., ＝").split(", ");
        for (int i = 0; i < btnName.length; i++) {
            JButton button = new JButton(btnName[i]);
            button.setBackground(Color.WHITE);
            button.setFont(new Font("微软雅黑", Font.PLAIN, 20));
            button.addActionListener(buttonListener);
            panel.add(button);
        }
        return panel;
    }

    /**
     * 按钮点击事件
     */
    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String btnTitle = ((e.paramString().split(",")[1]).substring(4));

            if (judge(btnTitle, "√", "x²", "1/x", "±")) {                      //---------------------------------- 单目运算符
/*                op = btnTitle;
//                paraACache = paraA;
                switch (btnTitle) {
                    case "√":
                        paraACache = "√(" + paraACache + ")";
                        break;
                    case "1/x":
                        paraACache = "1/(" + paraACache + ")";
                        break;
                    case "x²":
                        paraACache = "sqr(" + paraACache + ")";
                        break;
                    case "±":
                        paraACache = ((paraACache.startsWith("-")) ? "" : "-") + paraACache;
                        break;
                    default:
                        showParamInfo("出现了未处理的单目运算符");
                }
                show = show + paraACache;*/

            } else if (judge(btnTitle, "＋", "-", "×", "÷", "%")) {             //---------------------------------- 双目运算符
                op = btnTitle;
                if ("".equals(op)) {    // 如果没有操作符, 则当前操作 _pA
                    paraB = paraA;
                    show = show + paraA + op;
                } else {                // 有               当前操作pB
                    if (isPBReset) {   // 如果pB 为初值
                        paraB = paraA;
                        isPBReset = false;
                        show = show + paraB + op;
                    } else {
                        show = show + paraB + op;
                        paraB = getAnswer(paraA, op, paraB);
                    }
                }
            } else if (judge(btnTitle, "CE", "C", "←", "＝")) {                  //---------------------------------- 操作符
                switch (btnTitle) {
                    case "←":   // 删除当前操作的 para ( _pA 或者 _pB , 通过 op判断)
                        if ("".equals(op) && (!"0".equals(paraA) || !isPBReset)) {    // 如果没有操作符, && (_pA || _pB 没有复位)
                            if (paraA.length() == 1) {
                                resetValue(ValueToken._pA);            // pA复位
                            } else {
                                paraA = paraA.substring(0, paraA.length() - 1);
                            }
                        } else {
                            if (paraB.length() == 1) {
                                resetValue(ValueToken._pB);
                            } else {
                                paraB = paraB.substring(0, paraB.length() - 1);
                            }
                        }
                        break;
                    case "C":                       // 全清
                        resetValue(ValueToken._ALL);
                        break;
                    case "CE":  // 删除正在操作的操作数(通过op)
                        if ("".equals(op)) {  // 是, 则表明当前操作数为 _pA
                            resetValue(ValueToken._pA);    // pA复位
                        } else {
                            resetValue(ValueToken._pB);   // 当pB复位时, pBCache的值就无意义了, 故此无需再复位pBCache
                        }
                        break;
                    case "＝":   // 1. 没有op(只有pA), 2.有双目op, 3有单目op
                        resetValue(ValueToken._show);   // 按下 = . show复位
                        if (!"".equals(op)) {       // 1
                            paraA = getAnswer(paraA, op, paraB);
                            resetValue(ValueToken._pB, ValueToken._pBCache);     // 复位pB, 以后续操作
                            showParamInfo("输入了 = , 且op复位");
                        }   // todo 还有其他情况
                        break;
                }
            } else {                                                                 //---------------------------------- 数字
                //如果 op为"" , 则将数保存到pA, 否则保存到pB
                if ("".equals(op)) {
                    if ("0".equals(paraA)) {
                        paraA = btnTitle;
                    } else {
                        paraA += btnTitle;
                    }
                } else {
                    if (isPBReset || (paraA.equals(paraB) && !paraBCache.equals(paraB))) {  // 此处要保证条件二只执行一次 //(如果pB复位 || pA等于pB
                        paraB = btnTitle;
                        isPBReset = false;
                    } else {
                        paraB += btnTitle;
                    }
                }
                paraBCache = paraB; // 确保本句位于 数字段 最后一行;
            }

            labelA.setText(show);
            if ("".equals(op)) {      // 如果没有操作符, 则显示pA
                labelB.setText(paraA);
            } else {
                labelB.setText(paraB);
            }
        }
    }

    /**
     * 判断 sym 是否属于 arr数组
     *
     * @param sym btnTitle
     * @param arr 要匹配的符号
     * @return 是否属于
     */
    boolean judge(String sym, String... arr) {
        for (String s : arr)
            if (s.equals(sym))
                return true;
        return false;
    }


    String getAmser(String paraA, String op) {
        double a = Double.parseDouble(paraA);
        switch (op) {
            case "√":
                break;
            case "x²":
                break;
            case "1/x":
                break;
            case "±":
                break;
            default:

        }
        return "";  // todo
    }
    /**
     * 双目运算符,
     * @param paraA 操作数1
     * @param op    双目操作符
     * @param paraB 操作数2
     * @return 运算结果
     */
    String getAnswer(String paraA, String op, String paraB) {
        double a = Double.parseDouble(paraA);
        double b = Double.parseDouble(paraB);
        double ans = 0;
        switch (op) {
            case "＋": {
                ans = a + b;
                break;
            }
            case "-":
                ans = a - b;
                break;
            case "×":
                ans = a * b;
                break;
            case "÷":
                ans = a / b;
                break;
            case "%":   // 取余
                ans = a % b;
                break;
            default:
                showParamInfo("getAnswer(),没有匹配到合适的op");
                try {
                    throw new NoSuchAlgorithmException();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
        }       // 本行用于计算结果
        resetValue(ValueToken._op);
        return new DecimalFormat("###################.###########").format(ans);
    }


    enum ValueToken {
        _ALL, _excPA, _excPB,
        _pA, _pB, _pBCache, _show, _op, _pACache
    }

    /**
     * 用于复位重要参数
     *
     * @param token 口令数组, 可传入单个, 多个参数
     */
    void resetValue(ValueToken... token) {
        if (token.length == 1)   // 快捷口令
            switch (token[0]) {
                case _ALL:
                    token = new ValueToken[]{ValueToken._pA, ValueToken._pB, ValueToken._pBCache, ValueToken._show, ValueToken._op, ValueToken._pACache};
                    break;
                case _excPA:
                    token = new ValueToken[]{ValueToken._pB, ValueToken._pBCache, ValueToken._show, ValueToken._op, ValueToken._pACache};
                    break;
                case _excPB:
                    token = new ValueToken[]{ValueToken._pA, ValueToken._pBCache, ValueToken._show, ValueToken._op, ValueToken._pACache};
                    break;
            }
        for (ValueToken r : token) {
            switch (r) {
                case _pA:
                    paraA = "0";    // 复位 _pA
                    break;
                case _pB:
                    paraB = "0";
                    isPBReset = true;
                    break;
                case _pBCache:
                    paraBCache = "";
                    break;
                case _show:
                    show = "";
                    break;
                case _op:
                    op = "";
                    break;
                case _pACache:
                    paraACache = "0";
                    break;
                default:
                    showParamInfo("resetValue()参数错误! case的值为:" + r.toString());
            }
        }
    }

    /**
     * 用于调试
     */
    void showParamInfo(String errMsg) {
        System.err.println(errMsg);
        System.out.println("当前op为" + op);
        System.out.println("当前pA为" + paraA);
        System.out.println("当前pB为" + paraB);
        System.out.println("=======================================================\n");
    }
}
