package com.foodtruck.pos.foodtruck_pos_v1.common.config;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class H2ServerConfig {
    @Bean
    public CommandLineRunner startH2Server() {
        log.info("# h2 server started");
        return args -> {
            Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092")
                    .start();
        };
    }
}
