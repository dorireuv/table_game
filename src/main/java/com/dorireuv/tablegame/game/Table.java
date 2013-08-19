package com.dorireuv.tablegame.game;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.*;

/**
 * Table implementation with:
 *  - Construction - O(n*log(n))
 *  - isCoinOnPosition - O(1)
 *  - moveCoin - O(1)
 *  - getNumOfCoins - O(1)
 *  - dropLeftMostCoin - O(1)
 *
 */
class Table {

    private final List<Integer> sortedCoinPositions;
    private final Map<Integer, Integer> positionToIndexMapping;
    private int leftMostCoinIndex;
    private final int rightMostCoinPosition;

    public Table(Integer... coinPositions) {
        sortedCoinPositions = Arrays.asList(coinPositions);
        Collections.sort(this.sortedCoinPositions);

        positionToIndexMapping = new HashMap<Integer, Integer>();
        int i = 0;
        for (Integer coinPosition : sortedCoinPositions) {
            positionToIndexMapping.put(coinPosition, i);
            i++;
        }

        leftMostCoinIndex = 0;

        if (sortedCoinPositions.isEmpty()) {
            rightMostCoinPosition = 0;
        } else {
            rightMostCoinPosition = sortedCoinPositions.get(sortedCoinPositions.size() - 1);
        }
    }

    public boolean isCoinOnPosition(int position) {
        return positionToIndexMapping.containsKey(position);
    }

    public void moveCoin(int from, int to) throws
        Table.NoCoinOnPositionException,
        Table.InvalidFromPositionException,
        Table.InvalidToPositionException,
        Table.MoveCoinToNoneEmptyPositionException,
        Table.CannotMoveBackwardsException,
        Table.CannotMoveOverAnotherCoinException
    {
        validateMove(from, to);
        ensureCoinOnPosition(from);
        ensureNoCoinOnPosition(to);
        ensureCoinDoesNotMoveOverAnotherCoin(from, to);
        doMoveCoin(from, to);
    }

    private void doMoveCoin(int from, int to) {
        Integer fromIndex = positionToIndexMapping.get(from);
        sortedCoinPositions.set(fromIndex, to);
        positionToIndexMapping.remove(from);
        positionToIndexMapping.put(to, fromIndex);
    }

    private void validateMove(int from, int to) throws
        CannotMoveBackwardsException,
        InvalidFromPositionException,
        InvalidToPositionException
    {
        if (from <= 0) {
            throw new InvalidFromPositionException(String.format("Invalid from position: %d", from));
        }

        if (to <= 0) {
            throw new InvalidToPositionException(String.format("Invalid to position: %d", to));
        }

        if (to > from) {
            throw new CannotMoveBackwardsException(String.format("Cannot move coin backwards"));
        }
    }

    private void ensureCoinOnPosition(int from) throws NoCoinOnPositionException {
        if (positionToIndexMapping.get(from) == null) {
            throw new NoCoinOnPositionException(String.format("No coin in origin: %d", from));
        }
    }

    private void ensureNoCoinOnPosition(int to) throws MoveCoinToNoneEmptyPositionException {
        if (positionToIndexMapping.get(to) != null) {
            throw new MoveCoinToNoneEmptyPositionException(String.format("There is a coin in target: %d", to));
        }
    }

    private void ensureCoinDoesNotMoveOverAnotherCoin(int from, int to) throws CannotMoveOverAnotherCoinException {
        Integer fromIndex = positionToIndexMapping.get(from);
        if (fromIndex <= 0) {
            return;
        }

        int positionOfCoinBefore = sortedCoinPositions.get(fromIndex - 1);
        if (to < positionOfCoinBefore) {
            throw new CannotMoveOverAnotherCoinException(String.format("Cannot move one coin over another coin"));
        }
    }

    public int getNumOfCoins() {
        return positionToIndexMapping.size();
    }

    public void dropLeftMostCoin() throws Table.NoCoinsLeftException {
        ensureCoinsLeft();
        int position = sortedCoinPositions.set(leftMostCoinIndex, 0);
        leftMostCoinIndex++;
        positionToIndexMapping.remove(position);
    }

    private void ensureCoinsLeft() {
        if (getNumOfCoins() == 0) {
            throw new NoCoinsLeftException();
        }
    }

    public Collection<Integer> getCoinsPosition() {
        return Lists.newArrayList(
            Collections2.filter(
                this.sortedCoinPositions,
                new Predicate<Integer>() {
                    @Override
                    public boolean apply(Integer integer) {
                        return integer > 0;
                    }
                }
            )
        );
    }

    public int getRightMostCoinPosition() {
        return rightMostCoinPosition;
    }

    public abstract static class MoveCoinException extends Exception {
        public MoveCoinException(String message) {
            super(message);
        }
    }

    public static class NoCoinOnPositionException extends MoveCoinException {
        public NoCoinOnPositionException(String message) {
            super(message);
        }
    }

    public static class MoveCoinToNoneEmptyPositionException extends MoveCoinException {
        public MoveCoinToNoneEmptyPositionException(String message) {
            super(message);
        }
    }

    public static class CannotMoveBackwardsException extends MoveCoinException {
        public CannotMoveBackwardsException(String message) {
            super(message);
        }
    }

    public static class CannotMoveOverAnotherCoinException extends MoveCoinException {
        public CannotMoveOverAnotherCoinException(String message) {
            super(message);
        }
    }

    public static class InvalidFromPositionException extends MoveCoinException {
        public InvalidFromPositionException(String message) {
            super(message);
        }
    }

    public static class InvalidToPositionException extends MoveCoinException {
        public InvalidToPositionException(String message) {
            super(message);
        }
    }

    public static class NoCoinsLeftException extends RuntimeException { }
}
