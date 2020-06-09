package functionInterface.demo01FunctionalInterface;

/**
 * 函数式接口：
 * 有且只有一个抽象方法的接口，称之为函数式接口
 * 当然接口中可以包含其他的方法(默认，静态，私有)
 *
 * @FunctionalInterface 是：编译成功
 * 否：编译失败(1.接口中没有抽象方法 2.抽象方法的个数大于1)
 */
@FunctionalInterface
public interface MyFunctionlInterface {
    public abstract void method();

//    void method2();
}
