package com.example.marce.luckypuzzle.utils;

/**
 * Created by tictapps on 18/04/17.
 */

public class MathUtils {

    public static int roundUP(double d) {
        double dAbs = Math.abs(d);
        int i = (int) dAbs;
        double result = dAbs - (double) i;
        if (result == 0.0) {
            return (int) d;
        } else {
            return (int) d < 0 ? -(i + 1) : i + 1;
        }
    }
}
