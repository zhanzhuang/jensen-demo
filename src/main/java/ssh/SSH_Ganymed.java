package ssh;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SSH_Ganymed {
    public static void main(String[] args) {
        // 服务器ip
        String ipv4Address = "1.1.1.1";
        // freeSshd设置的连接端口
        int port = 22;
        String freeSshdUserName = "username";
        String freeSshdUserPassword = "password";
        // 在cmd中执行的命令
        String command = "java -version";

        connectServer(ipv4Address, port, freeSshdUserName, freeSshdUserPassword, command);

    }

    public static void connectServer(String ipv4Address, int port, String freeSshdUserName, String freeSshdUserpassword, String command) {
        Connection conn = new Connection(ipv4Address, port);
        Session session = null;
        try {
            conn.connect();
            // login
            boolean isLogin = conn.authenticateWithPassword(freeSshdUserName, freeSshdUserpassword);
            if (isLogin) {
                System.out.println("login success");
            } else {
                System.out.println("login failed");
            }
            Session openSession = conn.openSession();
            openSession.execCommand(command);
            InputStream is = new StreamGobbler(openSession.getStdout());
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();

            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
