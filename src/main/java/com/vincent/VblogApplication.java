package com.vincent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot启动初始化类
 */
@EnableScheduling
@SpringBootApplication
public class VblogApplication {

    public static void main(String[] args) {

        // 解决elasticsearch启动保存问题
        System.setProperty("es.set.netty.runtime.available.processors", "false");

        SpringApplication.run(VblogApplication.class, args);
        System.out.println("http://localhost:8080");
    }

}
