package com.tanykoo.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ThinkPad
 * Created : 2018-08-29 16:05
 * @Since
 */
public class Subject {
    private static Logger logger = LoggerFactory.getLogger(Subject.class);

    private List<EventListener> listeners = new ArrayList<>();

    public void addListener(EventListener l){
        listeners.add(l);
    }

    public void noticy(List<EventListener> listeners) {
        for(EventListener l : listeners){
            l.hello(new Event(this));
        }
    }
}
