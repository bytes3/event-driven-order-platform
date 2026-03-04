package com.bytes.services.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class FlywayConfig {

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy(ApplicationArguments args) {
        return flyway -> {
            boolean reset = args.containsOption("flywayReset");

            if (reset) {
                System.out.println("Flyway reset requested: cleaning DB...");
                flyway.clean();
            }

            flyway.migrate();
        };
    }
}