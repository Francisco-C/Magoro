package com.example.magoro.Activities.Game2048;

public class Validator {

    public static void mustBeTrue(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Condition is not true, " + msg);
        }
    }
}
