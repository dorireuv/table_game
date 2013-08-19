package com.dorireuv.tablegame.game;

import java.util.Collection;

public class TableGame {

    private final Table table;
    private Player currentPlayer;

    public TableGame(Table table) {
        this.table = table;
        this.currentPlayer = Player.ONE;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void dropLeftMostCoin() throws Table.NoCoinsLeftException {
        table.dropLeftMostCoin();
        switchCurrentPlayer();
    }

    public void moveCoin(int from, int to) throws
        Table.NoCoinOnPositionException,
        Table.InvalidToPositionException,
        Table.InvalidFromPositionException,
        Table.MoveCoinToNoneEmptyPositionException,
        Table.CannotMoveBackwardsException,
        Table.CannotMoveOverAnotherCoinException
    {
        table.moveCoin(from, to);
        switchCurrentPlayer();
    }

    private void switchCurrentPlayer() {
        if (table.getNumOfCoins() > 0) {
            if (currentPlayer == Player.ONE) {
                currentPlayer = Player.TWO;
            } else {
                currentPlayer = Player.ONE;
            }
        }
    }

    public GameStatus getGameStatus() {
        if (table.getNumOfCoins() > 0) {
            return GameStatus.IN_PLAY;
        } else {
            return GameStatus.ENDED;
        }
    }

    public Player getWinner() throws TableGame.GameStillInPlayException {
        if (getGameStatus() != GameStatus.ENDED) {
            throw new GameStillInPlayException();
        }

        return currentPlayer;
    }

    public Collection<Integer> getCoinsPosition() {
        return table.getCoinsPosition();
    }

    public int getRightMostCoinPosition() {
        return table.getRightMostCoinPosition();
    }

    public static class GameStillInPlayException extends RuntimeException {

    }
}
