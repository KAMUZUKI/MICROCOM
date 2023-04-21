package com.mu.meilisearch.config;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : MUZUKI
 * @date : 2023-04-02 18:36
 **/

@Data
@Configuration
@ConfigurationProperties(prefix = "meilisearch")
public class MeiliSearchAutoConfiguration {

    @Value("${hosturl}")
    private String hostUrl;
    @Value("${apikey}")
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

