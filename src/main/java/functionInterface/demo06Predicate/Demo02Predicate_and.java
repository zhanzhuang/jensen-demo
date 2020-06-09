package functionInterface.demo06Predicate;

import java.util.function.Predicate;

public class Demo02Predicate_and {
    /**
     * 定义一个方法，方法的参数，传递一个字符串
     * 传递两个Predicate接口
     * 一个用于判断字符串的长度是否大于5
     * 一个用于判断字符串中是否包含a
     * 两个条件必须同时满足
     */
    public static boolean checkString(String s, Predicate<String> pre1, Predicate<String> pre2) {
//        return pre1.test(s) && pre1.test(s);
        return pre1.and(pre2).test(s);
    }

    public static void main(String[] args) {
        // 定义一个字符串
        String s = "abcdef";
        boolean b = checkString(s, (String str) -> str.length() > 5, (String str) -> str.contains("a"));
        System.out.println(b);
    }
}
