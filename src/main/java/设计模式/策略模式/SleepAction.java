package 设计模式.策略模式;

/**
 * 具体策略
 */
public class SleepAction implements Action {
    @Override
    public void actionMethod() {
        System.out.println("sleep");
    }
}
