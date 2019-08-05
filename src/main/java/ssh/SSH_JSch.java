package ssh;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class SSH_JSch {
    private static JSch jsch;
    private static Session session;

    /**
     * 连接到指定的机器
     *
     * @param user
     * @param passwd
     * @param host
     * @param port
     */
    public static void connect(String user, String passwd, String host, int port) {
        jsch = new JSch();// 创建JSch对象
        try {
            session = jsch.getSession(user, host, port);// 根据用户名、主机ip、端口号获取一个Session对象
            session.setPassword(passwd);// 设置密码

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);// 为Session对象设置properties
            session.setTimeout(1500);// 设置超时
            session.connect();// 通过Session建立连接
        } catch (JSchException e) {
            e.printStackTrace();
        }

    }

    /**
     * 执行命令
     *
     * @param command
     * @throws JSchException
     */
    public static void execCmd(String command) {
        BufferedReader reader = null;
        Channel channel = null;
        try {
            if (command != null) {
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);
                // ((ChannelExec) channel).setErrStream(System.err);
                channel.connect();

                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                String buf = null;
                while ((buf = reader.readLine()) != null) {
                    System.out.println(buf);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            channel.disconnect();
        }
    }


    /**
     * 关闭连接
     */
    public static void close() {
        session.disconnect();
    }

    public static void main(String[] args) {
        connect("root", "root", "1.1.1.1", 22);

        execCmd("java -version");

        close();
    }
}
