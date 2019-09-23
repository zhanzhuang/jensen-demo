package functionInterface.demo06Predicate;

import java.util.function.Predicate;

public class Demo03Predicate_or {
    public static boolean checkString(String s, Predicate<String> pre1, Predicate<String> pre2) {
        return pre1.test(s) || pre2.test(s);
    }

    public static void main(String[] args) {
        String s = "bc";
        boolean b = checkString(s,(String str) -> str.length() > 5, (String str) ->  str.contains("a"));
        System.out.println(b);
    }
}
