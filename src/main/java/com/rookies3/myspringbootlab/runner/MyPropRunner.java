package com.rookies3.myspringbootlab.runner;

import com.rookies3.myspringbootlab.config.MyEnvironment;
import com.rookies3.myspringbootlab.property.MyPropProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyPropRunner implements ApplicationRunner {
    @Value("${my.prop.username}")
    private String username;

    @Value("${my.prop.port}")
    private int port;

    @Autowired
    MyEnvironment myEnvironment;

    @Autowired
    MyPropProperties properties;

    private Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception{
        System.out.println("현재 환경: "+ myEnvironment.getMode());
        log.info("My name is {}", username);
        log.info("My port = {}",port);

        System.out.println("Logger 구현체: "+logger.getClass().getName());

        log.debug("MyPropProperties getUsername: {}",properties.getUsername());
        log.debug("MyPropProperties getPort: {}", properties.getPort());
    }
}
