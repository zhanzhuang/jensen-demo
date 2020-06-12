package designPatterns.single;

/**
 * @author Jensen Zhan
 */
public class Test {

    public static void main(String[] args) {
        SingleHungry singleHungryInstance = SingleHungry.getSingleHungryInstance();
        SingleHungry singleHungry = SingleHungry.getSingleHungryInstance();
        System.out.println(singleHungry == singleHungryInstance);

        SingleLazy singleLazy = SingleLazy.getSingleLazyInstance();
        SingleLazy singleLazy1 = SingleLazy.getSingleLazyInstance();
        System.out.println(singleLazy == singleLazy1);

        SingleDoubleCheckLock singleDoubleCheckLock = SingleDoubleCheckLock.getInstance();
        SingleDoubleCheckLock singleDoubleCheckLock1 = SingleDoubleCheckLock.getInstance();
        System.out.println(singleDoubleCheckLock == singleDoubleCheckLock1);

    }

}
