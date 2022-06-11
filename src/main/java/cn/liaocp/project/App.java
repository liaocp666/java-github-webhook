package cn.liaocp.project;

import cn.liaocp.project.handler.WebHookHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Kent Liao
 */
public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    private static String URL = "/webhook";

    private static Integer PORT = 4567;

    public static String secret = "123456";

    static {
        try {
            Properties properties = new Properties();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("config.properties"));
            properties.load(bufferedReader);
            URL = properties.getProperty("url");
            PORT = Integer.valueOf(properties.getProperty("port"));
            secret = properties.getProperty("secret");
        } catch (Exception e) {
            LOGGER.warning("读取配置出错，使用默认的地址端口");
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext(URL, new WebHookHandler());
        server.start();
        LOGGER.info(String.format("服务启动完成，地址：[%s]，端口：[%s]", URL, PORT));
    }
}
