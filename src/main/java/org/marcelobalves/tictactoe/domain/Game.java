package org.marcelobalves.tictactoe.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Game {

    public static final int MAX_PLAYERS = 2;
    public static final int BOARD_ROWS = 3;
    public static final int BOARD_COLS = 3;

    /**
     * Primary key
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    private Integer nextPlayer;
    private String boardGame = "         ";
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

    public Integer getPosition(Integer row, Integer col) {
        char value = this.boardGame.toCharArray()[(row*BOARD_ROWS)+col];
        return (value == ' ') ? null : Character.getNumericValue(value);
    }

    public void setPosition(Integer row, Integer col, Integer value) {
        char[] arr = this.boardGame.toCharArray();
        arr[(row*BOARD_ROWS)+col] = (value == null) ? ' ' : Character.forDigit(value,10);
        this.boardGame = new String(arr);
    }

    public boolean isBoardFull() {
        return !this.boardGame.contains(" ");
    }
}
