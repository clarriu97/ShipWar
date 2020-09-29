package main;

import java.util.Random;

public class Rnd extends Random {
    private static Rnd singleton = new Rnd();
    public static Rnd get() { return singleton; }
    // ---------------------------------------------------------------------------------------

    private Rnd() { }
}
