package functionInterface.demo06Predicate;

import java.util.function.Predicate;

/**
 * 需求：判断一个字符串长度是否大于5
 * 如果字符串的长度大于5，那么返回false
 * 如果字符串的长度不大于5，那么返回true
 */
public class Demo04Predicate_negate {
    public static boolean checkString(String s, Predicate<String> pre) {
//        return !pre.test(s);
        return pre.negate().test(s);
    }

    public static void main(String[] args) {
        String s = "abc";

        boolean b = checkString(s, (String str) -> str.length() > 5);
        System.out.println(b);
    }
}
