package com.github.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 2:58 PM
 */
public class ExecutorUtils {
    public static ThreadFactory defaultThreadFactoryWithPrefix(String prefix){
        return new PrefixingDefaultThreadFactory(prefix);
    }

    private static class PrefixingDefaultThreadFactory implements ThreadFactory{

        private final String prefix;
        private final ThreadFactory defaultThreadFactory;

        public PrefixingDefaultThreadFactory(String prefix) {
            this.defaultThreadFactory = Executors.defaultThreadFactory();
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            final Thread thread = defaultThreadFactory.newThread(r);
            thread.setName(prefix + thread.getName());

            return thread;
        }
    }
}
