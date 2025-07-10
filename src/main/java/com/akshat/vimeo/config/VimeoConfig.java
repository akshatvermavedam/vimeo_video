package com.akshat.vimeo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.clickntap.vimeo.Vimeo;

@Configuration
public class VimeoConfig {

    @Value("${vimeo.access-token}")
    private String accessToken;

    @Bean
    public Vimeo vimeoClient() {

        return new Vimeo(accessToken); // bearer token
    }
}
