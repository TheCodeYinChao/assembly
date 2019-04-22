package com.bainuo.service.impl;

import com.bainuo.service.MqListenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MqListenerServiceImpl implements MqListenerService {
    @Override
    public void data(String msg) {
        log.info("recevice msg {}",msg);
    }
}
