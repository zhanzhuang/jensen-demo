package functionInterface.demo02Lambda;

/**
 * 使用Lambda优化日志案例
 * Lambda特点：延迟加载
 * 前提：必须存在函数式接口
 */
public class Demo02Lambda {
    // 定义显示日志方法,参数传递日志等级和MessageBuilder接口
    public static void showLog(int level, MessageBuilder mb) {
        // 对日志的等级进行判断，如果是1级，则调用MessageBuilder接口中的builderMessage方法
        if (level == 1) {
            System.out.println(mb.builderMessage());
        }
    }

    public static void main(String[] args) {
        String msg1 = "hello1";
        String msg2 = "hello2";
        String msg3 = "hello3";
        // 调用showLog方法，参数MessageBuilder是一个函数式接口，所以可以传递Lambda表达式
        showLog(2, () -> msg1 + msg2 + msg3);
        /**
         * 使用Lambda表达式作为参数传递，仅仅是把参数传递到showLog方法中
         * 只有满足条件，日志的等级是1级
         * 才会调用接口MessageBuilder中的builderMessage方法
         * 才会进行字符串的拼接
         * 如果条件不满足，日志的等级不是1级
         * 那么拼接字符串的代码不会执行，不会存在性能浪费
         */
    }

}
