package com.dorireuv.tablegame.game;

import com.dorireuv.tablegame.game.Player;
import com.dorireuv.tablegame.game.Table;
import com.dorireuv.tablegame.game.TableGame;
import com.dorireuv.tablegame.game.TableGamePlay;
import com.dorireuv.tablegame.input.InputReader;
import com.dorireuv.tablegame.input.Move;
import com.dorireuv.tablegame.render.Renderer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;

public class TableGamePlayTest {

    private TableGamePlay tableGamePlay;
    private InputReader inputReader;
    private Renderer renderer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        TableGame tableGame = new TableGame(new Table(10, 20));
        inputReader = mock(InputReader.class);
        renderer = mock(Renderer.class);
        tableGamePlay = new TableGamePlay(tableGame, inputReader, renderer);
    }

    @Test
    public void happyPath() {
        when(inputReader.readMove()).thenReturn(new Move(0, 0), new Move(0, 0));
        tableGamePlay.start();

        InOrder inOrder = inOrder(inputReader, renderer);
        inOrder.verify(renderer).renderPlayer(Player.ONE);
        inOrder.verify(renderer).renderCoins(Arrays.asList(10, 20), 20);
        inOrder.verify(inputReader).readMove();

        inOrder.verify(renderer).renderPlayer(Player.TWO);
        inOrder.verify(renderer).renderCoins(Arrays.asList(20), 20);
        inOrder.verify(inputReader).readMove();

        inOrder.verify(renderer).renderCoins(Collections.<Integer>emptyList(), 20);
        inOrder.verify(renderer).renderWinner(Player.TWO);
    }
}
