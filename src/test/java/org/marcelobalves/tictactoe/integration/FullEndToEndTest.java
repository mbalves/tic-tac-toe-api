package org.marcelobalves.tictactoe.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.marcelobalves.tictactoe.api.GameDTO;
import org.marcelobalves.tictactoe.api.MakeMoveRequestDTO;
import org.marcelobalves.tictactoe.controller.GamesController;
import org.marcelobalves.tictactoe.domain.enums.PlayerEnum;
import org.marcelobalves.tictactoe.domain.enums.StatusEnum;
import org.marcelobalves.tictactoe.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Full end to end test
 *
 * Stands up the full backend
 *
 * Makes http calls to it, simulating a real person with curl or similar http tool
 * Checks the results are correct
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class FullEndToEndTest {

    @Autowired
    private GamesController controller;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private GameRepository gameRepository;

    // Note: Wiremock could be used if any external services were being communicated with - there are none for now


    @Test
    void fullEndToEndTest_happyPath() throws Exception {

        // Create game
        ResponseEntity<GameDTO> createResponse = restTemplate.postForEntity("/games", null, GameDTO.class);

        // Check game is there - and maybe check game is setup correctly?
        Assertions.assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        // Make a move
        MakeMoveRequestDTO moveDTO = new MakeMoveRequestDTO();
        moveDTO.setPlayer(PlayerEnum.NOUGTH.getValue());
        moveDTO.setRow(1);
        moveDTO.setCol(1);
        ResponseEntity<GameDTO> moveResponse = restTemplate.postForEntity("/games/"+createResponse.getBody().getId()+"/moves", moveDTO, GameDTO.class);

        Assertions.assertEquals(HttpStatus.OK, moveResponse.getStatusCode());

        // Check game state after first move

        GameDTO finalGame = moveResponse.getBody();
        Assertions.assertNotNull(finalGame);
        Assertions.assertEquals(StatusEnum.RUNNING.getValue(), finalGame.getStatus());
        Assertions.assertEquals(PlayerEnum.CROSS.getValue(), finalGame.getNextPlayer());
        Assertions.assertEquals("    0    ", finalGame.getBoardGame());


    }

}
