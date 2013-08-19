package com.dorireuv.tablegame.render;

import com.dorireuv.tablegame.game.Player;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConsoleRendererTest {

    private String lineSeparator;
    private ConsoleRenderer consoleRenderer;
    private OutputStream outputStream;

    @Before
    public void setUp() {
        lineSeparator = System.getProperty("line.separator");
        outputStream = new ByteArrayOutputStream();
        consoleRenderer = new ConsoleRenderer(new PrintStream(outputStream));
    }

    @Test
    public void renderPlayer() {
        consoleRenderer.renderPlayer(Player.ONE);
        assertThat(outputStream.toString(), is(equalTo("Current player: ONE" + lineSeparator + lineSeparator)));
    }

    @Test
    public void renderCoins_WithCoins() {
        consoleRenderer.renderCoins(Arrays.asList(2, 3), 4);
        assertThat(
            outputStream.toString(),
            is(equalTo(
                "1 2 3 4" + lineSeparator +
                "  * *  " + lineSeparator
            ))
        );
    }

    @Test
    public void renderCoins_WithNoCoins() {
        consoleRenderer.renderCoins(new ArrayList<Integer>(), 0);
        assertThat(
            outputStream.toString(),
            is(equalTo(
                lineSeparator +
                lineSeparator
            ))
        );
    }

    @Test
    public void renderWinner() {
        consoleRenderer.renderWinner(Player.ONE);
        assertThat(outputStream.toString(), is(equalTo("Winner: ONE" + lineSeparator)));
    }

}
