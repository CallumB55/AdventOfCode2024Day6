/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.callumhobby.adventofcode2024day6;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 *
 * @author CallumBinns
 */
public class InputReader {

    private String path;
    private List<String> lines;
    private List<String> customSplit;

    public InputReader() {
        this.path = "Input.txt";
        this.lines = getInput();
    }

    public InputReader(String path) {
        this.path = path;
        this.lines = getInput();
    }

    public List<String> getLines() {
        return lines;
    }

    public List<String> getCustomSplit() {
        return customSplit;
    }

    private List<String> getInput() {
        List<String> input = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                input.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("input reader has failed with error:" + e.getMessage());
        }
        return input;
    }

    /**
     *
     * @param splitter used as regex, escape characters required
     * @param ignoreLinebreaks for more predictable custom splitting, view as default
     */
    public void customSplit(String splitter, boolean ignoreLinebreaks) {
        List<String> customList = new ArrayList<>();
        if (ignoreLinebreaks) {
            String monolithicData = String.join("",lines);
            String[] splitItems = monolithicData.split(splitter);
            customList.addAll(Arrays.asList(splitItems));

        } else {
            for (String ln : lines) {
                String[] splitItems = ln.split(splitter);
                customList.addAll(Arrays.asList(splitItems));
            }
        }

        this.customSplit = customList;
    }
}
