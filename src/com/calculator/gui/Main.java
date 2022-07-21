package com.calculator.gui;


import com.calculator.cal.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    JFrame          frame;
    JPanel          panel;
    JButton[][]     buttons;
    JTextField      resultShow;
    JTextField      resultShow2;
    String          inputString;
    buttonAction    Action;
    int             row;                    //偏移量
    int             line;
    boolean         calFlag;                //是否科学计算器，按钮布局和功能差异

    
    
    public Main(){
        this(false);
    }

    /**
     * 构造方法 新建 frame panel等实例，其他啥也不做
     * 其他东西全在lunch里面了
     * @param isScin
     */
    public Main(boolean isScin)
    {
       frame                = new JFrame("小小计算器");     //新建frame 设置标题
       panel                = new JPanel();                    //创建面板
       Action               = new buttonAction();              //创建监听实例
       inputString          = new String("0");          //初始显示0
       resultShow           = new JTextField();
       resultShow2          = new JTextField();
       calFlag = isScin;                                        //是否科学计算器
                           //居中显示
        

    }

    /**
     * 用于在普通界面 和plus 之间切换
     * 就重新构建panel视图
     */
    public void lunchChange(){
        frame.setVisible(false);
        frame = new JFrame("小小计算器");
        panel = new JPanel();
        calFlag = !calFlag;
        frame.setVisible(true);
        panel.setVisible(true);
        lunch();
    }

    /**
     * 负责初始化按钮
     * 文字框，设定属性，并显示出来
     */
    public void lunch(){
        frame.setSize(400,700);                     //设置frame大小
        frame.setLayout(new GridBagLayout());                    //使用 表格包布局
        frame.setResizable(false);                               //frame大小不可改变
        frame.setLocationRelativeTo(null);
        if(calFlag){                                             //设置是否科学计算器
            row  = 2;
            line = 1;
        }else{
            row  = 0;
            line = 0;
        }
        buttons = new JButton[6 + row][5 + line];
        for (int i = 1; i <= 3;i++) {
            for (int j = 1;j <= 3;j++){
                buttons[i + 1 + row][j + line] = new JButton(9 - (3*i) + j + "");
            }
        }

        buttons[1 + row][line + 1] = new JButton("清零");
        buttons[1 + row][line + 2] = new JButton("删除");
        buttons[1 + row][line + 3] = new JButton("÷");
        buttons[1 + row][line + 4] = new JButton("*");
        buttons[2 + row][line + 4] = new JButton("-");
        buttons[3 + row][line + 4] = new JButton("+");
        buttons[4 + row][line + 4] = new JButton("=");
        buttons[5 + row][line + 1] = new JButton("切换");
        buttons[5 + row][line + 2] = new JButton("0");
        buttons[5 + row][line + 3] = new JButton(".");
        buttons[5 + row][line + 4] = new JButton("none");

        if(calFlag){                                                     //为科学计算器设置按钮


            buttons[5 + row][line + 1].setText("%");

            buttons[1][1] = new JButton("切换");
            buttons[1][2] = new JButton("2nd");
            buttons[1][3] = new JButton("sin(");
            buttons[1][4] = new JButton("cos(");
            buttons[1][5] = new JButton("tan(");

            buttons[2][1] = new JButton("^");
            buttons[2][2] = new JButton("lg(");
            buttons[2][3] = new JButton("ln(");
            buttons[2][4] = new JButton("(");
            buttons[2][5] = new JButton(")");

            buttons[3][1] = new JButton("√");
            buttons[4][1] = new JButton("n!");
            buttons[5][1] = new JButton("1/X");
            buttons[6][1] = new JButton("π");
            buttons[7][1] = new JButton("e");
        }

        //设置所有按钮的属性
        for (int i = 1; i <= 5 + row;i++) {
            for (int j = 1;j <= 4 + line;j++){
                try{
                    buttons[i][j].setBackground(new Color(250, 250, 250,100));
                    buttons[i][j].setFont(new Font("Arabic", Font.PLAIN, 20));
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black,0,true));
                    buttons[i][j].addActionListener(Action);
                }catch (NullPointerException e) {
                    System.out.println("NO." + i + row + "row " + j + line + "line NULLPOINTER" );
                }
            }
        }


        resultShow.setHorizontalAlignment(SwingConstants.RIGHT);//文字右对齐
        resultShow.setColumns(18);                              //设置文本框的列数是18
        //resultShow.setBackground(Color.black);
        resultShow.setEditable(false);                          //不可编辑
        //resultShow.setText("0");
        //resultShow.setSize(400,100);
        resultShow.setFont(new Font("null",Font.BOLD,40));
        resultShow.setBorder(null);

        GridBagConstraints contains = new GridBagConstraints();
        contains.weightx = 0.5;                                 //多余部分占用比例 ？？？？？
        contains.weighty = 1;
        contains.gridx = 1;
        contains.gridy = 1;
        contains.gridwidth = 4 + row;
        contains.gridheight = 1;
        contains.fill = GridBagConstraints.HORIZONTAL;

        this.frame.add(resultShow,contains);
        contains.gridx = 1;
        contains.gridy = 2;

        resultShow2.setHorizontalAlignment(SwingConstants.RIGHT);//文字右对齐
        resultShow2.setColumns(18);                              //设置文本框的列数是18
        resultShow2.setText(inputString);
        resultShow2.setEditable(false);                          //不可编辑
        resultShow2.setSize(400,80);
        resultShow2.setFont(new Font("null",Font.BOLD,40));
        resultShow2.setBorder(null);

        this.frame.add(resultShow2,contains);


        contains.fill = GridBagConstraints.BOTH;
        contains.gridheight = 1;                                 //占用行列
        contains.gridwidth = 1;
        for (int i = 1; i <= 5 + row;i++) {
            for (int j = 1;j <= 4 + line;j++){
                if (i == 4 + row && j == 4 + line){
                    contains.gridheight = 2;                     //占用行列
                    contains.gridwidth = 1;
                    contains.gridx = j;                          // 行和列
                    contains.gridy = i + 2;
                    this.frame.add(buttons[i][j],contains);
                    contains.gridheight = 1;
                }else if (i != 5 + row || j != 4 + line ){
                    contains.gridx = j;                           // 行和列
                    contains.gridy = i + 2;
                    this.frame.add(buttons[i][j],contains);
                }
                String str = buttons[i][j].getText();
                System.out.println(str);
            }
        }
        //全部好了，让他显示出来
        this.frame.setVisible(true);
    }

    public void freshResult(String s){
        this.resultShow.setText(s.replace(" ",""));
    }
    public void freshResultShow2(){
        System.out.println("当前内容：" + inputString);
        if (inputString.length() != 0){
            this.resultShow2.setText(this.inputString.replace(" ",""));
        }else {
            this.resultShow2.setText("0");
        }

    }


    /**
     * 处理案件事件的类
     */
    class buttonAction implements ActionListener{                   //没写完，正在想方案
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            String cmd = e.getActionCommand();
            if(cmd.equals("删除")){
                inputString = numDelete(inputString);                           //本来直接获取对应按钮文本，但是代码太长了，就直接输字算了
                freshResultShow2();                                             //另外，换了比较方法 替换 ==
                return;
            }else if (isMathFunc(cmd)){                                     //几个特殊数学函数
                addInput(cmd);                                                  //例如 sin cos啥的
                return;
            }else if (cmd.equals("=")){                                          //开始计算吧，，没写计算方法，比输入方法简单不到哪里去
                cmdAnser();                                                     //输入方法写的我好累。。
                return;                                                         //一堆输入规则
            }else if (cmd.equals("清零")){                                        //就是清零呗
                inputString = "0";
                freshResultShow2();
                freshResult("");
                return;
            }else if (cmd.equals("切换")){                                        //进行界面切换，将清除所有已输入
                inputString = "0";
                freshResultShow2();
                freshResult("");
                lunchChange();
                return;
            }
            addInput(e.getActionCommand());                                 //其他的全交给输入了，一大波输入规则即将来袭
                                                                                //我忘写注释了。。。。好难受
            /*if(isFunc(e.getActionCommand())){
                //addNum(e.getActionCommand());
            }*/
        }

        /**
         * 判断输入字符串是否全数字
         * @param c
         * @return
         */
        private boolean isNum(String  c){
            for (int j = 0;j < c.length() ;j++){
                for (int i = 0;i < 10;i++){
                    if ((i + "").equals(c.charAt(j) + "")){ //如果是 10 个数当中一个 开始下一个字符
                        break;
                    }
                    if(i == 9){                             //都到9了，还没有匹配到数字
                        return false;
                    }
                }
            }
            //全部循环完毕，竟然没有遇到不能匹配的，我给你出彩
            return true;
        }


        /**
         * 开始计算方法，可能抛出错误
         */
        private void cmdAnser(){
            Calculator cal = new Calculator("start",inputString);
            Double r = 0.000000;
            try{
                r = cal.getRes();
            }catch (Exception e1){
                inputString = e1.getMessage();
                freshResultShow2();
                return;
            }
            freshResult(inputString);
            inputString = r.toString();
            freshResultShow2();
        }

        /**
         *
         * @param s
         * @return boolean
         */
        private boolean isMathFunc(String s){
            if (calFlag == false){
                return false;
            }
            return ("sin(".equals(s) ||
                    "cos(".equals(s) ||
                    "tan(".equals(s) ||
                    "ln(".equals(s)  ||
                    "lg(".equals(s)   ||
                    "^".equals(s)    ||
                    "√".equals(s)    ||
                    "n!".equals(s)   ||
                    "1/X".equals(s)
                    );
        }

        /**
         * 十分难受的输入方法，将输入以一定规则放到输入数组里面
         * 规则写的很难受，也就不考虑什么效率和漂亮了
         * 修修补补好些时间
          * @param s
         */
        private void addInput(String s) {
            /**
             *如果长度为0 或者只有一个0
             * 那就是没有东西咯
             * 直接弄上去
             **/
            int len = inputString.length();
            if (len == 0 || (len == 1 && inputString.charAt(0) == '0')) {
                if (".".equals(s)) {
                    inputString = " 0 .";
                } else if (s.equals("^") || "1/X".equals(s) || s.equals('%') || s.equals(")")) {               //+-*/
                    inputString = "0";
                } else if (isFunc(s)) {               //+-*/
                    inputString = "0 " + s;
                } else if (s.equals("x") || s.equals("/") || s.equals("÷")) {
                    inputString = "0 " + s;
                } else {
                    if (s.equals("n!")) {
                        inputString = "!";
                    } else {
                        inputString = s;
                    }
                }
                freshResultShow2();
                return;
            }

            /*
            * 获取已输入的最后一个字符来判断下一个输入是否合法
            * */
            char c = inputString.charAt(inputString.length() - 1);

            /*如果已经输入乘除 阶乘 百分号
            * 那么
            * 1. 后面直接输入 点 需要自动添加 0
            * 2. 仍然输入乘除 则认为纠正刚才输入，替换上一个符号
            * 3. 输入加减 可认为正负数，合法，添加
            * 4. 其他输入就没什么特别，直接加上 （n！和分数需要稍微变形，留在else里面 这被当作普通输入，将不再提起)
            * */
            if (c == '*' || c == '÷' || '!' == c || '%' == c) {
                if (s.equals(".")) {
                    inputString = inputString + " " + "0 .";
                } else if ( s.equals("-")) {
                    inputString = inputString + " " + s;
                } else if (s.equals("*") || s.equals("÷")) {
                    inputString = numDelete(inputString);
                    inputString = inputString + " " + s;
                } else if (s.equals("+") || s.equals(")") || s.equals("^") || s.equals("1/X") || s.equals(")") || s.equals("%") ) {
                    ;
                } else {
                    if (s.equals("n!")) {
                        inputString = inputString + " " + "!";
                    } else {
                        inputString = inputString + " " + s;
                    }
                }
                freshResultShow2();
                return;
                /*
                * 输入为 pi 和 e 那么后面
                * 1. 小数点自动加0
                * 2. 没了
                * */
            } else if (c == 'π' || c == 'e') {
                if (s.equals(".")) {
                    inputString = inputString + " " + "0 .";
                } else if (s.equals("^") || s.equals("1/X")) {
                    if (s.equals("^")) {
                        inputString = inputString + " " + s;
                    } else {
                        inputString = inputString + " " + "^(-1)";
                    }
                } else {
                    if (s.equals("n!")) {
                        inputString = inputString + " " + "!";
                    } else {
                        inputString = inputString + " " + s;
                    }
                }
                freshResultShow2();
                /* "+" "-"
                * 1. 输入 + - 替换
                * 2. 输入 乘除 检测前面是不是已经有乘除，有就将 加减 一起替换
                * 其实我觉得可以让他直接不输入，还能简单点
                * */
            } else if (c == '-' || c == '+') {
                if (s.equals(".")) {
                    inputString = inputString + " " + "0 .";
                } else if (s.equals("*") || s.equals("÷")) {        //将上一个+ - 替换
                    String str = inputString;                       //
//                    if (str.length() < 3){                        //这个语句 好像没什么用，因为他永远不会小于3 但是如果某些意外导致0没了可能会有用
//                        inputString = "0 " + s;
//                        freshResultShow2();
//                        return;
//                    }
                    System.out.println("length:" + str.length());
                    char strch = str.charAt(str.length() - 3);
                    if ( strch == '(' || strch == '!' || strch == '^' || strch == '%'){           //当                                //但是这几个情况例外（某些后面能+-，但不能*/的符号
                        if (s.equals("*") || s.equals("/") || s.equals("÷") ){
                            return;
                        }
                    }
                    char ch = '0';
                    do {                                                    //将上一个几 + - 直接替换
                        inputString = numDelete(inputString);
                        ch = inputString.charAt(inputString.length() - 1);
                    } while (ch == '*' || ch == '÷');
                    inputString = inputString + " " + s;
                    freshResultShow2();
                    /*
                    * 后面的注释不想写了，好多呀，目前运行良好就算了
                    * 注释终结
                    * */
                } else if (s.equals("+") || s.equals("-")) {
                    inputString = numDelete(inputString);
                    inputString = inputString + " " + s;
                    freshResultShow2();
                } else if (s.equals("^") || s.equals("1/X") || s.equals("%") || s.equals(")") || s.equals("(")) {
                    ;
                } else {
                    if (s.equals("n!")) {
                        inputString = inputString + " " + "!";
                    } else {
                        inputString = inputString + " " + s;
                    }
                }
                freshResultShow2();
                return;
            } else if (".".equals(c + "")) {
                if (!isNum(s)) {
                    if ((".").equals(s)) {
                        System.out.println("前面已经有一个点了");
                    } else if (inputString.length() > 2 && inputString.charAt(inputString.length() - 2) == '0') {
                        System.out.println("前面是0. 但是你输入不是数字，所以删了啊");
                        inputString = numDelete(inputString);
                        freshResultShow2();
                        inputString = inputString + "0" + " " + s;
                    } else if (s.equals("^") || s.equals("1/X")) {
                        inputString = numDelete(inputString);
                        if (s.equals("^")) {
                            inputString = inputString + " " + s;
                        } else {
                            inputString = inputString + " ^(-1)";
                        }
                    } else {
                        System.out.println("你输入不是是数字，所以点删了");
                        inputString = numDelete(inputString);
                        if (s.equals("n!")) {
                            inputString = inputString + " " + "!";
                        } else {
                            inputString = inputString + " " + s;
                        }
                    }
                } else {
                    inputString = inputString + " " + s;
                }
                freshResultShow2();
                return;
            }else if ( c == '^' ||
                    c == '√'    ||
                   // c == '('    ||
                    '%' == c    ||
                    '!' == c){  //这些符号后面不能 直接乘除
                if (c == '(' || c == '√'){
                    if (s.equals("*") || s.equals("/") || s.equals("÷") || "^".equals(s) || s.equals("1/X") || s.equals("%") ){
                        return;
                    }
                }
                if (c != '%' && s.equals("%")){
                    ;
                }
                if (s.equals("*") || s.equals("/") || s.equals("÷")){
                    return;
                }
                if (s.equals("n!")) {
                    inputString = inputString + " " + "!";
                } else {
                    inputString = inputString + " " + s;
                }
                freshResultShow2();
                return;
            } else if (!isNum(c + "")) {              //不是加减乘除 不是点 也不是数字 可能 ：括号，特殊运算符
                if (c == '(' ){
                    if (s.equals("%") || s.equals("^") || s.equals("1/X") || s.equals("*") || s.equals("÷")){
                        ;
                    }else if (s.equals(")") && !has129(inputString)){
                        inputString = numDelete(inputString);
                    }else if (s.equals(".")){
                        inputString = inputString + " 0 .";
                    }else {
                        if (s.equals("n!")) {
                            inputString = inputString + " " + "!";
                        }else if (s.equals("+")){
                            ;
                        }else {
                            inputString = inputString + " " + s;
                        }
                    }
                    freshResultShow2();
                    return;
                }else if (c == ')'){
                    if (isFunc(s) || s.equals("^") || s.equals(")") || s.equals("(") || s.equals("1/X") || s.equals("π") || s.equals("e")){
                        inputString = inputString + " " + s;
                    }
                    /*
                    * 上面这个if 和下面这个功能是一样的，上面只允许输入
                    * 下面这个过滤非法输入
                    *
                    * */
                    if (isNum(s + "") || s.equals("n!") || s.equals("π") || s.equals(".") || isFuncPlus(s)) {
                        ;
                    }
//                    }else {
//                        inputString = inputString + " " + s;
//                    }
                    freshResultShow2();
                    return;
                }
                if (s.equals("^") || s.equals("1/X")) {
                    if (s.equals("^")) {
                        inputString = inputString + " " + s;
                    } else {
                        inputString = inputString + " ^(-1)";
                    }
                } else {
                    if (s.equals("n!")) {
                        inputString = inputString + " " + "!";
                    } else {
                        inputString = inputString + " " + s;
                    }
                }
                freshResultShow2();
                return;
            } else if (isNum("" + c)){               //是数字，总算啥都能输入了，但是考虑小数末尾的0 （应该放到运算符处检测）和数字开头的 0
                /*此处考虑输入为0的情况
                *如果前面没有小数点，且前面的数里面没有1 - 9,那么开头多个0 只输入一个
                * */
                if (c == '0'){
                    if (s.equals(".")){
                        if (hasDot(inputString)){
                            return;
                        }else {
                            inputString = inputString + " .";
                        }
                        freshResultShow2();
                        return;
                    }
                    if (!isNum(s) && hasDot(inputString)){  //输入不是数字 前面有点 末尾是0
                        while (c == '0'){
                            inputString = numDelete(inputString);
                            len = inputString.length() - 1;
                            if (len >= 0){
                                c = inputString.charAt(len);
                            }else {
                                inputString = "0";
                                len = 0;
                                break;
                            }
                        }
                        if (inputString.charAt(len) == '.'){
                            inputString = numDelete(inputString) + " " + s;
                        }else {
                            inputString = inputString + " " + s;
                        }
                        freshResultShow2();
                        return;
                    }
                    if (s.equals("0") && !has129(inputString)) {
                        return;
                    }

                }

                if (s.equals(".") && hasDot(inputString)){
                    ;
                }else if (s.equals("^")){
                    inputString = inputString + " " + s;
                }else if (s.equals("1/X")){
                    inputString = inputString + " ^(-1)" ;
                }else if (s.equals("n!")){
                    return;
                }else if (c == '0' && !has129(inputString)){
                    if (s.equals(".")){
                        inputString = inputString + " " + s;
                    }else {
                        inputString = numDelete(inputString);
                        inputString = inputString + " " + s;
                    }
                }else {
                    inputString = inputString + " " + s;
                }
                freshResultShow2();
                return;
            }

            System.out.println("您似乎忽略了某些条件导致执行了该语句？位置 addinput.818156156");
        }

        /**
         * 判断前面是否有有效数字 1-9 & .
         * 是否可以输入0
         * @param s
         * @return
         */
        private boolean has129(String s){
            if (hasDot(s)){
                return true;
            }else{
                int len = s.length() - 1;
                char ch = s.charAt(len);
                while (true){
                    len -= 1;
                    if (isNum(ch + "") && ch != '0'){
                        System.out.println("true" + len);
                        return true;
                    }else if ( (ch == '0' || ch == ' ') && len >= 0){
                        System.out.println("has129 len 的值：" + len);
                        ch = s.charAt(len);
                        continue;
                    }else {
                        System.out.println("false" + len);
                        return false;
                    }
                }
            }
        }


        private Boolean isFuncPlus(String s){
            return (
                    s.equals("sin(") ||
                    s.equals("cos(") ||
                    s.equals("tan(") ||
                    s.equals("ln(")  ||
                    s.equals("lg(")  ||
                    s.equals("√")    ||
                    s.equals("n!")   ||
                    s.equals("1/X")
                    );
        }
        /**
         * 判断字符串最后一个非数字字符后是否含有点
         * @param s 待判断字符串
         * @return 是否可以插入点
         */
        private boolean hasDot(String s){
            int len = s.length() - 1;
            while (true){
                if (s.charAt(len) == '.'){          //找到点，true
                    return true;
                }else if ( (isNum(s.charAt(len) + "") || s.charAt(len) == ' ' ) && len - 1 > 0) {
                    len--;
                    continue;//是数字或空格，继续
                }else {       //不是数字，不是点，则false
                    return false;
                }
            }
        }

        /**
         * 删除函数，每一次输入以空格分隔
         * 所以实际功能是删除末尾到最后一个空格之间的内容
         * 这个地方我没有什么好办法  简单replace方法
         *
         * @param s要删除最后一次输入的字符串
         * @return 删除后的字符串
         */
        //@org.jetbrains.annotations.NotNull
        private String numDelete(String s){
            int len = s.length();
            if (len == 0 || len == 2 || len == 1){  //长度为 0 1 2 删完就都没有了
                return "0";
            }
            char c = s.charAt(--len);               //获取最后一个字符
            String str = c + "";                    //读取删除字符串
            while (c != ' ' && len-- > 0){           //重复直到遇到空格 或者 到头
                c = s.charAt(len);
                str = c + str;
                System.out.println("将删除：" + str + " i:" + len);
            }
            s = s + "aja";
            return s.replace(str + "aja","");
            //myJavaSwing.this.freshResultShow2();
        }

        /**
         * 判断是否数学方法，不过这个方法好像没有用
         * 先不删他
         * @param s
         * @return
         */
        private boolean isFunc(String s){
            return ("+".equals(s) ||
                    "-".equals(s) ||
                    "*".equals(s) ||
                    "÷".equals(s) ||
                    "%".equals(s) ||
                    "/".equals(s));
        }
    }

    /**
     * 本来写出来设置监听的，结果在lunch里面顺便就吧这事做了
     */
    public void setActionListener(){

    }
    public static  void main(String[] args) {
        System.out.println("hello world");
        com.calculator.gui.Main cal = new com.calculator.gui.Main(true);
        cal.lunch();
        //设置

//        Calculator cal = new Calculator("1+1");
//        try{
//            cal.start();
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
    }

    // todo list
    // 输入数字前面几个 0 删除多余，
    //小数后面多余 0 删除多余

}
