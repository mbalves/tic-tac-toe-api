package org.marcelobalves.tictactoe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.marcelobalves.tictactoe.api.MakeMoveRequestDTO;
import org.marcelobalves.tictactoe.domain.Game;
import org.marcelobalves.tictactoe.domain.enums.PlayerEnum;
import org.marcelobalves.tictactoe.domain.enums.StatusEnum;
import org.marcelobalves.tictactoe.service.GameService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Test for Controller
 * Only the web layer is tested, the dependent service layer is mocked
 */
@WebMvcTest(controllers = GamesController.class)
class GamesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gamesService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID SOME_UUID = UUID.randomUUID();
    private final String EMPTY_BOARD = "         ";
    private final String BOARD_1_MOVE = "1        ";

    @Test
    void createGame_success() throws Exception {

        Mockito.when(gamesService.createGame()).thenReturn(createEmptyGame());

        mockMvc.perform(MockMvcRequestBuilders.post("/games")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(SOME_UUID.toString()))
                .andExpect(jsonPath("$.boardGame").value(EMPTY_BOARD));
    }

    @Test
    void makeMove_success() throws Exception {

        Optional<Game> theGame = Optional.of(createEmptyGame());
        Mockito.when(gamesService.getGame(any())).thenReturn(theGame);

        Mockito.when(gamesService.validateMove(any(),any())).thenReturn("");

        Game gameChanged = getGameAfterFirstMove();
        Mockito.when(gamesService.makeMove(any(),any())).thenReturn(gameChanged);

        MakeMoveRequestDTO move = makeFirstMove();

        mockMvc.perform(MockMvcRequestBuilders.post("/games/"+SOME_UUID+"/moves")
                .content(objectMapper.writeValueAsString(move))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(SOME_UUID.toString()))
                .andExpect(jsonPath("$.nextPlayer").value(gameChanged.getNextPlayer()))
                .andExpect(jsonPath("$.status").value(gameChanged.getStatus()))
                .andExpect(jsonPath("$.boardGame").value(gameChanged.getBoardGame()));

    }

    @Test
    void makeMove_gameId_invalid() throws Exception {

        Mockito.when(gamesService.getGame(any())).thenReturn(Optional.empty());

        MakeMoveRequestDTO move = makeFirstMove();

        mockMvc.perform(MockMvcRequestBuilders.post("/games/"+SOME_UUID+"/moves")
                .content(objectMapper.writeValueAsString(move))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$").value("FATAL ERROR: Game Id Not Found"));

    }

    @Test
    void makeMove_invalid_move() throws Exception {

        Optional<Game> theGame = Optional.of(createEmptyGame());
        Mockito.when(gamesService.getGame(any())).thenReturn(theGame);

        Mockito.when(gamesService.validateMove(any(),any())).thenReturn("Player is empty");

        MakeMoveRequestDTO move = makeFirstMove();

        mockMvc.perform(MockMvcRequestBuilders.post("/games/"+SOME_UUID+"/moves")
                .content(objectMapper.writeValueAsString(move))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$").value("INVALID MOVE: Player is empty"));

    }

    private Game createEmptyGame() {
        Game game = new Game();
        game.setId(SOME_UUID);
        game.setBoardGame(EMPTY_BOARD);
        return game;
    }

    private MakeMoveRequestDTO makeFirstMove() {
        MakeMoveRequestDTO move = new MakeMoveRequestDTO();
        move.setPlayer(PlayerEnum.CROSS.getValue());
        move.setRow(0);
        move.setCol(0);
        return move;
    }

    private Game getGameAfterFirstMove() {
        Game game = new Game();
        game.setId(SOME_UUID);
        game.setBoardGame(BOARD_1_MOVE);
        game.setNextPlayer(PlayerEnum.NOUGTH.getValue());
        game.setStatus(StatusEnum.RUNNING.getValue());
        return game;
    }

    // TODO: Implement any other test cases you may need to cover, or add a comment for each one if you run out of time

}
