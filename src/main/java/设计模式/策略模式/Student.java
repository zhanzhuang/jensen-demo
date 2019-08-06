package 设计模式.策略模式;

public class Student {
    // 具体策略对象
    private Action action;

    public Student(Action action) {
        this.action = action;
    }
    // 学生的行为
    public void studentAction() {
        action.actionMethod();
    }
}
