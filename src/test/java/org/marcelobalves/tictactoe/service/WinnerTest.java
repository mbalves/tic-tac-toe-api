package org.marcelobalves.tictactoe.service;

import org.junit.jupiter.api.Test;
import org.marcelobalves.tictactoe.domain.enums.PlayerEnum;

import static org.junit.Assert.assertEquals;

class WinnerTest {

    @Test
    void calculateWinner() {
        GameService gameService = new GameService();

        assertEquals(PlayerEnum.NOUGTH.getValue(),gameService.calculateWinner("000      "));
        assertEquals(PlayerEnum.NOUGTH.getValue(),gameService.calculateWinner("   000   "));
        assertEquals(PlayerEnum.NOUGTH.getValue(),gameService.calculateWinner("      000"));
        assertEquals(PlayerEnum.NOUGTH.getValue(),gameService.calculateWinner("0  0  0  "));
        assertEquals(PlayerEnum.NOUGTH.getValue(),gameService.calculateWinner(" 0  0  0 "));
        assertEquals(PlayerEnum.NOUGTH.getValue(),gameService.calculateWinner("  0  0  0"));
        assertEquals(PlayerEnum.NOUGTH.getValue(),gameService.calculateWinner("0   0   0"));
        assertEquals(PlayerEnum.NOUGTH.getValue(),gameService.calculateWinner("  0 0 0  "));

        assertEquals(PlayerEnum.CROSS.getValue(),gameService.calculateWinner("111      "));
        assertEquals(PlayerEnum.CROSS.getValue(),gameService.calculateWinner("   111   "));
        assertEquals(PlayerEnum.CROSS.getValue(),gameService.calculateWinner("      111"));
        assertEquals(PlayerEnum.CROSS.getValue(),gameService.calculateWinner("1  1  1  "));
        assertEquals(PlayerEnum.CROSS.getValue(),gameService.calculateWinner(" 1  1  1 "));
        assertEquals(PlayerEnum.CROSS.getValue(),gameService.calculateWinner("  1  1  1"));
        assertEquals(PlayerEnum.CROSS.getValue(),gameService.calculateWinner("1   1   1"));
        assertEquals(PlayerEnum.CROSS.getValue(),gameService.calculateWinner("  1 1 1  "));

        assertEquals(PlayerEnum.NOUGTH.getValue(),gameService.calculateWinner("01 0 10  "));

    }
}
