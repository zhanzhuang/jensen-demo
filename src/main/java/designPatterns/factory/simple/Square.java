package designPatterns.factory.simple;

/**
 * 步骤二：创建接口的实现类,正方形
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
