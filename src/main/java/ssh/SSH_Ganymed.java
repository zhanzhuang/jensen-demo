package ssh;

import com.jcraft.jsch.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class SSH_Ganymed {
    private static JSch jSch;
    private static Session session;

    private String freesshdIpFour;
    private int freesshdPort;
    private String freesshdUsername;
    private String freesshdPassword;
    private String command;

    public SSH_Ganymed(String freesshdIpFour, int freesshdPort, String freesshdUsername, String freesshdPassword, String command) {
        this.freesshdIpFour = freesshdIpFour;
        this.freesshdPort = freesshdPort;
        this.freesshdUsername = freesshdUsername;
        this.freesshdPassword = freesshdPassword;
        this.command = command;
    }

    public String connectFreesshd() {
        // 创建JSch对象
        jSch = new JSch();
        BufferedReader reader = null;
        Channel channel = null;
        try {
            session = jSch.getSession(freesshdUsername, freesshdIpFour, freesshdPort);
            session.setPassword(freesshdPassword);
            Properties config = new Properties();
            session.setTimeout(1500);
            session.connect();
            if (command != null) {
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);
                channel.connect();
                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in, "GBK"));
                String buf;
                StringBuffer sb = new StringBuffer();
                while ((buf = reader.readLine()) != null) {
                    sb = sb.append(buf);
                }
                return sb.toString();
            }
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
            session.disconnect();
            if (channel != null) {
                channel.disconnect();
            }
        }
        return null;
    }
}
