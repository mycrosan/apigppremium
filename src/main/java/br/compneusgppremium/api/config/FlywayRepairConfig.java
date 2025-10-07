package br.compneusgppremium.api.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;

/**
 * Repairs Flyway's schema history if there is a failed migration entry
 * (e.g., "Schema contains a failed migration to version X"), then proceeds
 * with migrate().
 *
 * This avoids application startup failure when enabling Flyway on an existing
 * database where a past migration failed and was left recorded as unsuccessful
 * in flyway_schema_history.
 */
@Configuration
public class FlywayRepairConfig {

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return new FlywayMigrationStrategy() {
            @Override
            public void migrate(Flyway flyway) {
                // Attempt to repair failed entries and checksum mismatches first
                try {
                    flyway.repair();
                } catch (Exception e) {
                    // Log and continue to try migrate; Spring will log full stack
                    System.err.println("Flyway repair encountered an issue: " + e.getMessage());
                }
                // Proceed with migrations
                flyway.migrate();
            }
        };
    }
}