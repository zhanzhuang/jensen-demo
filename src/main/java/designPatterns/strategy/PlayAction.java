package designPatterns.strategy;

/**
 * 具体策略
 */
public class PlayAction implements Action {
    @Override
    public void actionMethod() {
        System.out.println("play");
    }
}
