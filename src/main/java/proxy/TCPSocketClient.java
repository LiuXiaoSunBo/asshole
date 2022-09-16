package proxy;

import common.Asshole;
import util.SocketUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author bsx6
 */
public class TCPSocketClient {
    private static Logger logger = Logger.getLogger(TCPSocketClient.class.getSimpleName());

    public TCPSocketClient(String severHost, Integer serverPort, String remoteHost, Integer remotePort, String token, Integer proxyPort) throws IOException, InterruptedException {
        Socket socket = new Socket(severHost, serverPort);
        socket.setKeepAlive(true);
        socket.setSoTimeout(0);
        SocketUtil.writeOutputStream(socket.getOutputStream(), token, token);
        String data = SocketUtil.readInputStream(socket.getInputStream(), token);
        if (data.trim().equals(Asshole.CHECK_MESSAGE)) {
            logger.log(Level.WARNING, "check->校验通过~");
            SocketUtil.writeOutputStream(socket.getOutputStream(), proxyPort + ":" + remoteHost + ":" + remotePort, token);
            while (true) {
                logger.info("request->请求来了~");
                String msg = SocketUtil.readInputStream(socket.getInputStream(), token);
                if (msg.length() > 0) {
                    Socket remoteSocket = getSocketLocal(remoteHost, remotePort);
                    SocketUtil.writeOutputStream(remoteSocket.getOutputStream(), msg, token);
                    SocketUtil.writeOutputStream(socket.getOutputStream(), SocketUtil.readInputStream(remoteSocket.getInputStream(), token), token);
                    remoteSocket.close();
                }
            }

        }

    }

    private Socket getSocketLocal(String host, Integer port) throws IOException, InterruptedException {
        Socket
                remoteSocket = new Socket(host, port);
        remoteSocket.setKeepAlive(true);
        remoteSocket.setSoTimeout(0);
        while (!remoteSocket.isConnected()) {
            logger.log(Level.INFO, "error-> 本地端口未启动~");
            TimeUnit.SECONDS.sleep(60);
        }
        return remoteSocket;
    }
}
