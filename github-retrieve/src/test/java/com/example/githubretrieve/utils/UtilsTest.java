package com.example.githubretrieve.utils;

import com.example.githubretrieve.util.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {

    @Test
    void testCalculateNumberOfPages_totalIsMultipleOfPageSize() {
        assertEquals(5, Utils.calculateNumberOfPages(50, 10));

    }

    @Test
    void testCalculateNumberOfPages_totalIsLessThanPageSize() {
        assertEquals(1, Utils.calculateNumberOfPages(5, 10));
    }

    @Test
    void testCalculateNumberOfPages_totalIsNotAPerfectMultipleOfPageSize() {
        assertEquals(6, Utils.calculateNumberOfPages(53, 10));    }
}
