package util;

import common.Asshole;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author bsx6
 */
public class SocketUtil {
    public static String readInputStream(InputStream inputStream, String token) throws IOException {
        byte[] bytes = new byte[1024];
        StringBuilder data = new StringBuilder();
        // 出现结束符时代表报文结束
        while (data.toString().indexOf(Asshole.END_MESSAGE) < 0  && inputStream.read(bytes) != -1) {
            // 替换掉空格符
            data.append(new String(bytes).replaceAll(Asshole.NULL_CODE, ""));
        }
        // 替换HOST
        return MsgUtil.msgDecrypt(data.toString(), token);
    }

    public static void writeOutputStream(OutputStream outputStream, String msg, String token) throws IOException {
        outputStream.write((MsgUtil.msgEncrypt(msg, token) + Asshole.END_MESSAGE).getBytes());
        outputStream.flush();
    }
}
