package com.cache.lock;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Data
@PropertySources(value = {@PropertySource("classpath:zookeeper.properties")})
@Component
public class WrapperZk {
    @Autowired
    private Environment environment;

    private int retryCount;

    private int elapsedTimeMs;

    private String connectString;

    private int sessionTimeoutMs;

    private int connectionTimeoutMs;

    @Bean(name = "WrapperZk",initMethod = "init")
    public WrapperZk instance(){
        return new WrapperZk();
    }

    private void init() {
        this.retryCount = Integer.valueOf(environment.getRequiredProperty("curator.retryCount"));

        this.elapsedTimeMs = Integer.valueOf(environment.getRequiredProperty("curator.elapsedTimeMs"));

        this.connectString = environment.getRequiredProperty("curator.connectString") ;

        this.sessionTimeoutMs = Integer.valueOf(environment.getRequiredProperty("curator.sessionTimeoutMs"));

        this.connectionTimeoutMs = Integer.valueOf(environment.getRequiredProperty("curator.connectionTimeoutMs"));
    }
}