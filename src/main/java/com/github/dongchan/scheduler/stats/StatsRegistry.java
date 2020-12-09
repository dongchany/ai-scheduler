package com.github.dongchan.scheduler.stats;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 11:40 PM
 */
public interface StatsRegistry {

    enum SchedulerStatsEvent{
        UNEXPECTED_ERROR,
        UNRESOLVED_TASK,
        RAN_EXECUTE_DUE
    }

    void register(SchedulerStatsEvent e);

    StatsRegistry NOOP = new DefaultStatsRegistry();

    class DefaultStatsRegistry implements StatsRegistry{

        @Override
        public void register(SchedulerStatsEvent e) {

        }
    }
}
