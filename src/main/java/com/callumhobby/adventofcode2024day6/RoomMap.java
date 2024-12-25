/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.callumhobby.adventofcode2024day6;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author CallumBinns
 */
public class CrosswordGrid {

    public char[][] grid;
    private String forwards;
    private String backwards;
    private Integer gridMaxX;
    private Integer gridMaxY;

    public CrosswordGrid(List<String> input) {
        this.gridMaxX = input.getFirst().length() - 1;
        this.gridMaxY = input.size() - 1;
        this.grid = new char[gridMaxY+1][gridMaxX+1];
        for (int i = gridMaxY; i >= 0; i--) {
            for (int j = 0; j <= gridMaxX; j++) {
                grid[i][j] = input.get(i).charAt(j);
            }
        }
        this.forwards = "XMAS";
        this.backwards = "SAMX";

    }

    /**
     *
     * @param isColumns
     * @return integer array where 0 is forwards count and 1 is backwards count
     */
    public Integer[] getInstancesInRowsOrColumns(boolean isColumns) {
        List<String> linesToCheck = new ArrayList<>();

        if (!isColumns) {
            for (int i = 0; i < grid[0].length; i++) {
                linesToCheck.add("");
                for (char[] row : grid) {
                    linesToCheck.set(i, linesToCheck.get(i) + row[i]);
                }
            }
        } else {
            for (char[] row : grid) {
                linesToCheck.add(String.valueOf(row));
            }
        }

        return xMasCountLogic(linesToCheck);
    }

    public Integer[] getInstancesInDiagonals() {
        List<String> linesToCheck = new ArrayList<>();
        //Doing this graphically with y=mx+c but bound by the length and width of the grid
        //positive gradient diagonals loops:
        for (int c = -gridMaxX; c <= gridMaxY; c++) {//loop to iterate through each line (aka every possible y intercept)
            String line = "";
            for (int x = 0; x <= gridMaxX; x++) {//iterate through each possible x value
                Integer y = x + c;
                if (y >= 0 && x >= 0 && y <= gridMaxY) {
                    line += grid[y][x];
                }
            }
            linesToCheck.add(line);
        }
        //negative gradient diagonals loops:
        for (int c = 0; c <= gridMaxY + gridMaxX; c++) {
            String line = "";
            for (int x = 0; x <= gridMaxX; x++) {//iterate through each possible x value
                Integer y = -x + c;
                if (y >= 0 && x >= 0 && y <= gridMaxY) {
                    line += grid[y][x];
                }

            }
            linesToCheck.add(line);
        }

        return xMasCountLogic(linesToCheck);
    }

    private Integer[] xMasCountLogic(List<String> linesToCheck) {
        Integer[] xMasCount = {0, 0};
        for (String string : linesToCheck) {
            Integer initialCount = string.length();
            Integer forwardsMissing = string.replaceAll(forwards, "").length();
            Integer backwardsMissing = string.replaceAll(backwards, "").length();

            xMasCount[0] += initialCount != forwardsMissing ? (initialCount - forwardsMissing) / forwards.length() : 0;
            xMasCount[1] += initialCount != backwardsMissing ? (initialCount - backwardsMissing) / backwards.length() : 0;

        }
        return xMasCount;
    }

    public Integer masInXShapeCount() {

        Integer[] startCoordinates = {0, 0};
        XWindow win = new XWindow(startCoordinates);
        Integer xCount = xShapeCounter(win.getCoordsOfStringsToCheck());
        while (win.hasSpaceToShiftDown(gridMaxY)) {//possible bugs from going down one line too far or too little

            while (win.hasSpaceToShiftRight(gridMaxX)) {
                xCount += xShapeCounter(win.shiftRightOne());
            }
            xCount += xShapeCounter(win.shiftDownOneAndReturnToLeft());
        }
        while (win.hasSpaceToShiftRight(gridMaxX)) {
            xCount += xShapeCounter(win.shiftRightOne());
        }

        return xCount;
    }
    
    public void switchSearchStrings(String[] newPair){
        this.forwards = newPair[0];
        this.backwards = newPair[1];
    }

    private Integer xShapeCounter(List<Integer[][]> in) {
        List<String> linesToCheck = new ArrayList<>();
        for (Integer[][] charCoords : in) {
            String line = "";
            for (Integer[] charCoord : charCoords) {
                line += grid[charCoord[0]][charCoord[1]];
            }
            linesToCheck.add(line);
        }
        boolean isX = true;
        
        for (String string : linesToCheck) {
            if (!(string.equals(forwards) || string.equals(backwards))) {
                isX = false;
            }
        }
        
        if (isX) {
            return 1;
        }
        else{
            return 0;
        }
    }
}
