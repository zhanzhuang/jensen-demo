package functionInterface.demo05Consumer;

import java.util.function.Consumer;

/**
 * java.util.function.Consumer<T>接口正好于Supplier接口相反，
 * Consumer接口中包含抽象方法void accept(T t),意为消费一个指定泛型的数据
 * 至于怎么消费(使用),需要自定义(输出，计算......)
 */
public class Demo01Consumer {
    /**
     * 定义一个方法
     * 方法的参数传递一个字符串的姓名
     * 方法的参数传递Consumer接口，泛型使用String
     * 可以使用Consumer接口消费字符串的姓名
     */
    public static void method(String name, Consumer<String> con) {
        con.accept(name);
    }

    public static void main(String[] args) {
        // 调用method方法，传递字符串姓名，方法的另一个参数是Consumer接口，是一个函数式接口，所以可以传递Lambda表达式
        method("赵丽颖", (String name) -> {
            // 消费方式1：直接输出
            System.out.println(name);

            // 消费方式2：把字符串进行反转输出
            String reName = new StringBuilder(name).reverse().toString();
            System.out.println(reName);
        });
    }
}
