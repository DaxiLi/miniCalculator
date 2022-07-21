package com.calculator.cal;


/**写出来用于解析并计算的类
 * 这是所有计算类的父类，写一些公共方法
 */
public class Calculator {
    protected Double  num;    //左操作数
    protected String  exp;    //表达式
    //protected Boolean com;    //是否完成计算 false 出错或其他事件
    protected Double  res;    //结果
    protected String  rec;    //用于恢复上一个数据
    static Double   E     = 2.7182818285;
    static Double   PI    = 3.1415926536;





    public Calculator(){
        this("0");
    }

    public Calculator(String newExp){
        this("start",newExp);
    }
    public Calculator(String newMethod, String newExp){
        if (newExp.length() == 0){
            exp = "0";
        }else {
            exp   = newExp.replace(" ","");
        }
        getMethod(newMethod);
        //this.com   = false;
        this.res   = 0.0;
        this.rec   = "";
    }





    protected String getNextMethod() throws Exception{
        int len = exp.length();
        if (0 == len || 1 ==len){
            throw new Exception("表达式结束");
        }else if (3 == len){

        }
        return "test";
    }



    protected Calculator getMethod(String method) {
        switch (method){
            case "+":
                return new Addtion(exp);
            case "-":
                return new Subtion(exp);
            case "*":
                return new Mutipli(exp);
            case "÷":
            case "/":
                return new Dividion(exp);
            case "sin":
                return new Sin(exp);
            case "cos":
                return new Cos(exp);
            case "tan":
                return new Tan(exp);
            case "ln":
                return new Ln(exp);
            case "lg":
                return new Lg(exp);
            case "√":
                return new Square(exp);
            case "(":
                return new Brackets(exp);
            case "!":
                return new Factoaril(exp);
            case "^":
                return new Power(exp);
            default:
                throw new IllegalStateException("Unexpected value: " + method);
        }
    }
    public void setExp(String s){
        this.exp = new String(s);
    }

    /**
     * 用于将 geNextNum() 获取到的最后一个数放回到表达式
     */
    protected void unget(){
        this.exp = this.rec + exp;
    }

    /**
     * 取得下一串数字
     * @return
     */
    protected Double getNexNum() throws Exception{
        int len  = exp.length();
        if (len == 0){
            throw new Exception("表达式结束");
        }

        if (exp.charAt(0) == 'e'){
            return E;
        }else if (exp.charAt(0) == 'π'){
            return PI;
        } else{
            Double retVal;
            int left = 0;
            char ch = exp.charAt(left);
            String readNum = "";
            while (isNum(ch + "") || '.' == ch){
                readNum = readNum + ch;
                if (left == (len - 1) ){
                    break;
                }
                left++;
                ch = exp.charAt(left);
            }
            try{
                retVal = new Double(readNum);
                this.rec = readNum;                             //转换成功以后，把记录放到rec 里面，并从exp里面删除
                exp = exp.replaceFirst(readNum,"");
            }catch (Exception e){
                throw new Exception(e.getMessage() + "值：" + readNum);
            }
            return retVal;//返回数值
        }
        //return  0.0;
    }

    /**
     * 取得下一个运算符
     * @return
     */
    protected String getFunc() throws Exception{
        return "null";
    }

    /**
     * 判断输入字符串是否全数字
     * @param c
     * @return
     */
    private boolean isNum(String  s){
        String chk = "1234567890";
        for (char ch:s.toCharArray()){
            if (chk.indexOf(ch) != -1) {
                return false;
            }
        }//全部循环完毕，竟然没有遇到不能匹配的，我给你出彩
        return true;
    }
    /**
     * 从这里开始解析表达式
     * @throws Exception
     */
    public void start() throws Exception{
        int len = exp.length() - 1;
        if (len == 1){
            if (isNum(exp.charAt(0) + "")){
                try{
                    res = new Double(exp);
                }catch (Exception e){
                    throw e;
                }
            }
        }
        char ch = exp.charAt(0);
        if (isNum(ch + "")){
            try{
                Double num = getNexNum();
            }catch (Exception e){
                throw e;
            }

        }

    }

    /**
     * 外部访问此方法 获取结果
     * @return
     */
    public Double getRes() throws Exception{
        try{
            start();
        }catch (Exception e){
            throw e;
        }
        return res;
        //return 16852.00000000;
    }




    protected  String numSqrt(String s) throws Exception{
        if (s.indexOf('.') > 10 || s.indexOf('-') != -1){              //数值过大或含有负号
            throw new Exception("输入错误，数值过大或尝试进行负数开方");
        }
        Double num;                                       //将字符串转换整型
        try {
            num = new Double(s);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception("格式错误");
        }
        return Math.sqrt(num) + "";
    }


    protected String numFactorial(String s) throws Exception{ //阶乘方法
        if (s.length() > 10 || s.indexOf('.') != -1){      //过长或含有小数点
            throw new Exception("计算数值过大");
        }
        long num;                                       //将字符串转换整型
        try {
            num = Integer.parseInt(s);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception("格式错误");
        }
        num = Integer.parseInt(s);
        if(num == 1 || num == 0){                       //计算阶乘 返回结果
            return "1";                                 //但是，写完发现好像原来有这个方法。。。。。
        }else{
            long temp;
            try{
                temp = Integer.parseInt(numFactorial(num - 1 + ""));
            }catch (Exception e){
                throw e;
            }
            return (num * temp) + "";
        }
    }



    public static void main(String[] args){
        Calculator cal = new Calculator();

    }


}