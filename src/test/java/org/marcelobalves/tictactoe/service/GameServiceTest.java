package org.marcelobalves.tictactoe.service;

import org.junit.jupiter.api.Test;
import org.marcelobalves.tictactoe.api.MakeMoveRequestDTO;
import org.marcelobalves.tictactoe.domain.Game;
import org.marcelobalves.tictactoe.domain.enums.PlayerEnum;
import org.marcelobalves.tictactoe.domain.enums.StatusEnum;
import org.marcelobalves.tictactoe.repository.GameRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * Service layer test
 * All dependencies should be mocked - you could use Mockito for this
 * This test should not need any Spring annotations or Spring context brought up for it to run
 */
@SpringBootTest
class GameServiceTest {

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GameService gameService;

    private final UUID SOME_UUID = UUID.randomUUID();
    private final String EMPTY_BOARD = "         ";
    private final String BOARD_1_MOVE = "1        ";
    private final Integer ZERO = 0;

    @Test
    void createGame_success() {
        Game theGame = createEmptyGame();
        when(gameRepository.save(any())).thenReturn(theGame);

        assertEquals(theGame, gameService.createGame());
    }

    @Test
    void getGame_success() {
        Optional<Game> theGame = Optional.of(createEmptyGame());
        when(gameRepository.findById(any())).thenReturn(theGame);

        assertEquals(theGame, gameService.getGame(SOME_UUID));
    }

    @Test
    void getGame_not_found() {
        Optional<Game> theGame = Optional.empty();
        when(gameRepository.findById(any())).thenReturn(theGame);

        assertEquals(theGame, gameService.getGame(SOME_UUID));
    }

    @Test
    void validateMove_firstMove_success() {
        Game theGame = createEmptyGame();
        MakeMoveRequestDTO move = makeFirstMove();
        assertEquals("", gameService.validateMove(theGame,move));
    }

    @Test
    void validateMove_empty_game() {
        MakeMoveRequestDTO move = makeFirstMove();
        assertEquals("Game not found", gameService.validateMove(null,move));
    }

    @Test
    void validateMove_empty_move() {
        Game theGame = createEmptyGame();
        assertEquals("Move is empty", gameService.validateMove(theGame,null));
    }

    @Test
    void validateMove_finished_game() {
        Game theGame = createEmptyGame();
        theGame.setStatus(StatusEnum.FINISHED.getValue());
        MakeMoveRequestDTO move = makeFirstMove();
        assertEquals("Game was finished", gameService.validateMove(theGame,move));
    }

    @Test
    void validateMove_empty_player() {
        Game theGame = createEmptyGame();
        MakeMoveRequestDTO move = new MakeMoveRequestDTO();
        assertEquals("Player is empty", gameService.validateMove(theGame,move));
    }

    @Test
    void validateMove_invalid_player() {
        Game theGame = createEmptyGame();
        MakeMoveRequestDTO move = new MakeMoveRequestDTO();
        move.setPlayer(9);
        assertEquals("Player is invalid", gameService.validateMove(theGame,move));
    }

    @Test
    void validateMove_empty_row() {
        Game theGame = createEmptyGame();
        MakeMoveRequestDTO move = new MakeMoveRequestDTO();
        move.setPlayer(PlayerEnum.NOUGTH.getValue());
        move.setRow(null);
        move.setCol(null);
        assertEquals("Destination Row is empty", gameService.validateMove(theGame,move));
    }

    @Test
    void validateMove_empty_col() {
        Game theGame = createEmptyGame();
        MakeMoveRequestDTO move = new MakeMoveRequestDTO();
        move.setPlayer(PlayerEnum.NOUGTH.getValue());
        move.setRow(0);
        move.setCol(null);
        assertEquals("Destination Col is empty", gameService.validateMove(theGame,move));
    }

    @Test
    void validateMove_wrong_player_turn() {
        Game theGame = createEmptyGame();
        theGame.setNextPlayer(PlayerEnum.NOUGTH.getValue());
        MakeMoveRequestDTO move = makeFirstMove();
        assertEquals("Wrong player turn", gameService.validateMove(theGame,move));
    }

    @Test
    void validateMove_destination_used() {
        Game theGame = createEmptyGame();
        theGame.setNextPlayer(PlayerEnum.CROSS.getValue());
        theGame.setBoardGame(BOARD_1_MOVE);
        MakeMoveRequestDTO move = makeFirstMove();
        assertEquals("Destination is already used", gameService.validateMove(theGame,move));
    }

    @Test
    void makeMove_firstMove_success() {
        Game theGame = createEmptyGame();
        MakeMoveRequestDTO move = makeFirstMove();

        Game postGame = getGameAfterFirstMove();
        when(gameRepository.save(any())).thenReturn(getGameAfterFirstMove());

        Game gameSaved = gameService.makeMove(theGame,move);
        assertEquals(postGame.getId(), gameSaved.getId());
        assertEquals(postGame.getNextPlayer(), gameSaved.getNextPlayer());
        assertEquals(postGame.getStatus(), gameSaved.getStatus());
        assertEquals(postGame.getBoardGame(), gameSaved.getBoardGame());
    }

    private Game createEmptyGame() {
        Game game = new Game();
        game.setId(SOME_UUID);
        game.setBoardGame(EMPTY_BOARD);
        game.setStatus(StatusEnum.NEW.getValue());
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
}
