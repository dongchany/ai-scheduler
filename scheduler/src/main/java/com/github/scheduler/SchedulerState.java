package com.github.scheduler;

import lombok.Getter;
import lombok.Setter;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 3:35 PM
 */
public interface SchedulerState {

    boolean isShuttingDown();

    boolean isStarted();

    @Setter
    class SettableSchedulerState implements SchedulerState{

        private boolean isShuttingDown;
        private boolean isStarted;
        @Override
        public boolean isShuttingDown() {
            return isShuttingDown;
        }

        @Override
        public boolean isStarted() {
            return isStarted;
        }
    }
}
