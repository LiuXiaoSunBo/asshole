package cs;

import proxy.BaseSocketServer;
import util.IniUtil;

import java.util.Map;

/**
 * @Author bsx6
 */
public class Server {
    public static void main(String[] args) throws Exception {
        Map<String, Map<String, String>> data = IniUtil.readIniFile(IniUtil.argsGetFileUrl(args));
        new BaseSocketServer(data.get("[server]").get("server_host"),
                Integer.parseInt(data.get("[server]").get("server_port")), data.get("[server]").get("token"));
    }
}
