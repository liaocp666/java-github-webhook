package cn.liaocp.project.event;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author LiaoChuning
 */
public class PingEvent implements Event{

    private static final Logger LOGGER = Logger.getLogger(PingEvent.class.getName());

    @Override
    public void run(HttpExchange exchange) throws IOException {
        LOGGER.info("开始执行 ping 事件脚本");
        exec("ping");
    }
}
