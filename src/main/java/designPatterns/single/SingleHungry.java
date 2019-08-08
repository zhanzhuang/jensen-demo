package designPatterns.single;

/**
 * 饿汉时单例，线程安全
 */
public class SingleHungry {
    private static SingleHungry singleHungry;

    private SingleHungry(){

    }

    public SingleHungry getSingleHungryInstance(){
        if (singleHungry == null) {
            return new SingleHungry();
        } else {
            return singleHungry;
        }
    }
}
