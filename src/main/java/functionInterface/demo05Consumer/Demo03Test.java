package functionInterface.demo05Consumer;

import java.util.function.Consumer;

/**
 * 练习：
 * 字符串数组当中存有多条信息，请按照格式 "姓名":XX 的格式打印出来。
 * 要求将打印姓名的动作作为第一个Consumer接口的Lambda实例
 * 将两个Consumer接口按照顺序拼接到一起
 */
public class Demo03Test {

    public static void printInfo(String[] arr, Consumer<String> con1, Consumer<String> con2) {
        for (String message : arr) {
            con1.andThen(con2).accept(message);
        }
    }

    public static void main(String[] args) {
        String[] arr = {"迪丽热巴,女", "古力娜扎,女", "马儿扎哈,男"};
        printInfo(arr, (message) -> {
            String name = message.split(",")[0];
            System.out.print("姓名:" + name);
        }, (message) -> {
            String age = message.split(",")[1];
            System.out.println("。年龄：" + age + "。");
        });
    }
}
