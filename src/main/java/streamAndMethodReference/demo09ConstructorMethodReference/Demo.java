package streamAndMethodReference.demo09ConstructorMethodReference;

/**
 * 类的构造器引用
 */
public class Demo {
    // 定义一个方法，参数传递姓名和PersonBuilder接口
    // 方法中通过姓名创建Person对象
    public static void printName(String name, PersonBuilder p) {
        Person person = p.builderPerson(name);
        System.out.println(person.getName());
    }

    public static void main(String[] args) {
        // 调用 printName 方法，方法的参数PersonBuilder接口是一个函数式接口，可以传递Lambda
        printName("迪丽热巴", (String name) -> {
            return new Person(name);
        });

        /**
         * 使用方法引用优化Lambda表达式
         * 构造方法new Person(String name)已知
         * 创建对象已知 new
         * 就可以使用Person引用new创建对象
         */
        printName("斋藤飞鸟", Person::new);
    }
}
