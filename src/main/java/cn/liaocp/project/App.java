package cn.liaocp.project;

import cn.liaocp.project.handler.WebHookHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

/**
 * @author Kent Liao
 */
public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    private static final String URL = "/webhook";

    private static final int PORT = 4567;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext(URL, new WebHookHandler());
        server.start();
        LOGGER.info(String.format("服务启动完成，地址：[%s]，端口：[%s]", URL, PORT));
    }
}
