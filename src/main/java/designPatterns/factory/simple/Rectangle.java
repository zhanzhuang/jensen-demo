package designPatterns.factory.simple;

/**
 * 步骤二：创建接口的实现类,矩形
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
