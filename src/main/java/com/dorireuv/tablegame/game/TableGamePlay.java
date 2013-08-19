package com.dorireuv.tablegame.game;

import com.dorireuv.tablegame.input.InputReader;
import com.dorireuv.tablegame.input.Move;
import com.dorireuv.tablegame.render.Renderer;

public class TableGamePlay {

    private final Renderer renderer;
    private final InputReader inputReader;
    private final TableGame tableGame;

    public TableGamePlay(TableGame tableGame, InputReader inputReader, Renderer renderer) {
        this.tableGame = tableGame;
        this.inputReader = inputReader;
        this.renderer = renderer;
    }

    public void start() {
        while (tableGame.getGameStatus() != GameStatus.ENDED) {
            displayCurrentPlayer();
            displayCoinsPosition();
            doMove();
        }

        displayCoinsPosition();
        displayWinner();
    }

    private void displayCurrentPlayer() {
        renderer.renderPlayer(tableGame.getCurrentPlayer());
    }

    private void displayCoinsPosition() {
        renderer.renderCoins(tableGame.getCoinsPosition(), tableGame.getRightMostCoinPosition());
    }

    private void displayWinner() {
        renderer.renderWinner(tableGame.getWinner());
    }

    private void doMove() {
        while (true) {
            try {
                tryToDoMove();
                break;
            } catch (Table.MoveCoinException e) {
                renderer.renderError(e.getMessage());
            }
        }
    }

    private void tryToDoMove() throws Table.MoveCoinException {
        Move move = inputReader.readMove();
        if (move.from == 0 && move.to == 0) {
            tableGame.dropLeftMostCoin();
        } else {
            tableGame.moveCoin(move.from, move.to);
        }
    }
}
