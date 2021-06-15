package functionInterface.demo04Supplier;

import java.util.function.Supplier;

/**
 * 练习：求数组元素的最大值
 * 使用Supplier接口作为方法参数类型，通过Lambda表达式求出int数组中的最大值
 * 提示：接口的泛型请使用Java.lang.Integer类
 */
public class Demo02Test {
    public static int getMax(Supplier<Integer> sup) {
        return sup.get();
    }

    public static void main(String[] args) {
        int[] arr = {199, 34, 234, 999, 65, 3, 5};

        int maxValue = getMax(() -> {
            int max = arr[0];
            for (int i : arr) {
                // 如果i大于max，则替换max作为最大值
                if (i > max) {
                    max = i;
                }
            }
            return max;
        });
        System.out.println("数组中元素的最大值是:" + maxValue);
    }
}
