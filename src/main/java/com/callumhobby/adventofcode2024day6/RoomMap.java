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
public class RoomMap {

    public char[][] grid;
    public List<Integer[]> obstacles;
    public Integer[] guardStartPosition;
    public Integer gridMaxX;
    public Integer gridMaxY;

    public RoomMap(List<String> input) {
        this.gridMaxX = input.getFirst().length() - 1;
        this.gridMaxY = input.size() - 1;
        this.obstacles = new ArrayList<>();
        this.grid = new char[gridMaxY+1][gridMaxX+1];
        for (int i = 0; i <= gridMaxY; i++) {
            for (int j = 0; j <= gridMaxX; j++) {
                char charToAdd = input.get(i).charAt(j);
                if (charToAdd == '#') {
                    obstacles.add(new Integer[]{i,j});
                }
                if (charToAdd == '^') {
                    guardStartPosition = new Integer[]{i,j};
                }
                grid[i][j] = charToAdd;
            }
        }


    }
    
    public void pathVisualiser(List<Integer[]> visitedPositions){
        for (int i = 0; i <= gridMaxY; i++) {
            String lineToPrint = "";
            for (int j = 0; j <= gridMaxX; j++) {
                if (inCoordinateArray(i,j,visitedPositions)) {
                    lineToPrint += 'x';
                }
                else{
                    lineToPrint += grid[i][j];
                }
            }
            System.out.println(lineToPrint);
        }
    }
    
    private boolean inCoordinateArray(Integer y, Integer x, List<Integer[]> coordinateArray){
        boolean isIn = false;
        for (Integer[] coordinate : coordinateArray) {
            if (coordinate[0].equals(y) && coordinate[1].equals(x)) {
                isIn = true;
            }
        }
        return isIn;
    }
    
}
