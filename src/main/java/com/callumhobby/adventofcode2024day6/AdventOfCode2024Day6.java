/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.callumhobby.adventofcode2024day6;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 *
 * @author CallumBinns
 */
public class AdventOfCode2024Day6 {

    public static void main(String[] args) {
        InputReader in = new InputReader("src\\main\\java\\com\\callumhobby\\adventofcode2024day6\\Input.txt");
        List<String> data = in.getLines();
        RoomMap room = new RoomMap(data);
        Guard g1 = new Guard(new Integer[]{room.guardStartPosition[0],room.guardStartPosition[1]}, room.obstacles);
        plotGuardRoute(g1, room);
        room.pathVisualiser(g1.coordinatesVisited);
        System.out.println("distinct coordinates visited: " + String.valueOf(g1.coordinatesVisited.size()));
        
        Integer loopsFound = 0;
        for (int i = 0; i < g1.coordinatesVisited.size(); i++) {
            if (!Arrays.equals(g1.coordinatesVisited.get(i), room.guardStartPosition)) {
                List<Integer[]> newObstaclesObject = new ArrayList<>();//previously all guards were using the same object reference
                for (Integer[] obstacle : room.obstacles) {
                    newObstaclesObject.add(obstacle);
                }
                Guard loopGuard = new Guard(new Integer[]{room.guardStartPosition[0],room.guardStartPosition[1]}, newObstaclesObject);
                loopGuard.addNewObstacle(g1.coordinatesVisited.get(i));
                //loopGuard = plotGuardRoute(loopGuard, room);
                plotGuardRoute(loopGuard, room);
                if (!loopGuard.hasLeftArea) {
                    loopsFound++;
                }
                //room.pathVisualiser(loopGuard.coordinatesVisited);
            }

        }
        System.out.println("Number of options for loop generating obstacles: " + String.valueOf(loopsFound));

    }

    public static void plotGuardRoute(Guard guard, RoomMap room) {
        Deque<String> directions = new ArrayDeque<>();
        directions.addLast("up");
        directions.addLast("right");
        directions.addLast("down");
        directions.addLast("left");
        boolean forwardIsClear = true;
        String direction = directions.removeFirst();
        while (guard.notTooManySteps()) {
            
            if (!forwardIsClear) {
                directions.addLast(direction);
                direction = directions.removeFirst();
            }
            forwardIsClear = true;
            while (forwardIsClear) {
                forwardIsClear = guard.takeStep(direction, room.gridMaxX, room.gridMaxY);
            }
            if (guard.hasLeftArea) {
                break;
            }
        }
    }

}
