package com.dorireuv.tablegame.game;

import com.dorireuv.tablegame.game.generator.RandomCoinsPositionGenerator;
import com.dorireuv.tablegame.input.ConsoleInputReader;
import com.dorireuv.tablegame.input.InputReader;
import com.dorireuv.tablegame.render.ConsoleRenderer;
import com.dorireuv.tablegame.render.Renderer;

public class TableGamePlayBuilder {

    private final InputReader inputReader;
    private final Renderer renderer;
    private int numOfCoins;
    private int maxPosition;

    public TableGamePlayBuilder() {
        this(new ConsoleInputReader(), new ConsoleRenderer());
    }

    public TableGamePlayBuilder(InputReader inputReader, Renderer renderer) {
        this.inputReader = inputReader;
        this.renderer = renderer;
        numOfCoins = 2;
        maxPosition = 30;
    }

    public TableGamePlayBuilder withNumOfCoins(int numOfCoins) {
        this.numOfCoins = numOfCoins;
        return this;
    }

    public TableGamePlayBuilder withMaxPosition(int maxPosition) {
        this.maxPosition = maxPosition;
        return this;
    }

    private TableGame createTableGame() {
        return new TableGame(new Table(new RandomCoinsPositionGenerator().generate(numOfCoins, maxPosition)));
    }

    public TableGamePlay build() {
        TableGame tableGame = createTableGame();
        TableGamePlay tableGamePlay = new TableGamePlay(tableGame, inputReader, renderer);
        return tableGamePlay;
    }
}