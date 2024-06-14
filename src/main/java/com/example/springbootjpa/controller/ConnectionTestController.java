package com.example.springbootjpa.controller;

import com.example.springbootjpa.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLTransientConnectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class ConnectionTestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookService bookService;
    @Autowired
    private ServerProperties serverProperties;

    @GetMapping("/test-connections")
    public String testConnections(@RequestParam("connectionNumber") int connectionNumber) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < connectionNumber; i++) {
            int finalI = i + 1;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                log.info("Start connection.........");
//                        jdbcTemplate.queryForObject("SELECT " + finalI + " FROM dual", Integer.class);
//                        jdbcTemplate.queryForObject("SELECT pg_sleep(10)", Integer.class);
//                        bookService.findAll();
                        bookService.selectSleep(10);
                        log.info("Message: {}", finalI);
                    }
            );

            futures.add(future);
        }

        return "Connections tested.";
    }
}