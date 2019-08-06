package 设计模式.单例模式;

/**
 * 双检锁
 */
public class SingleDoubleCheckLock {
    private static SingleDoubleCheckLock instance;

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
