package com.dorireuv.tablegame.game.generator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomCoinsPositionGenerator {

    private final RandomIntGenerator randomIntGenerator;

    public RandomCoinsPositionGenerator() {
        this(new RandomIntGenerator(new Random(System.currentTimeMillis())));
    }

    private RandomCoinsPositionGenerator(RandomIntGenerator randomIntGenerator) {
        this.randomIntGenerator = randomIntGenerator;
    }

    public Integer[] generate(int numOfCoins, int maxPosition) {
        Set<Integer> coinsPosition = new HashSet<Integer>();
        while (coinsPosition.size() < numOfCoins) {
            coinsPosition.add(randomIntGenerator.nextInt(maxPosition));
        }

        return coinsPosition.toArray(new Integer[coinsPosition.size()]);
    }
}
