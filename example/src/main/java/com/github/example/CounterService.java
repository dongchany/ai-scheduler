package com.github.example;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 4:01 PM
 */
@Service
public class CounterService {
    private final AtomicLong count = new AtomicLong(0L);
    public void increase(){
       count.incrementAndGet();
    }
    public long read(){
        return count.get();}
}
