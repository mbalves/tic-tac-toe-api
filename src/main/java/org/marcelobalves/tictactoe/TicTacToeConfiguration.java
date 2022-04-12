package org.marcelobalves.tictactoe;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EntityScan(basePackages = "org.marcelobalves.tictactoe.domain")
@EnableJpaRepositories(basePackages = "org.marcelobalves.tictactoe.repository")
@EnableTransactionManagement
@Configuration
public class TicTacToeConfiguration {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
}
