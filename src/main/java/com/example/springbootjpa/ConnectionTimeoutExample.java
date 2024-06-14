package com.example.springbootjpa;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTimeoutExample {
    public static void main(String[] args) throws SQLException, InterruptedException {
        // Configure HikariCP connection pool
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(2); // Set maximum pool size to 2 (for demonstration purposes)
        config.setConnectionTimeout(30000);// Set connection timeout to 30 seconds
        HikariDataSource dataSource = new HikariDataSource(config);
        dataSource.setUsername("root");
        dataSource.setPassword("P@ssW0rd");
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/my-db");

        // Simulate high load with connection requests exceeding the pool size
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    // Request a connection from the pool
                    Connection connection = dataSource.getConnection();
                    // Simulate a delay or long-running operation
                    Thread.sleep(40000); // Delay longer than the connection timeout
                    // Release the connection back to the pool
                    connection.close();
                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // Allow some time for the connections to be requested and timeouts to occur
        Thread.sleep(5000);

        // Shutdown the connection pool
        dataSource.close();
    }
}