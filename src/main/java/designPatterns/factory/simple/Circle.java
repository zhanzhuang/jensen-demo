package designPatterns.factory.simple;
/**
 * 步骤二：创建接口的实现类,圆
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
