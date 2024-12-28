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
public class Guard {

    private Integer[] position;
    private List<Integer[]> obstacles;
    public List<Integer[]> coordinatesVisited;
    private Integer stepsSinceLastUnknownCoordinate;
    public boolean hasLeftArea;

    /**
     * @param start, where guard pointer was found
     * @param knownObstacles, a list of obstacle locations found on the map
     */
    public Guard(Integer[] start, List<Integer[]> knownObstacles) {
        this.position = start;
        this.obstacles = knownObstacles;
        this.coordinatesVisited = new ArrayList<>();
        coordinatesVisited.add(new Integer[]{start[0],start[1]});
        this.stepsSinceLastUnknownCoordinate = 0;
        this.hasLeftArea = false;
    }

    /**
     *
     * @param direction
     * @param gridMaxX
     * @param gridMaxY
     * @return
     */
    public boolean takeStep(String direction, Integer gridMaxX, Integer gridMaxY) {
        Integer[] original = new Integer[] {position[0],position[1]};
        if(direction.equals("up")) {
            position[0] -= 1;
        }
        else if(direction.equals("down")){
            position[0] += 1;
        }
        else if(direction.equals("left")){
            position[1] -= 1;
        }
        else{
            position[1] += 1;
        }
        
                
        if (checkPositionIsValid(gridMaxX, gridMaxY)) {
            boolean visitedBefore = false;
            for (Integer[] coordinate : coordinatesVisited) {
                
                if (coordinate[0].equals(position[0]) && coordinate[1].equals(position[1])) {
                    visitedBefore = true;
                    stepsSinceLastUnknownCoordinate ++;
                }
            }
            if (!visitedBefore) {
                coordinatesVisited.add(new Integer[]{position[0],position[1]});

            }
            return true;
        } else {
            if (isInBounds(gridMaxX, gridMaxY)) {
                position = original;
                return false;
            }
            else{
                this.hasLeftArea = true;
                return false;
            }
            
        }

    }
    

    
    public boolean notTooManySteps(){
        return coordinatesVisited.size() >= stepsSinceLastUnknownCoordinate;
    }

    private boolean checkPositionIsValid(Integer gridMaxX, Integer gridMaxY) {
        
        if (isInBounds(gridMaxX,gridMaxY)) {
            for (Integer[] coordinate : obstacles) {//not the most efficient way of doing this but a map datatype requires a key datatype with a .equals() method
                if (position[0].equals(coordinate[0]) && position[1].equals(coordinate[1])) {
                    return false;
                    
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    private boolean isInBounds(Integer gridMaxX, Integer gridMaxY){
        boolean isValidRow = position[0] >= 0 && position[0] <= gridMaxY;
        boolean isValidColumn = 0 <= position[1] && position[1] <= gridMaxX ;       
        return isValidColumn && isValidRow;
    }
    
    public void addNewObstacle(Integer[] newObstacle){
        obstacles.add(newObstacle);
    }
    
    public void swapNewObstacle(Integer[] newObstacle){
        obstacles.removeLast();
        addNewObstacle(newObstacle);
    }

}
