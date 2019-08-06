package 设计模式.策略模式;

public class Test {
    public static void main(String[] args) {
        Action sleep = new SleepAction();
        Action play = new PlayAction();

        Student s1 = new Student(sleep);
        s1.studentAction();

        Student s2 = new Student(play);
        s2.studentAction();
    }
}
