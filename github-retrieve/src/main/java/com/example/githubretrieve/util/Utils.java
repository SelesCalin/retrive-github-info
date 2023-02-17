package com.example.githubretrieve.util;


public class Utils {

    private Utils() {
    }

    public static int calculateNumberOfPages(int total, int pageSize) {
        var totalPages = total / pageSize;
        if (total % pageSize != 0) {
            totalPages++;
        }
        return totalPages;
    }
}
