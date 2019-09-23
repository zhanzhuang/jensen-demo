package functionInterface.demo06Predicate;

import java.util.function.Predicate;

/**
 * java.util.function.Predicate
 * 作用：对某种数据类型的数据进行判断，结果返回一个boolean值
 * Predicate接口中包含一个抽象放发：
 * boolean test(T t):用来执行数据类型数据进行判断的方法
 * 条件符合返回true
 * 条件不符合返回false
 */
public class Demo01Predicate {
    /**
     * 定义一个方法
     * 参数传递一个string类型的字符串
     * 传递一个Predicate接口，泛型使用String
     * 使用Predicate中的方法test对字符串进行判断，并把判断的结果返回
     */
    public static boolean checkString(String s, Predicate<String> pre) {
        return pre.test(s);
    }

    public static void main(String[] args) {
        String s = "abcde";

        // 调用checkString方法对字符串进行校验，参数传递字符串和Lambda表达式
        boolean b = checkString(s, (String str) -> {
            // 对参数传递的字符串进行判断，判断字符串的长度是否大于5，并把判断的结果返回
            return str.length() > 5;
        });
        System.out.println(b);
    }
}
