package com.lixl.study.DataStructureAndAlgorithm.stack;

import java.util.Stack;

/**
 * @author : lixl
 * @date : 2020-10-21 10:56:30
 * 34+13*9+44-12/3
 **/
public class MathC {
    public static void main(String[] args) {

        String test = "{[}()]";
        char[] chars = test.toCharArray();
        boolean b = checkSign(chars);
        if (b){
            System.out.println("合法");
        }else{
            System.out.println("非法");
        }

    }

    public static boolean checkSign(char[] sign) {
        if (sign.length <= 1) {
            return false;
        }
        Stack signStack = new Stack();
        for (char element : sign) {
            System.out.println("当前符号：" + element);
            boolean left = element == '[' || element == '{' || element == '(';
            boolean right = element == ']' || element == '}' || element == ')';
            if (left) {
                signStack.push(element);
            }
            if (right) {
                if (signStack.size()==0){
                    return false;
                }
                char pop = (char) signStack.pop();
                boolean xmatch = pop == '(' && element == ')';
                boolean ymatch = pop == '[' && element == ']';
                boolean zmatch = pop == '{' && element == '}';
                boolean match = xmatch || ymatch || zmatch;
                if (!match) {
                    return false;
                }
            }
        }
        int size = signStack.size();
        if (size == 0) return true;
        return false;

    }


    public static void calculate() {
        String number[] = {"1", "2", "3", "4", "5", "6"};
        String cal[] = {"+", "-", "*", "+", "/"};

        Stack numberStack = new Stack();//操作数栈
        Stack calStack = new Stack();//运算符栈
        for (int i = 0; i < number.length; i++) {
            double numberX = Double.parseDouble(number[i]);
            numberStack.push(numberX);
            if (i < number.length - 1) {
                String item = cal[i];//入栈前先做比较
                if (calStack.size() > 0) {//并且有可比较的值了
                    String prevCal = (String) calStack.peek();
                    //判定优先级
                    boolean b = firstPreSecond(prevCal, item);
                    if (b) {
                        numberStack = calAndPushResult(numberStack, calStack);//这样的出来的都只有一个数字的新栈，calStack为空
                    }
                    calStack.push(item);
                } else {
                    calStack.push(item);
                }
            }
        }

        //只有下面两种情况，剩余两个数，和剩余三个数，所以分开处理
        if (numberStack.size() == 2 && calStack.size() == 1) {
            Double second = (Double) numberStack.pop();
            Double first = (Double) numberStack.pop();
            String calxxx = (String) calStack.pop();
            Double calculate = calculate(first, second, calxxx);
            System.out.println(calculate);
        } else {
            Double second = (Double) numberStack.pop();
            Double first = (Double) numberStack.pop();
            String calxxx = (String) calStack.pop();
            Double calculate = calculate(first, second, calxxx);
            numberStack.push(calculate);

            Double second1 = (Double) numberStack.pop();
            Double first1 = (Double) numberStack.pop();
            String calxxx1 = (String) calStack.pop();
            Double calculate1 = calculate(first1, second1, calxxx1);
            System.out.println(calculate1);
        }


    }

    //同级别的计算符，计算出结果，并把下面的低级别的计算也计算出结果。最后记得清栈，把最后的结果入栈
    private static Stack calAndPushResult(Stack numberStack, Stack calStack) {
        if (numberStack.size() == 1) {
            return numberStack;
        }
        double numberX = (double) numberStack.pop();
        double numberPre = (double) numberStack.pop();
        String pop = (String) calStack.pop();//栈顶元素出栈
        Double calResult = calculate(numberPre, numberX, pop);
        numberStack.push(calResult);//入栈
        if (numberStack.size() > 1) {
            calAndPushResult(numberStack, calStack);
        }
        return numberStack;
    }

    private static Double calculate(double first, double second, String pop) {
        Double calResult = null;
        if ("+".equals(pop)) {
            calResult = first + second;
        } else if ("-".equals(pop)) {
            calResult = first - second;
        } else if ("*".equals(pop)) {
            calResult = first * second;
        } else if ("/".equals(pop)) {
            calResult = first / second;
        }
        return calResult;
    }

    /**
     * 后面的比前面的低
     * 判断优先级
     *
     * @param first
     * @param second
     * @return
     */
    private static boolean firstPreSecond(String first, String second) {
        if (first.equals("*") || first.equals("/")) {
            if (second.equals("+") || second.equals("-")) {
                return true;
            }
        }
        return false;
    }
}
