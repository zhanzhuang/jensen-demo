package 设计模式.策略模式;

public class PlayAction implements Action {
    @Override
    public void actionMethod() {
        System.out.println("play");
    }
}
