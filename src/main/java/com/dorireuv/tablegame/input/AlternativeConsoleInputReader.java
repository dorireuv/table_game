package com.dorireuv.tablegame.input;

import com.google.common.base.Splitter;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AlternativeConsoleInputReader implements InputReader {

    private final PrintStream ps;
    private final BufferedReader br;

    public AlternativeConsoleInputReader(PrintStream ps, InputStream is) {
        this(ps, new BufferedReader(new InputStreamReader(is)));
    }

    public AlternativeConsoleInputReader(PrintStream ps, BufferedReader br) {
        this.ps = ps;
        this.br = br;
    }

    @Override
    public Move readMove() {
        Move move = null;
        while (move == null) {
            try {
                String text = tryToReadLine("Enter from and to space separated: ");
                Iterator<String> parts = Splitter.on(' ').split(text).iterator();
                int from = Integer.parseInt(parts.next());
                int to = Integer.parseInt(parts.next());
                move = new Move(from, to);
            } catch (NumberFormatException e) {
                // ignore
            } catch (NoSuchElementException e) {
                // ignore
            }
        }

        return move;
    }

    private String tryToReadLine(String text) {
        String fromString = null;
        try {
            ps.print(text);
            fromString = br.readLine();
        } catch (IOException e) {
            // ignore
        }

        return fromString;
    }
}
