package designPatterns.single;

/**
 * 双检锁
 */
public class SingleDoubleCheckLock {
    private static volatile SingleDoubleCheckLock instance;

    public static SingleDoubleCheckLock getInstance() {
        if (instance == null) {
            synchronized (SingleDoubleCheckLock.class) {
                if (instance == null) {
                    instance = new SingleDoubleCheckLock();
                }
            }
        }
        return instance;
    }
}
