package com.mu.config;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : MUZUKI
 * @date : 2023-04-02 18:36
 **/

@Data
@Configuration
public class MeiliSearchAutoConfiguration {

    @Value("${meilisearch.host-url}")
    private String hostUrl;
    @Value("${meilisearch.api-key}")
    private String apiKey;

    @Bean
    @ConditionalOnMissingBean(Client.class)
    Client client() {
        return new Client(config());
    }

    @Bean
    @ConditionalOnMissingBean(Config.class)
    Config config() {
        return new Config(hostUrl, apiKey);
    }
}

