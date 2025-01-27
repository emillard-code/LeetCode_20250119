package com.project.attempt;

import java.util.HashSet;

public class LeetCodeAttempt {

    public static void main(String[] args) {

        int[][] heightMap1 = new int[][]{{1,4,3,1,3,2},{3,2,1,3,2,4},{2,3,3,2,3,1}};
        System.out.println(trappingRainWaterII(heightMap1));

        int[][] heightMap2 = new int[][]{{3,3,3,3,3},{3,2,2,2,3},{3,2,1,2,3},{3,2,2,2,3},{3,3,3,3,3}};
        System.out.println(trappingRainWaterII(heightMap2));

    }

    // This method will return the number the volume of water trapped.
    public static int trappingRainWaterII(int[][] heightMap) {

        // int height will contain the value of the 'highest' cell and treat it as
        // the overall height of the entire structure.
        int height = 0;

        // We will also count the total number of cells physically present.
        int totalCells = 0;

        for (int i = 0; i < heightMap.length; i++) {

            for (int j = 0; j < heightMap[i].length; j++) {

                height = Math.max(height, heightMap[i][j]);
                totalCells = totalCells + heightMap[i][j];

            }

        }

        // Once we have the "height" of the entire structure, we will treat the entire structure
        // as a giant rectangular block and find its entire hypothetical area, including even
        // the spaces where cells do not exist within our calculations.
        int length = heightMap.length;
        int width = heightMap[0].length;
        int area = length * width * height;

        // Once we have the entire "area" of the rectangular super-structure including the spaces
        // where cells do not exist, we then take this "area" and subtract the total number of
        // actual physical cells from it. This will give us the number of 'empty' cells that are
        // left. The reason we are interested in 'empty' cells is because water can only be filled
        // inside 'empty' cells that are enclosed and surrounded by actual physical cells. Hence,
        // the number of water spaces that can be filled will necessarily be equal or smaller than
        // the value that we obtained here. We store the value inside int water, and we will keep
        // subtracting cells from it that are unable to hold water in the later logic of this method.
        // Once we finished all our method logic, we will eventually return int water as the method's
        // solution for the volume of water that this structure can contain.
        int water = area - totalCells;

        // We will iterate through the entire structure, *including* through its height.
        // We will look at *each* cell in heightMap[i][j] and see which ones can store water.
        for (int i = 0; i < heightMap.length; i++) {

            for (int j = 0; j < heightMap[i].length; j++) {

                // If the index is anywhere on the edge of the structure, it necessarily cannot
                // be a space that holds water as it will spill off the edge no matter what.
                // Hence, we subtract the number of all 'empty' cells that exists at this index
                // from int water (we've already subtracted the number of physical cells earlier).
                if (i == 0 || j == 0 || i == heightMap.length - 1 || j == heightMap[i].length - 1) {
                    water = water - (height - heightMap[i][j]);
                    continue;
                }

                // For each heightMap[i][j], we look at *every* 'empty' cell that exists here.
                // The number of 'empty' cells that exist is the number of actual physical cells
                // at this index minus the total height of the structure. This value will be
                // represented by int k which will iterate through all 'empty' cells at this index.
                for (int k = heightMap[i][j] + 1; k <= height; k++) {

                    // For each 'empty' cell, we will call a helper method that will check if water
                    // would spill from this cell. If the water will spill from this cell, we
                    // decrement the value of int water by 1. We do this for each 'empty' cell
                    // that is unable to hold water.
                    if (spillable(heightMap, i, j, k, new HashSet<Integer>())) {
                        water--;
                    }

                }

            }

        }

        // Once we have subtracted all actual physical cells, and all 'empty' cells that are
        // unable to hold water, what we have left is the actual number of 'empty' cells that
        // actually *can* hold water within them. We will return this value as the method solution.
        return water;

    }

    // This is a helper method that checks if a specific 'empty' cell can hold water or not.
    // Returns true if it cannot hold water, returns false if it can hold water.
    private static boolean spillable(int[][] heightMap, int row, int column, int height,
                                     HashSet<Integer> travelledIndex) {

        // If the current cell is adjacent to another cell that's at the edge of the structure,
        // and that other cell is an 'empty' cell and not an actual physical cell, then we can
        // assume water will spill out of the current cell into that other cell and eventually
        // out of the structure. In all these cases, we return true to indicate water is spilt.
        if (heightMap[row+1][column] < height && row + 1 == heightMap.length - 1) { return true; }
        if (heightMap[row-1][column] < height && row - 1 == 0) { return true; }
        if (heightMap[row][column+1] < height && column + 1 == heightMap[0].length - 1) { return true; }
        if (heightMap[row][column-1] < height && column - 1 == 0) { return true; }

        // Since we will be recursively calling this method, we will utilize a list of
        // indexes that we have already traversed so as to avoid being trapped in
        // some kind of hypothetical loop.
        travelledIndex.add(row * heightMap[0].length + column);

        // These boolean values will indicate if the cell in the indicated
        // direction (from the current cell) can hold water or not.
        // true if it can't hold water, false if it can. Default is false.
        boolean north = false;
        boolean south = false;
        boolean east = false;
        boolean west = false;

        // If the cell in the upwards direction is an 'empty' cell, then we will call the
        // method again on that cell to see if it will eventually return true at some point.
        // If true is returned, then boolean north is set to true and we know that cell can't
        // hold water. We also check to make sure we haven't already traversed that cell before.
        if (heightMap[row+1][column] < height &&
                !travelledIndex.contains((row+1) * heightMap[0].length + column)) {
            north = spillable(heightMap, row+1, column, height, travelledIndex);
        }

        // If the cell in the downwards direction is an 'empty' cell, then we will call the
        // method again on that cell to see if it will eventually return true at some point.
        // If true is returned, then boolean south is set to true and we know that cell can't
        // hold water. We also check to make sure we haven't already traversed that cell before.
        if (heightMap[row-1][column] < height &&
                !travelledIndex.contains((row-1) * heightMap[0].length + column)) {
            south = spillable(heightMap, row-1, column, height, travelledIndex);
        }

        // If the cell in the right direction is an 'empty' cell, then we will call the
        // method again on that cell to see if it will eventually return true at some point.
        // If true is returned, then boolean east is set to true and we know that cell can't
        // hold water. We also check to make sure we haven't already traversed that cell before.
        if (heightMap[row][column+1] < height &&
                !travelledIndex.contains((row) * heightMap[0].length + column + 1)) {
            east = spillable(heightMap, row, column+1, height, travelledIndex);
        }

        // If the cell in the left direction is an 'empty' cell, then we will call the
        // method again on that cell to see if it will eventually return true at some point.
        // If true is returned, then boolean west is set to true and we know that cell can't
        // hold water. We also check to make sure we haven't already traversed that cell before.
        if (heightMap[row][column-1] < height &&
                !travelledIndex.contains((row) * heightMap[0].length + column - 1)) {
            west = spillable(heightMap, row, column-1, height, travelledIndex);
        }

        // If any of the directions returned true, then that means the water from the current cell
        // will inevitably spill out through those cells into the outside. The only scenario where
        // water can be held in the current cell is if there's no escape and all directions return
        // false to indicate that water has nowhere to spill out.
        return north || south || east || west;

    }

}
