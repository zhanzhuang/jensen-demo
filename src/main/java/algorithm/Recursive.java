package algorithm;

/**
 * 递归求阶乘
 */
public class Recursive {
    public static void main(String[] args) {
        System.out.println(recursive(9));
    }

    public static int recursive(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * recursive(n - 1);
        }
    }
}
