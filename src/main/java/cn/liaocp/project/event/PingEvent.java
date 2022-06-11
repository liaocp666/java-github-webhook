package cn.liaocp.project.event;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author LiaoChuning
 */
public class PingEvent {

    private static final Logger LOGGER = Logger.getLogger(PingEvent.class.getName());

    public void run() throws IOException {
        LOGGER.info("开始执行 ping 事件脚本");
        new EventExec().exec("ping");
    }

}
