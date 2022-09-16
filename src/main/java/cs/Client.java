package cs;

import proxy.TCPSocketClient;
import util.IniUtil;

import java.util.Map;

/**
 * @Author bsx6
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Map<String, Map<String, String>> data = IniUtil.readIniFile(IniUtil.argsGetFileUrl(args));
        new TCPSocketClient(data.get("[server]").get("server_host"),
                Integer.parseInt(data.get("[server]").get("server_port"))
                , data.get("[local]").get("local_host")
                , Integer.parseInt(data.get("[local]").get("local_port")), data.get("[server]").get("token"), Integer.parseInt(data.get("[local]").get("proxy_port")));
    }
}
