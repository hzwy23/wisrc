package com.wisrc.wms.basic;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicConfig {
    @Bean
    public Gson getGson() {
        return new Gson();
    }
}
