package designPatterns.single;

/**
 * 饿汉式单例
 */
public class SingleLazy {
    private static final SingleLazy SINGLE_LAZY = new SingleLazy();

    private SingleLazy() {

    }


    public static SingleLazy getSingleLazyInstance() {
        return SINGLE_LAZY;
    }
}
