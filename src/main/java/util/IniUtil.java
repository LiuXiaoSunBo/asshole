package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author bsx6
 */
public class IniUtil {
    public static Map<String, Map<String, String>> readIniFile(String urlPath) throws Exception {
        InputStream in = new FileInputStream(new File(urlPath));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        Map<String, Map<String, String>> map = new HashMap<>();
        Map m = null;
        String v = null;
        while ((v = br.readLine()) != null) {
            if (v.toString().contains("[")) {
                m = new HashMap();
                map.put(v.toString(), m);
                continue;
            }
            String[] d = v.split("=");
            m.put(d[0], d[1]);
        }
        return map;
    }
    public static String argsGetFileUrl(String... args) {
        return args[1];
    }
}
