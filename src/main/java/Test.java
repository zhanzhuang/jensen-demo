import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
//        boolean isPiv4 = "".matches("");
//        System.out.println(isPiv4);

        LocalDateTime s = LocalDateTime.now();
        String s2 = s.toString().replace("T", " ");
        String s3 = s2.substring(0, s2.length() - 7);
        // TODO: 2019-08-08
        System.out.println(s3);

        System.out.println("test.xml");
        System.out.println("hello world");
        for (int i = 0; i < 2; i++) {
            System.out.println(i);
        }
    }
}
