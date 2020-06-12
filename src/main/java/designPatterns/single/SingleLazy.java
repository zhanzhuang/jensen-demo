package designPatterns.single;

/**
 * 懒汉式单例
 */
public class SingleLazy {
    private static SingleLazy SINGLE_LAZY ;

    public static SingleLazy getSingleLazyInstance() {
        if (SINGLE_LAZY == null) {
            SINGLE_LAZY = new SingleLazy();
            return SINGLE_LAZY;
        }
        return SINGLE_LAZY;
    }
}
