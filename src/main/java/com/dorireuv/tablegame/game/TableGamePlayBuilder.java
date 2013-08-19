package com.dorireuv.tablegame.game;

import com.dorireuv.tablegame.game.generator.RandomCoinsPositionGenerator;
import com.dorireuv.tablegame.input.AlternativeConsoleInputReader;
import com.dorireuv.tablegame.input.ConsoleInputReader;
import com.dorireuv.tablegame.input.InputReader;
import com.dorireuv.tablegame.render.ConsoleRenderer;
import com.dorireuv.tablegame.render.Renderer;

public class TableGamePlayBuilder {
    public TableGamePlay buildDefault() {
        Integer[] coinsPosition = new RandomCoinsPositionGenerator().generate(2, 30);
        TableGame tableGame = new TableGame(new Table(coinsPosition));
        InputReader inputReader = new ConsoleInputReader(System.out, System.in);
        Renderer renderer = new ConsoleRenderer(System.out);
        TableGamePlay tableGamePlay = new TableGamePlay(tableGame, inputReader, renderer);
        return tableGamePlay;
    }
}
