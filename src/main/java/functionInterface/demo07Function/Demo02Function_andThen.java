package functionInterface.demo07Function;

import java.util.function.Function;

/**
 * Function接口中的默认方法andThen：用来进行组合操作
 * 需求：
 * 把String类型的"123"转换为Integer类型，把转换后的结果加10
 * 把增加之后的Integer类型的数据转换为String类型
 * 分析:
 * 转换两次
 * one：把String类型转换成Integer类型
 * 使用Function<String,Integer> fun1
 * Integer i = fun1.apply("123") + 10;
 * two:把Integer类型转换为String类型
 * String s = fun2.apply(i);
 * String s = fun1.andThen(fun2).apply("123") // andThen将两次转换组合使用
 * fun1先调用apply方法，把字符串转换为Integer
 * fun2在调用apply方法，把Integer转换为字符串
 */
public class Demo02Function_andThen {
    public static void change(String s, Function<String, Integer> fun1, Function<Integer, String> fun2) {
        String ss = fun1.andThen(fun2).apply(s);
        System.out.println(ss);
    }

    public static void main(String[] args) {
        // 定义一个字符串类型的整数
        String s = "123";
        // 调用change方法，传递字符串和两个Lambda表达式
        change(s, str -> Integer.parseInt(str) + 10, i -> i + "");
    }
}
