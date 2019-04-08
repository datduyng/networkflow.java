package com.networkflow.app;

import com.almasb.fxgl.entity.Entity;

import javafx.event.Event;
import javafx.event.EventType;

public class CarEvent extends Event {
	
	public static final EventType<CarEvent> ANY
     = new EventType<>(Event.ANY, "CAR_EVENT");
	
	private Entity car;
	
	public CarEvent(EventType<? extends Event> eventType, Entity car) {
        super(eventType);
        this.car = car;
	}
}
