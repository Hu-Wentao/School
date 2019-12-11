package report5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * Created by HuWT on 2018/12/23
 */
public class Sort extends JFrame {
    private JList list;
    private JTextArea textArea;
    private static final String defaultTips = "请在此处填写要排序的数字, 并以 \",\" 分隔";
    public static void main(String[] args) {
        Sort s = new Sort();
        s.lunch();
    }

    private void lunch(){
        this.setTitle("输入数字并展示排序结果");
        this.setBounds(300, 300, 400, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getLeftComp(), getRightComp());
        sp.setDividerLocation(140);
        this.setContentPane(sp);
        this.setVisible(true);
    }


    private JComponent getLeftComp(){
        JPanel panel = new JPanel(new GridLayout(1,1,0,0));
        list = new JList();
        panel.add(list);

        return panel;
    }
    private JComponent getRightComp(){
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, getRightComp("Top"), getRightComp("Bottom"));
        splitPane.setDividerLocation(200);


        return splitPane;
    }
    private JComponent getRightComp(String RightAddress){
        JPanel panel = new JPanel();
        if("Top".equals(RightAddress)){
            panel.setLayout(new GridLayout(1,1, 0, 20));
            textArea = new JTextArea(defaultTips);
            textArea.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(defaultTips.equals(textArea.getText())){
                        textArea.setText("");
                    }
                }
            });
            textArea.setBackground(Color.WHITE);
            textArea.setText(defaultTips);
            panel.add(textArea);
        }else if("Bottom".equals(RightAddress)){
            panel.setLayout(new GridLayout(1,2, 10, 0));
            JButton reset = new JButton("重新输入");
            reset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textArea.setText(defaultTips);
                    showResult(defaultTips);
                }
            });
            JButton show = new JButton("显示结果");
            show.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showResult(textArea.getText());
                }
            });
            panel.add(reset);
            panel.add(show);
        }else {
            System.err.println("错误! getRightCom() 获取了未知参数!");
            return null;
        }
        return panel;
    }

    private void showResult(String s){
        if(s.equals(defaultTips)){
            list.setListData(new String[]{});
        }else {
            String[] sArr = s.split(",");
            Double[] arr = new Double[sArr.length];
            for (int i = 0; i < sArr.length; i++) {
                double temp = Double.parseDouble(sArr[i].trim());
                arr[i] = Double.parseDouble(sArr[i]);
            }
            Arrays.sort(arr);

            String[] result = new String[sArr.length];
            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i].toString();
                if(".0".equals( result[i].substring(result[0].length()-2, result[0].length())) ){
                    result[0] = result[0].substring(0, result[0].length()-2);
                }
            }
            list.setListData(result);
        }
    }

}
