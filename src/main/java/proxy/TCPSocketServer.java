package proxy;

import common.Asshole;
import util.SocketUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author bsx6
 */
public class TCPSocketServer {
    private static Logger logger = Logger.getLogger(TCPSocketClient.class.getSimpleName());

    public TCPSocketServer(int socketPort, Socket socket, String token, String serveHost, String serverPort, String remoteHost, String remotePort) throws IOException {
        ServerSocket socketServer = new ServerSocket(socketPort);
        while (true) {
            Socket accept=null;
            try {
                accept = socketServer.accept();
                SocketUtil.writeOutputStream(socket.getOutputStream(), SocketUtil.readInputStream(accept.getInputStream(), token).replace(serveHost + ":" + serverPort, remoteHost + ":" + remotePort), token);
                SocketUtil.writeOutputStream(accept.getOutputStream(), SocketUtil.readInputStream(socket.getInputStream(), token), token);
            } catch (Exception e) {
                socketServer.close();
                accept.close();
                return;
            }
        }
    }
}
