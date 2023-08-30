package com.lito.core.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ChatGPTConfig {

    @Value("${chat-gpt.api-key}")
    private String apiKey;

    @Bean
    public OpenAiService openAiService(){
        return new OpenAiService(apiKey, Duration.ofSeconds(60));
    }

}
