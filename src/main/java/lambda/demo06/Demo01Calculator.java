package lambda.demo06;

/**
 * Lambda表达式有参数有返回值的联系
 * 需求：
 * 给定一个计算器Calculator接口，内含抽象放发calc可以将两个int数字相加得到和
 * 使用Lambda表达式的标准格式调用invokeCalc方法，完成120，130相加
 */
public class Demo01Calculator {
    public static void main(String[] args) {
        // 调用invokeCalc方法，方法的参数是一个接口，可以使用匿名内部类
        invokeCalc(10, 20, new Calculator() {
            @Override
            public int calc(int a, int b) {
                return a + b;
            }
        });
        // 使用Lambda表达式简化匿名内部类的书写
        invokeCalc(10, 20, (int a, int b) -> {
            return a + b;
        });

    }

    public static void invokeCalc(int a, int b, Calculator c) {
        int sum = c.calc(a, b);
        System.out.println(sum);

    }
}
