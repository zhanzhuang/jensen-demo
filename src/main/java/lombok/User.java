package lombok;

import lombok.extern.slf4j.Slf4j;

@Data // 自动生成get set方法
@AllArgsConstructor // 自动生成全参构造器
@NoArgsConstructor // 自动生成无参构造器
@Builder // 使用Builder模式创建对象
@Slf4j // 生成log对象
public class User {
    private String name;
    private String age;
    private String sex;

    public static void main(String[] args) {
        // builder模式创建对象
        User u = User.builder().name("jack").age("12").sex("男").build();
        System.out.println(u);
        log.info("使用lombok打印日志");

    }
}
