package streamAndMethodReference.demo10ArrayMethodReference;

/**
 * 数组的构造器引用
 */
public class Demo {
    /**
     * 定义一个方法
     * 方法的参数传递创建数组的长度和ArrayBuilder接口
     * 方法内部根据传递的长度使用ArrayBuilder中的方法创建数组并返回
     */
    public static int[] createArray(int length, ArrayBuilder arrayBuilder) {
        return arrayBuilder.builderArray(length);
    }

    public static void main(String[] args) {
        // 调用createArray方法，传递数组的长度和Lambda表达式
        int[] arr1 = createArray(10,(len) -> {
           return new int[len];
        });
        System.out.println(arr1.length);
        /**
         * 使用方法引用优化Lambda表达式
         * 已知创建数组的就是int[]数组
         * 数组的长度也是已知的
         * 就可以使用int[]引用new
         */
        int[] arr2 = createArray(10, int[]::new);
        System.out.println(arr2.length);
    }
}
