package streamAndMethodReference.demo02Stream;

import java.util.stream.Stream;

/**
 * Stream流中的常用方法 concat：用于把流组合到一起
 * 如果两个流，希望合并成为一个流，那么可以使用Stream接口的方法concat
 * static <T> concat(Stream<? extends T> a, Stream<? extends T> b)
 */
public class Demo08Stream_concat {
    public static void main(String[] args) {
        Stream<String> stream1 = Stream.of("张三丰", "张翠山", "赵敏", "周芷若", "张无忌");
        String[] arr = {"美羊羊", "喜羊羊", "懒羊羊", "灰太狼"};
        Stream<String> stream2 = Stream.of(arr);
        Stream<String> concatStream = Stream.concat(stream1, stream2);
        concatStream.forEach(System.out::println);
    }
}
