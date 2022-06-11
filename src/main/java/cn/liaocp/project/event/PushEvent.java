package cn.liaocp.project.event;

import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.logging.Logger;

/**
 * @author Kent Liao
 */
public class PushEvent implements Event {

    private static final Logger LOGGER = Logger.getLogger(PushEvent.class.getName());

    @Override
    public void run(HttpExchange exchange) throws IOException {
        LOGGER.info("开始执行 push 事件脚本");
        exec("push");
    }
}
