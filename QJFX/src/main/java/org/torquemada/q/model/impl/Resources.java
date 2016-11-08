package org.torquemada.q.model.impl;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by torquemada on 6/5/16.
 */
public class Resources {

    private static HashMap<Integer, LevelData> levelMap = new HashMap<>();

    public static class LevelData {
        public int[] levelData;
//        public int[] dimension;
        public int number;
        public int row;
        public int col;

        LevelData(int number, int[] levelData, int row, int col) {
            this.levelData = levelData;
            this.row = row;
            this.col = col;
//            this.dimension = dimension;
            this.number = number;
        }
    }

    public static void loadResources() {
        List<String> stringLevels = null;

        try {
            stringLevels = FileUtils.readLines(new File("src/main/resources/standard.lev"), "UTF-8");
        } catch (IOException e) {
            System.err.println("Can't load levels from standard.lev");
            System.exit(1);
        }

        boolean readLevelNumber;
        boolean readDimension = false;
        boolean readLevelData = false;

        int currentNumber = 0;
        int stringLevelDataNumber = 0;
        int row = 0, col = 0;
        int[] currentLevelData = null;
        String[] currentSplitData;

        int j = 0;
        for (String stringLevel : stringLevels) {
            readLevelNumber = stringLevel.startsWith("%");

            if (readLevelNumber) {
                if (readLevelData) {
                    readLevelData = false;
                    levelMap.put(currentNumber, new LevelData(currentNumber, currentLevelData, /*currentDimension.clone()*/ row, col));
                }
                currentNumber = Integer.parseInt(stringLevel.replace('%', ' ').trim());
                stringLevelDataNumber = 0;
                readDimension = true;
                continue;
            }
            if (readDimension) {
                currentSplitData = stringLevel.split("\\s");
                col = Integer.parseInt(currentSplitData[0]);
                row = Integer.parseInt(currentSplitData[1]);
                currentLevelData = new int[row * col];
                readDimension = false;
                readLevelData = true;
                continue;
            }
            if (readLevelData) {
                currentSplitData = stringLevel.split("\\s");
                int i = col * stringLevelDataNumber++;
                for (int loop = i; loop < i + currentSplitData.length; loop++) {
                    currentLevelData[loop] = Integer.parseInt(currentSplitData[loop - i]);
                }
            }
        }
    }

    public static String[] getAvailableLevelNumbersAsStringArray() {
        return levelMap.keySet().stream().map(String::valueOf).toArray(size -> new String[size]);
    }

    public static LevelData getLevelData(int number) {
        LevelData levelData = levelMap.get(number);
        if (levelData == null) return null;
        return new LevelData(levelData.number, levelData.levelData.clone(), /*levelData.dimension.clone()*/ levelData.row, levelData.col);
    }
}
