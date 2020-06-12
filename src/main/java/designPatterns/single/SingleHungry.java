package designPatterns.single;

/**
 * 饿汉式单例，线程安全
 */
public class SingleHungry {
    private static final SingleHungry SINGLE_HUNGRY = new SingleHungry();

    public static SingleHungry getSingleHungryInstance() {
        return SINGLE_HUNGRY;
    }
}
