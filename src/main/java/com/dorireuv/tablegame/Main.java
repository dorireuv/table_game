package com.dorireuv.tablegame;

import com.dorireuv.tablegame.game.TableGamePlayBuilder;

public class Main {
    public static void main(String[] args) throws Exception {
        new TableGamePlayBuilder().buildDefault().start();
    }
}
