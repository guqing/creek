package xyz.guqing.creek.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author guqing
 * @date 2020-06-16
 */
public class UserLoginEvent extends ApplicationEvent {
    private String username;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserLoginEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
