package org.marcelobalves.tictactoe.service;

import org.marcelobalves.tictactoe.api.MakeMoveRequestDTO;
import org.marcelobalves.tictactoe.domain.Game;
import org.marcelobalves.tictactoe.domain.enums.PlayerEnum;
import org.marcelobalves.tictactoe.domain.enums.StatusEnum;
import org.marcelobalves.tictactoe.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public Game createGame() {
        Game game = new Game();
        game.setStatus(StatusEnum.NEW.getValue());
        return gameRepository.save(game);
    }

    public Optional<Game> getGame(UUID gameId) {
        return gameRepository.findById(gameId);
    }

    public String validateMove(Game game, MakeMoveRequestDTO moveRequest) {

        if (game == null){
            return "Game not found";
        }
        if (game.getStatus().equals(StatusEnum.FINISHED.getValue())) {
            return "Game was finished";
        }
        if(moveRequest == null){
            return "Move is empty";
        }
        if(moveRequest.getPlayer() == null){
            return "Player is empty";
        }
        if(moveRequest.getPlayer() > Game.MAX_PLAYERS - 1){
            return "Player is invalid";
        }
        if(moveRequest.getRow() == null){
            return "Destination Row is empty";
        }
        if(moveRequest.getRow() > Game.BOARD_ROWS - 1){
            return "Destination Row is invalid";
        }
        if(moveRequest.getCol() == null){
            return "Destination Col is empty";
        }
        if(moveRequest.getCol() > Game.BOARD_COLS - 1){
            return "Destination Col is invalid";
        }
        if (game.getNextPlayer() != null && !game.getNextPlayer().equals(moveRequest.getPlayer())){
            return "Wrong player turn";
        }
        if (game.getPosition(moveRequest.getRow(),moveRequest.getCol()) != null){
            return "Destination is already used";
        }
        return "";
    }

    public Game makeMove(Game game, MakeMoveRequestDTO moveRequest) {

        game.setPosition(moveRequest.getRow(), moveRequest.getCol(), moveRequest.getPlayer());
        Integer winner = calculateWinner(game.getBoardGame());
        if (winner!=null){
            game.setStatus(StatusEnum.FINISHED.getValue());
            game.setNextPlayer(null);
            game.setWinner(winner);
        } else if (game.isBoardFull()) {
            game.setStatus(StatusEnum.FINISHED.getValue());
            game.setNextPlayer(null);
        } else {
            game.setStatus(StatusEnum.RUNNING.getValue());
            game.setNextPlayer(PlayerEnum.next(moveRequest.getPlayer()));
        }
        return gameRepository.save(game);

    }

    public Integer calculateWinner(String boardGame) {
        Integer[][] board = toMatrix(boardGame);
        Integer[] sub = new Integer[3];

        for (int row = 0; row < Game.BOARD_ROWS; row++) {
            sub[0] = board[row][0];
            sub[1] = board[row][1];
            sub[2] = board[row][2];
            if (isEqualNumbers(sub)) return sub[0];
        }
        for (int col = 0; col < Game.BOARD_COLS; col++) {
            sub[0] = board[0][col];
            sub[1] = board[1][col];
            sub[2] = board[2][col];
            if (isEqualNumbers(sub)) return sub[0];
        }

        sub[0] = board[0][0];
        sub[1] = board[1][1];
        sub[2] = board[2][2];
        if (isEqualNumbers(sub)) return sub[0];

        sub[0] = board[0][2];
        sub[1] = board[1][1];
        sub[2] = board[2][0];
        if (isEqualNumbers(sub)) return sub[0];

        return null;
    }

    private boolean isEqualNumbers(Integer[] num) {
        Integer first = num[0];
        if (first != null){
             for(int i=1; i<num.length;i++){
                 if (num[i] == null || !num[i].equals(first)){
                     return false;
                 }
             }
             return true;
        }
        return false;
    }

    private Integer[][] toMatrix(String boardRepo){
        Integer[][] board = new Integer[3][3];
        for(int i=0;i<boardRepo.length();i++){
            board[i / 3][i % 3] = (boardRepo.charAt(i) == ' ') ? null : Character.getNumericValue(boardRepo.charAt(i));
        }
        return board;
    }

}
