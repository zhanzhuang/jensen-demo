package designPatterns.strategy;

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
