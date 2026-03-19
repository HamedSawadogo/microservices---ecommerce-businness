package org.ecom.backend.shared.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class Event extends ApplicationEvent  {
    public Event(Object source) {
        super(source);
    }
}
