package com.dorireuv.tablegame.game;

import com.dorireuv.tablegame.game.GameStatus;
import com.dorireuv.tablegame.game.Player;
import com.dorireuv.tablegame.game.Table;
import com.dorireuv.tablegame.game.TableGame;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TableGameTest {

    private TableGame tableGame;

    @Before
    public void setUp() {
        tableGame = new TableGame(new Table(10, 20, 30));
    }

    @Test
    public void getCurrentPlayer_OnStartup_ReturnsPlayerOne() {
        assertThat(tableGame.getCurrentPlayer(), is(equalTo(Player.ONE)));
    }

    @Test
    public void getCurrentPlayer_AfterMoveCoin_ReturnsPlayerTwo() throws Exception {
        tableGame.moveCoin(10, 8);
        assertThat(tableGame.getCurrentPlayer(), is(equalTo(Player.TWO)));
    }

    @Test
    public void getCurrentPlayer_AfterDropLeftMostCoin_ReturnsPlayerTwo() throws Exception {
        tableGame.dropLeftMostCoin();
        assertThat(tableGame.getCurrentPlayer(), is(equalTo(Player.TWO)));
    }

    @Test
    public void getCurrentPlayer_AfterMoveCoinTwice_ReturnsPlayerOne() throws Exception {
        tableGame.moveCoin(10, 8);
        tableGame.moveCoin(8, 6);
        assertThat(tableGame.getCurrentPlayer(), is(equalTo(Player.ONE)));
    }

    @Test
    public void getCurrentPlayer_AfterDropLeftMostCoinTwice_ReturnsPlayerOne() throws Exception {
        tableGame.dropLeftMostCoin();
        tableGame.dropLeftMostCoin();
        assertThat(tableGame.getCurrentPlayer(), is(equalTo(Player.ONE)));
    }

    @Test
    public void getGameStatus_OnStartup_ReturnsInPlay() throws Exception {
        assertThat(tableGame.getGameStatus(), is(equalTo(GameStatus.IN_PLAY)));
    }

    @Test
    public void getGameStatus_NoCoinsLeft_ReturnsEnded() throws Exception {
        tableGame = new TableGame(new Table());
        assertThat(tableGame.getGameStatus(), is(equalTo(GameStatus.ENDED)));
    }

    @Test
    public void getWinner_GameEnded_ReturnsTheLastPlayedPlayer() {
        tableGame.dropLeftMostCoin();
        tableGame.dropLeftMostCoin();
        tableGame.dropLeftMostCoin();
        assertThat(tableGame.getWinner(), is(equalTo(Player.ONE)));
    }

    @Test(expected = TableGame.GameStillInPlayException.class)
    public void getWinner_GameInPlay_ThrowsGameStillInPlayException() {
        tableGame.getWinner();
    }
}
