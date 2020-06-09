package functionInterface.demo07Function;

import java.util.function.Function;

/**
 * 练习：自定义函数模型拼接
 * 题目: 请使用Function进行函数模型的拼接，按照顺序需要执行的多个函数操作为：
 * String str = "赵丽颖,20";
 */
public class Demo03Test {

    public static int change(String s, Function<String, String> fun1, Function<String, Integer> fun2, Function<Integer, Integer> fun3) {
        return fun1.andThen(fun2).andThen(fun3).apply(s);
    }

    public static void main(String[] args) {
        String str = "赵丽颖,20";
        int num = change(str, s -> s.split(",")[1], s -> Integer.parseInt(s), i -> i + 100);
        System.out.println(num);
    }
}
