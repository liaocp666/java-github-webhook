package cn.liaocp.project.event;

/**
 * @author LiaoChuning
 */
public class EventFactory {

    public Event getEvent (String event) {
        if (event.equalsIgnoreCase("push")) {
            return new PushEvent();
        }
        if (event.equalsIgnoreCase("ping")) {
            return new PingEvent();
        }
        return null;
    }

}
