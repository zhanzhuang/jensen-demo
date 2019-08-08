package designPatterns.strategy;

/**
 * 具体策略
 */
public class SleepAction implements Action {
    @Override
    public void actionMethod() {
        System.out.println("sleep");
    }
}
