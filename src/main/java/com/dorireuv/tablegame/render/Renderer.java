package com.dorireuv.tablegame.render;

import com.dorireuv.tablegame.game.Player;

import java.util.Collection;

public interface Renderer {

    public void renderPlayer(final Player player);

    public void renderCoins(final Collection<Integer> coinsPosition, final Integer max);

    public void renderWinner(Player winner);

    public void renderError(String message);
}
