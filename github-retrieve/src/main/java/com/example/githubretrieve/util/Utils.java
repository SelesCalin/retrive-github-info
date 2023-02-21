package com.example.githubretrieve.util;


public class Utils {

    private Utils() {
    }

    public static int calculateNumberOfPages(int total, int pageSize) {
        return (int) Math.ceil((float) total / pageSize);
    }
}
