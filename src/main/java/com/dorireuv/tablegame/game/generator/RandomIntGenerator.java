package com.dorireuv.tablegame.game.generator;

import java.util.Random;

class RandomIntGenerator {
    private final Random random;

    public RandomIntGenerator(Random random) {
        this.random = random;
    }

    public int nextInt(int max) {
        return random.nextInt(max - 1) + 1;
    }
}
