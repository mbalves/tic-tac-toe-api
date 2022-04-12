package org.marcelobalves.tictactoe.api;

import java.util.UUID;

/**
 * Game Dto
 *
 * Dto to get Game information
 */
public class GameDTO {

    private UUID id;

    private Integer nextPlayer;
    private String boardGame;
    private Integer status;
    private Integer winner;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(Integer nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public String getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(String boardGame) {
        this.boardGame = boardGame;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }
}
