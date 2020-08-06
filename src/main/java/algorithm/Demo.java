package algorithm;

import java.util.Arrays;

/**
 * @author Jensen Zhan
 */
public class Demo {
    public static void main(String[] args) {
        int[] arr = {6, 3, 8, -2, 9, -1};
        // bubble
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = 0; j < arr.length - 1 - i; j++) {
//                if (arr[j] > arr[j + 1]) {
//                    int temp = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = temp;
//                }
//            }
//        }

        // choose
//        for (int i = 0; i < arr.length; i++) {
//            int minIndex = i;
//            for (int j = i + 1; j < arr.length; j++) {
//                if (arr[j] < arr[minIndex]) {
//                    minIndex = j;
//                }
//            }
//            int temp = arr[i];
//            arr[i] = arr[minIndex];
//            arr[minIndex] = temp;
//        }

        // insert
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[i]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        Arrays.stream(arr).forEach(item -> System.out.print(item + " "));
    }

}
