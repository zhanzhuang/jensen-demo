package functionInterface.demo03Comparator;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 如果一个方法的返回值类型是一个函数式接口，那么就可以直接返回一个Lambda表达式
 * 当需要通过一个方法来获取一个java.util.Comparator接口类型的对象作为排序器时，就可以调用该方法
 */
public class Demo02Comparator {
    public static Comparator<String> getComparator() {
        // 方法的返回值类型是一个接口，那么我们可以返回这个接口的匿名内部类
//        return new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.length() - o2.length();
//            }
//        };

        // 方法的返回值是一个函数式接口，所以我们可以返回一个Lambda表达式
        return (o1, o2) -> o1.length() - o2.length();
    }

    public static void main(String[] args) {
        // 创建一个字符串数组
        String[] arr = {"aaa", "b", "cccccc", "ddddddddd"};
        // 输出排序前的数组
        System.out.println(Arrays.toString(arr));
        // 调用Arrays中的sort方法，对字符串数组进行排序
        Arrays.sort(arr, getComparator());
        // 输出排序后的数组
        System.out.println(Arrays.toString(arr));

    }
}
