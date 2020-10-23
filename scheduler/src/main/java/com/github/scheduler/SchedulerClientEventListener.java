package com.github.scheduler;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 7:01 PM
 */
public interface SchedulerClientEventListener {
    void newEvent(ClientEvent event);


    SchedulerClientEventListener NOOP = new SchedulerClientEventListener() {

        @Override
        public void newEvent(ClientEvent event) {
        }
    };
}
