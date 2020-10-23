package com.github.scheduler;

import java.time.Instant;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 11:25 PM
 */
public interface Clock {
    Instant now();
}
