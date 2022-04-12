package org.marcelobalves.tictactoe.api;

/**
 * Move Request Dto
 *
 * Dto to send data to make a Move
 */
public class MakeMoveRequestDTO {

    private Integer player;
    private Integer row;
    private Integer col;

    public Integer getPlayer() {
        return player;
    }

    public void setPlayer(Integer player) {
        this.player = player;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }
}
