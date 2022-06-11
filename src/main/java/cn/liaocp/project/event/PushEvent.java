package cn.liaocp.project.event;

import java.io.*;
import java.util.logging.Logger;

/**
 * @author Kent Liao
 */
public class PushEvent {

    private static final Logger LOGGER = Logger.getLogger(PushEvent.class.getName());

    public void run() throws IOException {
        LOGGER.info("开始执行 push 事件脚本");
        new EventExec().exec("push");
    }

}
