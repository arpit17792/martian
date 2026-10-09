package com.example.martian;

public enum Orientation {
    N(0, 1),
    E(1, 0),
    S(0, -1),
    W(-1, 0);

    private final int deltaX;
    private final int deltaY;

    Orientation(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int deltaX() {
        return deltaX;
    }

    public int deltaY() {
        return deltaY;
    }

    public Orientation turnLeft() {
        return switch (this) {
            case N -> W;
            case W -> S;
            case S -> E;
            case E -> N;
        };
    }

    public Orientation turnRight() {
        return switch (this) {
            case N -> E;
            case E -> S;
            case S -> W;
            case W -> N;
        };
    }

    public static Orientation parse(String value) {
        try {
            return Orientation.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException(
                    "Invalid orientation: " + value, e);
        }
    }
}
