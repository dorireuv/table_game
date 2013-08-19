package com.dorireuv.tablegame.input;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsoleInputReaderTest {

    private OutputStream outputStream;
    private InputReader inputReader;
    private BufferedReader bufferedReader;

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        bufferedReader = mock(BufferedReader.class);
        inputReader = new ConsoleInputReader(new PrintStream(outputStream), bufferedReader);
    }

    @Test
    public void readMove_EnterValidMoves_ReturnsExpectedMove() throws Exception {
        when(bufferedReader.readLine()).thenReturn("1").thenReturn("2");
        Move move = inputReader.readMove();
        assertThat(move.from, is(equalTo(1)));
        assertThat(move.to, is(equalTo(2)));
    }

    @Test
    public void readMove_EnterInvalidMoves_Skipped() throws Exception {
        when(bufferedReader.readLine()).thenReturn("a", "3", "-10");
        Move move = inputReader.readMove();
        assertThat(move.from, is(equalTo(3)));
        assertThat(move.to, is(equalTo(-10)));
    }

    @Test
    public void readMove_RepeatEnterText_WhenInvalidValueSupplied() throws Exception {
        when(bufferedReader.readLine()).thenReturn("a", "3", "-10");
        inputReader.readMove();
        assertThat(outputStream.toString(), is(equalTo("From: From: To: ")));
    }

    @Test
    public void readMove_DisplayEnterText() throws Exception {
        when(bufferedReader.readLine()).thenReturn("3", "-10");
        inputReader.readMove();
        assertThat(outputStream.toString(), is(equalTo("From: To: ")));
    }
}
