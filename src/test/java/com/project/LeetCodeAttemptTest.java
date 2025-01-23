package com.project;

import com.project.attempt.LeetCodeAttempt;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeetCodeAttemptTest {

    @Test
    public void trappingRainWaterIITest() {

        int[][] heightMap1 = new int[][]{{1,4,3,1,3,2},{3,2,1,3,2,4},{2,3,3,2,3,1}};
        assertEquals(4, LeetCodeAttempt.trappingRainWaterII(heightMap1));

        int[][] heightMap2 = new int[][]{{3,3,3,3,3},{3,2,2,2,3},{3,2,1,2,3},{3,2,2,2,3},{3,3,3,3,3}};
        assertEquals(10, LeetCodeAttempt.trappingRainWaterII(heightMap2));

    }

}
