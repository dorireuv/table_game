package com.dorireuv.tablegame.input;

import java.io.*;

public class ConsoleInputReader implements InputReader {

    private final PrintStream ps;
    private final BufferedReader br;

    public ConsoleInputReader() {
        this(System.out, new BufferedReader(new InputStreamReader(System.in)));
    }

    public ConsoleInputReader(PrintStream ps, BufferedReader br) {
        this.ps = ps;
        this.br = br;
    }

    @Override
    public Move readMove() {
        int from = readNumber("From: ");
        int to = readNumber("To: ");
        return new Move(from, to);
    }

    private int readNumber(String text) {
        Integer number = null;
        while (number == null) {
            try {
                number = Integer.parseInt(tryToReadLine(text));
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        return number;
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
