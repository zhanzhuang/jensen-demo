package algorithm;

import java.util.Arrays;

/**
 * 选择排序
 */
public class SortSelect {
    public static void main(String[] args) {
        int[] arr = {6, 3, 8, -2, 9, -1};

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) { // 寻找最小的数
                    minIndex = j; // 将最小数的索引保存
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        Arrays.stream(arr).forEach(System.out::println);
    }
}
