package anno;

import java.lang.reflect.Field;

/**
 * @author Jensen Zhan
 */
public class UserCheck {
    public static void main(String[] args) {
        User user = new User();
        user.setName("liang");
        user.setAge("1");
        System.out.println(UserCheck.check(user));
    }

    public static boolean check(User user) {
        if (user == null) {
            System.out.println("！！校验对象为空！！");
            return false;
        }
        // 获取User类的所有属性（如果使用getFields，就无法获取到private的属性）
        Field[] fields = User.class.getDeclaredFields();
        for (Field field : fields) {
            // 如果属性有注解，就进行校验
            if (field.isAnnotationPresent(Validate.class)) {
                Validate validate = field.getAnnotation(Validate.class);
                if (field.getName().equals("age")) {
                    if (user.getAge().length() > validate.max()) {
                        System.out.println("！！年龄最大长度校验不通过！！");
                        return false;
                    } else {
                        System.out.println("年龄最大长度校验通过");
                    }
                }
            }
        }
        return true;
    }
}
