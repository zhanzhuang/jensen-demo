package algorithm;

/**
 * 选择排序
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] arr = {6, 3, 8, 2, 9, 1};
        int max = 0;
        int index = 0;
        // 外层循环，控制选择的次数。数组长度为6，一共选择5次
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (max < arr[j]) {
                    max = arr[j];
                    index = j;
                }
            }
            // 每次选择完成后，max中存放的是该轮选出的最大值
            // 将max指向位置的元素和数组最后一个元素位置呼唤
            int temp = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = max;
            arr[index] = temp;
            // 晴空max和index，便于下次
            max = 0;
            index = 0;

        }
        for (int num : arr) {
            System.out.println(num);
        }
    }
}
