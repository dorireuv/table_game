package com.dorireuv.tablegame.game;

import com.dorireuv.tablegame.game.Table;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class TableTest {

    private Table table;

    @Before
    public void setUp() {
        this.table = new Table(20, 10);
    }

    @Test
    public void getCoinsPosition_OnStart_ReturnsCoinsPosition() {
        assertThat(table.getCoinsPosition(), contains(10, 20));
    }

    @Test
    public void isCoinOnPosition_OnCoinPositions_ReturnsTrue() {
        assertThat(table.isCoinOnPosition(10), is(equalTo(true)));
        assertThat(table.isCoinOnPosition(20), is(equalTo(true)));
    }

    @Test
    public void isCoinOnPosition_OnEmptyPosition_ReturnsFalse() {
        assertThat(table.isCoinOnPosition(1), is(equalTo(false)));
    }

    @Test
    public void isCoinOnPosition_OnNewCoinPositionAfterMove_ReturnsTrue() throws Exception {
        table.moveCoin(10, 8);
        assertThat(table.isCoinOnPosition(8), is(equalTo(true)));
    }

    @Test
    public void getCoinsPosition_AfterMove_ReturnsUpdatedCoinsPosition() throws Exception {
        table.moveCoin(10, 8);
        assertThat(table.getCoinsPosition(), contains(8, 20));
    }

    @Test
    public void isCoinOnPosition_OnOldCoinPositionAfterMove_ReturnsFalse() throws Exception {
        table.moveCoin(10, 8);
        assertThat(table.isCoinOnPosition(10), is(equalTo(false)));
    }

    @Test(expected = Table.NoCoinOnPositionException.class)
    public void moveCoin_OnEmptyPosition_ThrowsNoCoinOnPositionException() throws Exception {
        table.moveCoin(8, 7);
    }

    @Test(expected = Table.MoveCoinToNoneEmptyPositionException.class)
    public void moveCoin_ToAlreadyTakenPosition_ThrowsMoveCoinToNoneEmptyPositionException() throws Exception {
        table.moveCoin(20, 10);
    }

    @Test(expected = Table.CannotMoveBackwardsException.class)
    public void moveCoin_Backwards_ThrowsCannotMoveBackwardsException() throws Exception {
        table.moveCoin(20, 22);
    }

    @Test(expected = Table.MoveCoinToNoneEmptyPositionException.class)
    public void moveCoin_ToTheSamePosition_ThrowsCannotMoveBackwardsException() throws Exception {
        table.moveCoin(20, 20);
    }

    @Test(expected = Table.CannotMoveOverAnotherCoinException.class)
    public void moveCoin_OverAnotherCoin_ThrowsCannotMoveOverAnotherCoinException() throws Exception {
        table.moveCoin(20, 5);
    }

    @Test
    public void moveCoin_LeftMostCoin_NeverThrowsCannotMoveOverAnotherCoinException() throws Exception {
        table.moveCoin(10, 1);
    }

    @Test(expected = Table.InvalidFromPositionException.class)
    public void moveCoin_ToPlaceLessThanOne_ThrowsInvalidFromPositionException() throws Exception {
        table.moveCoin(0, 10);
    }

    @Test(expected = Table.InvalidToPositionException.class)
    public void moveCoin_FromPlaceLessThanOne_ThrowsInvalidToPositionException() throws Exception {
        table.moveCoin(10, 0);
    }

    @Test
    public void getNumOfCoins_AfterConstruction_ReturnsNumberOfCoins() {
        assertThat(table.getNumOfCoins(), is(equalTo(2)));
    }

    @Test
    public void getNumOfCoins_AfterDropLeftMostCoin_DecreasedByOne() throws Exception {
        table.dropLeftMostCoin();
        assertThat(table.getNumOfCoins(), is(equalTo(1)));
    }

    @Test
    public void getCoinsPosition_AfterDropLeftMostCoin_DoesNotReturnTheDroppedCoin() throws Exception {
        table.dropLeftMostCoin();
        assertThat(table.getCoinsPosition(), contains(20));
    }

    @Test
    public void isCoinOnPosition_AfterDropLeftMostCoin_ReturnsFalse() throws Exception {
        table.dropLeftMostCoin();
        assertThat(table.isCoinOnPosition(10), is(equalTo(false)));
        table.dropLeftMostCoin();
        assertThat(table.isCoinOnPosition(20), is(equalTo(false)));
    }

    @Test(expected = Table.NoCoinsLeftException.class)
    public void dropLeftMostCoin_NoCoinsLeft_ThrowsNoCoinsLeftException() throws Exception {
        table.dropLeftMostCoin();
        table.dropLeftMostCoin();
        table.dropLeftMostCoin();
    }

    @Test
    public void getRightMostCoinPosition_WithNoCoins_ReturnsZero() throws Exception {
        table = new Table();
        assertThat(table.getRightMostCoinPosition(), is(equalTo(0)));
    }
}