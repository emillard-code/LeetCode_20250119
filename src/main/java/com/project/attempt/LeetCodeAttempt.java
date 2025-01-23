package com.project.attempt;

import java.util.HashSet;

public class LeetCodeAttempt {

    public static void main(String[] args) {

        int[][] heightMap1 = new int[][]{{1,4,3,1,3,2},{3,2,1,3,2,4},{2,3,3,2,3,1}};
        System.out.println(trappingRainWaterII(heightMap1));

        int[][] heightMap2 = new int[][]{{3,3,3,3,3},{3,2,2,2,3},{3,2,1,2,3},{3,2,2,2,3},{3,3,3,3,3}};
        System.out.println(trappingRainWaterII(heightMap2));

    }

    public static int trappingRainWaterII(int[][] heightMap) {

        int height = 0;
        int totalBlocks = 0;

        for (int i = 0; i < heightMap.length; i++) {

            for (int j = 0; j < heightMap[i].length; j++) {

                height = Math.max(height, heightMap[i][j]);
                totalBlocks = totalBlocks + heightMap[i][j];

            }

        }

        int length = heightMap.length;
        int width = heightMap[0].length;
        int area = length * width * height;

        int water = area - totalBlocks;

        for (int i = 0; i < heightMap.length; i++) {

            for (int j = 0; j < heightMap[i].length; j++) {

                if (i == 0 || j == 0 || i == heightMap.length - 1 || j == heightMap[i].length - 1) {
                    water = water - (height - heightMap[i][j]);
                    continue;
                }

                for (int k = heightMap[i][j] + 1; k <= height; k++) {

                    if (spillable(heightMap, i, j, k, new HashSet<Integer>())) {
                        water--;
                    }

                }

            }

        }

        return water;

    }

    private static boolean spillable(int[][] heightMap, int row, int column, int height,
                                     HashSet<Integer> travelledIndex) {

        if (heightMap[row+1][column] < height && row + 1 == heightMap.length - 1) { return true; }
        if (heightMap[row-1][column] < height && row - 1 == 0) { return true; }
        if (heightMap[row][column+1] < height && column + 1 == heightMap[0].length - 1) { return true; }
        if (heightMap[row][column-1] < height && column - 1 == 0) { return true; }

        travelledIndex.add(row * heightMap[0].length + column);

        boolean north = false;
        boolean south = false;
        boolean east = false;
        boolean west = false;

        if (row+1 < heightMap.length - 1) {
            if (heightMap[row+1][column] < height &&
                    !travelledIndex.contains((row+1) * heightMap[0].length + column)) {
                north = spillable(heightMap, row+1, column, height, travelledIndex);
            }
        }

        if (row-1 > 0) {
            if (heightMap[row-1][column] < height &&
                    !travelledIndex.contains((row-1) * heightMap[0].length + column)) {
                south = spillable(heightMap, row-1, column, height, travelledIndex);
            }
        }

        if (column+1 < heightMap[0].length - 1) {
            if (heightMap[row][column+1] < height &&
                    !travelledIndex.contains((row) * heightMap[0].length + column + 1)) {
                east = spillable(heightMap, row, column+1, height, travelledIndex);
            }
        }

        if (column-1 > 0) {
            if (heightMap[row][column-1] < height &&
                    !travelledIndex.contains((row) * heightMap[0].length + column - 1)) {
                west = spillable(heightMap, row, column-1, height, travelledIndex);
            }
        }

        return north || south || east || west;

    }

//    public static int trappingRainWaterII(int[][] heightMap) {
//
//        int length = heightMap.length;
//        int width = heightMap[0].length;
//
//        int water = 0;
//        int minHeight = Integer.MAX_VALUE;
//
//        for (int i = 0; i < length; i++) {
//
//            for (int j = 0; j < width; j++) {
//
//                boolean waterFlag = true;
//
//                if ((i + 1) < length) {
//                    if (heightMap[i+1][j] > heightMap[i][j]) {
//                        minHeight = heightMap[i+1][j] - heightMap[i][j];
//                    } else {
//                        waterFlag = false;
//                    }
//                } else {
//                    waterFlag = false;
//                }
//
//                if ((i - 1) >= 0) {
//                    if (heightMap[i-1][j] > heightMap[i][j]) {
//                        minHeight = heightMap[i-1][j] - heightMap[i][j];
//                    } else {
//                        waterFlag = false;
//                    }
//                } else {
//                    waterFlag = false;
//                }
//
//                if ((j + 1) < width) {
//                    if (heightMap[i][j+1] > heightMap[i][j]) {
//                        minHeight = heightMap[i][j+1] - heightMap[i][j];
//                    } else {
//                        waterFlag = false;
//                    }
//                } else {
//                    waterFlag = false;
//                }
//
//                if ((j - 1) >= 0) {
//                    if (heightMap[i][j-1] > heightMap[i][j]) {
//                        minHeight = heightMap[i][j-1] - heightMap[i][j];
//                    } else {
//                        waterFlag = false;
//                    }
//                } else {
//                    waterFlag = false;
//                }
//
//                if (waterFlag) { water = water + minHeight; }
//
//            }
//        }
//
//        return water;
//
//    }

}
