package com.fuuzii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * Spring Boot 引导类。
 *
 * @author yinlei
 * @since 2017/11/9 15:01
 */
// 目前没有使用MongoDB，但是一些封装代码对MongoDB进行了封装，引入了驱动jar，这里排除掉
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
