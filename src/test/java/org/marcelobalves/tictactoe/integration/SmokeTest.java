package org.marcelobalves.tictactoe.integration;

import org.junit.jupiter.api.Test;
import org.marcelobalves.tictactoe.controller.GamesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SmokeTest {

    @Autowired
    private GamesController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
