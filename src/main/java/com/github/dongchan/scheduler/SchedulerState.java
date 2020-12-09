package com.github.dongchan.scheduler;


/**
 * @author DongChan
 * @date 2020/10/23
 * @time 3:35 PM
 */
public interface SchedulerState {

    boolean isShuttingDown();

    boolean isStarted();

    class SettableSchedulerState implements SchedulerState {

        private boolean isShuttingDown;
        private boolean isStarted;

        @Override
        public boolean isShuttingDown() {
            return isShuttingDown;
        }

        public void setShuttingDown(boolean shuttingDown) {
            isShuttingDown = shuttingDown;
        }

        @Override
        public boolean isStarted() {
            return isStarted;
        }

        public void setStarted(boolean started) {
            isStarted = started;
        }
    }
}
