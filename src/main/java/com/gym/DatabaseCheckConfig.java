package com.gym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DatabaseCheckConfig {

    @Bean
    public CommandLineRunner checkConnection(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                if (connection != null && !connection.isClosed()) {
                    System.out.println("=========================================");
                    System.out.println("✅ DATABASE CONNECTED SUCCESSFULLY!");
                    System.out.println("Database Product: " + connection.getMetaData().getDatabaseProductName());
                    System.out.println("=========================================");
                }
            } catch (Exception e) {
                System.err.println("=========================================");
                System.err.println("❌ DATABASE CONNECTION FAILED!");
                System.err.println("Error: " + e.getMessage());
                System.err.println("=========================================");
            }
        };
    }
}