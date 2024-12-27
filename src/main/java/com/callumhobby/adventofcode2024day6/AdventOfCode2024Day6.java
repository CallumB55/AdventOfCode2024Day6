/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.callumhobby.adventofcode2024day6;

import java.util.ArrayDeque;
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
        Deque<String> directions = new ArrayDeque<>();
        directions.addLast("up");
        directions.addLast("right");
        directions.addLast("down");
        directions.addLast("left");

        RoomMap room = new RoomMap(data);
        Guard guard = new Guard(room.guardStartPosition, room.obstacles);
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
        room.pathVisualiser(guard.coordinatesVisited);
        System.out.println("distinct coordinates visited: " + String.valueOf(guard.coordinatesVisited.size()));

    }
}
