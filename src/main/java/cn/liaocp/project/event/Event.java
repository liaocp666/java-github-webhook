package cn.liaocp.project.event;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * @author LiaoChuning
 */
public interface Event {

    void run(HttpExchange exchange) throws IOException;

    default void exec (String event) throws IOException {
        new EventExec().exec(event);
    }

}
