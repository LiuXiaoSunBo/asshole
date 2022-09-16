package proxy;

import common.Asshole;
import util.SocketUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author bsx6
 */
public class BaseSocketServer {

    private String token;
    private String host;

    public BaseSocketServer(String host, Integer port, String token) throws IOException {
        this.token = token;
        this.host = host;
        startProxy(port);
    }

    private static Map<Socket, Boolean> session = Collections.synchronizedMap(new HashMap());

    private void startProxy(int port) throws IOException {
        ServerSocket socket = new ServerSocket(port);
        while (true) {
            Socket accept = socket.accept();
            if (session.get(accept) == null || !session.get(accept)) {
                boolean check = checkToken(accept.getInputStream());
                if (check) {
                    accept.setKeepAlive(true);
                    accept.setSoTimeout(0);
                    session.put(accept, check);
                    SocketUtil.writeOutputStream(accept.getOutputStream(), Asshole.CHECK_MESSAGE, token);
                    String[] hostWithPort = SocketUtil.readInputStream(accept.getInputStream(), token).split(";");
                    for (int i = 0; i < hostWithPort.length; i++) {
                        String[] client = hostWithPort[i].split(":");
                        new Thread(() -> {
                            try {
                                new TCPSocketServer(Integer.parseInt(client[0]),
                                        accept, token, client[1], client[2], host, String.valueOf(port));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                }
            }
        }
    }

    private boolean checkToken(InputStream inputStream) throws IOException {
        String data = SocketUtil.readInputStream(inputStream, token);
        if (data != null && data.length() > 0) {
            return this.token.equals(token);
        }
        return false;
    }
}
