import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Test {
    public static void main(String[] args) {
//        boolean isPiv4 = "".matches("");
//        System.out.println(isPiv4);

        LocalDateTime s = LocalDateTime.now();
        String s2 = s.toString().replace("T", " ");
        String s3 = s2.substring(0, s2.length() - 7);
        System.out.println(s3);


    }
}