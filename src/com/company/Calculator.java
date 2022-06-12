package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    //此处注释详细见记事本
    public static final int SCREEN_W = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_H = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static final int  W = 400;
    public static final int  H = 500;

    public int x = (SCREEN_W-W)/2;
    public int y = (SCREEN_H-H)/2;

    /*
    上方控件
     */
    private final JPanel jPanel_n = new JPanel();
    private final JTextField jTextField = new JTextField();
    private final JButton jButton = new JButton("清除");

    /*
    中间控件
     */
    private final JPanel jPanel_c = new JPanel();

    /*
    构造方法调用下方的三个方法，构造计算器布局
     */
    public Calculator() throws HeadlessException {
        this.init();
        this.addNorthPanel();
        this.addCenterBottom();
    }

    /*
    基础属性
    */
    public void init(){
        this.setTitle("计算器");
        this.setSize(W,H);
        this.setLocation(x,y);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
    上方控件
     */
    public void addNorthPanel(){
        //设置大小
        this.jTextField.setPreferredSize(new Dimension(250,30));
        jPanel_n.add(jTextField);//显示框
        jPanel_n.add(jButton);//清除按钮
        //匿名类 重写接口方法，点击清除按钮就重置文本框
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextField.setText("");
            }
        });
        //位置
        this.add(jPanel_n,BorderLayout.NORTH);
    }

    /*
    中间控件
     */
    public void addCenterBottom(){
        //这是按钮的值
        String str = "123+456-789*0.=/";
        //生成4*4的按钮
        this.jPanel_c.setLayout(new GridLayout(4,4));
        //用循环把按钮的值赋上去
        for(int i = 0;i<16;i++){
            String text = String.valueOf(str.charAt(i));
            JButton button = new JButton();
            button.setText(text);
            //添加监视器，用来管理按钮操作
            button.addActionListener(this);
            jPanel_c.add(button);
        }
        this.add(jPanel_c,BorderLayout.CENTER);
    }

    //第一次输入的数
    String input1 = null;
    //存运算符
    String c = null;
    //重写接口方法
    @Override
    public void actionPerformed(ActionEvent e) {
        //click用来存储点击的按钮
        String click = e.getActionCommand();
        //如果点击的是数字
        if(".0123456789".contains(click)) {
            //字符串拼接，后面再转为数字
            this.jTextField.setText(jTextField.getText()+click);
            //如果是运算符，就让c = 运算符
        }else if(click.matches("[+\\-*/]{1}")){
            c = click;
            input1 = this.jTextField.getText();
            this.jTextField.setText("");
            //如果是=，就运算
        }else if(click.equals("=")){
            //第一次输入的数
            Double a = Double.valueOf(input1);
            //第二次输入的数
            Double b = Double.valueOf(this.jTextField.getText());
            double result = 0.0;
            switch (c){
                case "+":
                    result = a+b;
                    break;
                case "-":
                    result = a-b;
                    break;
                case "*":
                    result = a*b;
                    break;
                case "/":
                    //异常处理，除数为0
                    try {
                        result = a/b;
                    }catch (java.lang.ArithmeticException e1){
                        System.out.println(e1.getMessage());
                    }
                    break;
            }
            //显示结果
            this.jTextField.setText(Double.toString(result));
        }
    }
    //main方法
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }
}