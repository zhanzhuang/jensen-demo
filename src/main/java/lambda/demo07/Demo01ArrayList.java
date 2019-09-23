package lambda.demo07;

import java.util.ArrayList;

/**
 * Lambda表达式：是可推导，可省略
 * 凡是根据上下文推导出来的内容，都可以省略书写
 * 可以省略的内容：
 * 1.(参数列表)：括号中的参数的数据类型，可以省略不写
 * 2.(参数列表)：括号中的参数如果只有一个，那么类型和()都可以省略
 * 3.{一些代码}：如果{}中的代码只有一行，无论是否有返回值，都可以省略({},return,分号)
 */

/**
 * Lambda的使用前提：
 * 1.必须具有接口，并且有且仅有一个抽象方法
 * 2.必须具有上下文推断，也就是方法的参数或局部变量类型必须为Lambda对应的接口类型
 */
public class Demo01ArrayList {
    public static void main(String[] args) {
        // jdk1.7之前，创建集合对象必须把前后泛型写上
        ArrayList<String> list01 = new ArrayList<String>();
        // jdk1.7之后，=后边的泛型可以省略，后边的泛型可以根据前边的泛型推导出来
        ArrayList<String> list02 = new ArrayList<>();

        // 只有一行代码的Lambda省略方式
        new Thread(() -> System.out.println(Thread.currentThread().getName() + "新线程创建了")).start();
    }
}
