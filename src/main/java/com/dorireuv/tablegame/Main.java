package com.dorireuv.tablegame;

import com.dorireuv.tablegame.game.TableGamePlayBuilder;
import com.dorireuv.tablegame.input.AlternativeConsoleInputReader;
import com.dorireuv.tablegame.input.ConsoleInputReader;
import com.dorireuv.tablegame.input.InputReader;
import com.dorireuv.tablegame.render.ConsoleRenderer;
import com.dorireuv.tablegame.render.Renderer;

public class Main {
    public static void main(String[] args) throws Exception {
        new TableGamePlayBuilder(readInputReader(args), readRenderer())
            .withNumOfCoins(readNumOfCoins(args))
            .withMaxPosition(readMaxPosition(args))
            .build()
            .start();
    }

    private static int readNumOfCoins(String[] args) {
        return readParam(args, 0, 2);
    }

    private static int readMaxPosition(String[] args) {
        return readParam(args, 1, 30);
    }

    private static InputReader readInputReader(String[] args) {
        if (readParam(args, 2, 0) == 0) {
            return new AlternativeConsoleInputReader();
        } else {
            return new ConsoleInputReader();
        }
    }

    private static Renderer readRenderer() {
        return new ConsoleRenderer();
    }

    private static Integer readParam(String[] args, int index, int defaultValue) {
        int res = defaultValue;
        if (args.length > index) {
            try {
                res = Integer.parseInt(args[index]);
            } catch (Exception e) {

            }
        }

        return res;
    }
}
