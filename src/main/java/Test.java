import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {
//    public static void main(String[] args) {
//        String urlz = concatUrl("aaa", "bbb");
//        System.out.println(urlz);
//        String url1 = concatUrl("http://10.69.94.140:8080", "/api/equipment/getEquipmentByNo/");
//        String url2 = concatUrl(url1, "123");
//        System.out.println(url2);
//    }
//
//    public static String concatUrl(String url, String path) {
//        String newUrl;
//        if (url.endsWith("/") && path.startsWith("/")) {
//            newUrl = url.concat(path.substring(1));
//        } else {
//            newUrl = !url.endsWith("/") && !path.startsWith("/") ? url.concat("/").concat(path) : url.concat(path);
//        }
//
//        return newUrl;
//    }

  public static void main(String[] args) {
    Map<String, Object> map = new HashMap<>();
    map.put("demokey", "demovalue");
    System.out.println(map.entrySet());
    Set<String> set = new HashSet<>();
    List<Object> list = new LinkedList<>();
  }
}
