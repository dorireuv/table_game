package com.dorireuv.tablegame.render;

import com.dorireuv.tablegame.game.Player;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConsoleRenderer implements Renderer {

    private final PrintStream ps;

    public ConsoleRenderer() {
        this(System.out);
    }

    public ConsoleRenderer(PrintStream ps) {
        this.ps = ps;
    }

    @Override
    public void renderPlayer(final Player player) {
        ps.println(String.format("Current player: %s", player.name()));
        ps.println();
    }

    @Override
    public void renderCoins(final Collection<Integer> coinsPosition, final Integer max) {
        final Integer maxNumOfDigits = max.toString().length();
        ps.println(buildNumbersLine(max, maxNumOfDigits));
        ps.println(buildCoinsLine(coinsPosition, max, maxNumOfDigits));
    }

    @Override
    public void renderWinner(Player winner) {
        ps.println(String.format("Winner: %s", winner.name()));
    }

    @Override
    public void renderError(String message) {
        ps.println(String.format("Error: %s", message));
    }

    private String buildCoinsLine(final Collection<Integer> coinsPosition, Integer max, final Integer maxNumOfDigits) {
        return Joiner.on(' ').join(
            Collections2.transform(Lists.newArrayList(new IntRangeIterator(max)), new Function<Integer, String>() {
                @Override
                public String apply(Integer i) {
                    String coin;
                    if (coinsPosition.contains(i)) {
                        coin = "*";
                    } else {
                        coin = " ";
                    }
                    return Strings.padStart(coin, maxNumOfDigits, ' ');
                }
            })
        );
    }

    private String buildNumbersLine(Integer max, final Integer maxNumOfDigits) {
        return Joiner.on(' ').join(
            Collections2.transform(Lists.newArrayList(new IntRangeIterator(max)), new Function<Integer, String>() {
                @Override
                public String apply(Integer i) {
                    return Strings.padStart(i.toString(), maxNumOfDigits, ' ');
                }
            })
        );
    }

    private static class IntRangeIterator implements Iterator<Integer> {

        private final int to;
        private int nextValue;

        public IntRangeIterator(int to) {
            this.nextValue = 1;
            this.to = to;
        }

        public boolean hasNext() {
            return nextValue <= to;
        }

        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return Integer.valueOf(nextValue++);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
