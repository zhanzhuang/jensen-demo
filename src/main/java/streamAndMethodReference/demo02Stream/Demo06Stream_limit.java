package streamAndMethodReference.demo02Stream;

import java.util.stream.Stream;

/**
 * Stream流中的常用方法 limit：用于截取流中的元素
 * limit方法可以对流进行截取，截取前n个
 * Stream<T> limit(long maxSize);
 * 参数是一个long类型，如果集合当前长度大于参数进行截取，否则不操作
 * limit方法是一个延迟方法，只是对流中的元素进行截取，返回的是一个新的流
 * 所以可以继续调用Stream流中的其他方法
 */
public class Demo06Stream_limit {
    public static void main(String[] args) {
        // 获取一个Stream流
        String[] arr = {"美羊羊", "喜羊羊", "懒羊羊", "灰太狼"};
        Stream<String> stream = Stream.of(arr);
        // 进行截取
        Stream<String> stream2 = stream.limit(3);
        // 遍历stream2
        stream2.forEach(System.out::println);
    }
}
