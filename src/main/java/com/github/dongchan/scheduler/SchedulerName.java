package com.github.dongchan.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 2:10 PM
 */
public interface SchedulerName {
    String getName();

    class Hostname implements SchedulerName {
        private static final Logger log = LoggerFactory.getLogger(Hostname.class);
        private String cacheHostname;

        public Hostname() {
            try {
                long start = System.currentTimeMillis();
                log.debug("Resolving hostname...");
                cacheHostname = InetAddress.getLocalHost().getHostName();
                long duration = System.currentTimeMillis() - start;
                if (duration > 1000) {
                    log.warn("Hostname-lookup took {}ms", duration);
                }
            } catch (UnknownHostException e) {
                log.warn("Failed to resolve hostname. Using dummy-name for scheduler.");
                cacheHostname = "failed.hostname.lookup.";
            }
        }

        @Override
        public String getName() {
            return cacheHostname;
        }
    }
}
