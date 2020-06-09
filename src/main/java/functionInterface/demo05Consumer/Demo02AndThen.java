package functionInterface.demo05Consumer;

import java.util.function.Consumer;

/**
 * Consumer接口的默认方法andThen
 * 作用：需要两个Consumer接口，可以把两个Consumer接口组合到一起，再对数据进行消费
 * 例如：
 * Consumer<String> con1
 * Consumer<String> con2
 * String s = "hello";
 * con1.accept(s);
 * con2.accept(s);
 * 链接两个Consumer接口，再进行消费
 * con1.andThen(con2).accept(s); // 谁在前面谁先消费
 */
public class Demo02AndThen {
    // 定义一个方法，方法的参数传递一个字符串和两个Consumer接口，Consumer接口的泛型使用字符串
    public static void method(String s, Consumer<String> con1, Consumer<String> con2) {
        con1.accept(s);
        con2.accept(s);

        // 使用andThen方法，把两个Consumer接口连接到一起，再消费数据
        con1.andThen(con2).accept(s); // con1(先消费) 连接 con2(后消费),
    }

    public static void main(String[] args) {
        // 调用method方法，传递一个字符串两个Lambda表达式
        method("Hello", (t) -> {
            // 消费方式：把字符串转换为大写输出
            System.out.println(t.toUpperCase());
        }, (t) -> {
            System.out.println(t.toLowerCase());
        });
    }
}
