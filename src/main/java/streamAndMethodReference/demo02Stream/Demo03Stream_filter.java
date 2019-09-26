package streamAndMethodReference.demo02Stream;

import java.util.stream.Stream;

/**
 * Stream流中的常用方法 filter：用于对Stream流中的数据进行过滤
 * Stream<T> filter(predicate<? super T> predicate);
 * filter方法的参数Predicate是一个函数式接口，所以可以传递Lambda表达式，对数据进行过滤
 * Predicate中的抽象方法
 * boolean test(T t);
 */
public class Demo03Stream_filter {
    public static void main(String[] args) {
        // 创建一个Stream流
        Stream<String> stream = Stream.of("张三丰", "张翠山", "赵敏", "周芷若", "张无忌");
        // 对Stream流中的元素进行过滤，只要姓张的人
        Stream<String> stream2 = stream.filter(name -> name.startsWith("张"));
        // 遍历stream2流
        stream2.forEach(System.out::println);

        /**
         * Stream流属于管道流，只能被消费一次
         * 第一个Stream流调用完毕方法，数据就会流传到下一个Stream上
         * 而这时第一个Stream流已经使用完毕，会关闭
         * 所以第一个Stream流就不能再调用方法了
         */
//        stream.forEach(System.out::println);
    }
}
