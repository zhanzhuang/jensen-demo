package algorithm;

import java.util.Arrays;

/**
 * 插入排序
 */
public class SortInsert {

    public static void main(String[] args) {

        int[] arr = {6, 3, 8, -2, 9, -1};

        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] < arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        Arrays.stream(arr).forEach(System.out::println);
    }
}
