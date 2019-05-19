package com.reactor;

/**
 * Created by Admin on 2019/5/19.
 */
public abstract class EventHandler {
    private InputSource source;
    public abstract void handle(Event event);

    public InputSource getSource() {
        return source;
    }

    public void setSource(InputSource source) {
        this.source = source;
    }
}
