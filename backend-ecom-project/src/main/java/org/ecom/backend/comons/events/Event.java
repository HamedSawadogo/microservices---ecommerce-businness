package org.ecom.backend.comons.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class Event extends ApplicationEvent  {
    public Event(Object source) {
        super(source);
    }
}
