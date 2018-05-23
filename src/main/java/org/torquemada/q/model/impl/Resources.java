package org.torquemada.q.model.impl;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
/**
 * Created by torquemada on 6/5/16.
 * Load and get level map.
 */
// TODO: make normal load level
public class Resources {

    private static HashMap<Integer, LevelData> levelMap = new HashMap<>();

    public static class LevelData {
        public int[] levelData;
        public int number;
        public int row;
        public int col;

        LevelData(int number, int[] levelData, int row, int col) {
            this.levelData = levelData;
            this.row = row;
            this.col = col;
            this.number = number;
        }
    }

    @SneakyThrows(Exception.class)
    private static List<String> readStringLevels() {
        ClassLoader classLoader = Resources.class.getClassLoader();
        InputStream levels = classLoader.getResourceAsStream("standard.lev");
        LineNumberReader reader = new LineNumberReader(new BufferedReader(new InputStreamReader(levels)));
        return IOUtils.readLines(levels, "UTF-8");
    }

    public static void loadResources() {
        List<String> stringLevels = readStringLevels();

        boolean readLevelNumber;
        boolean readDimension = false;
        boolean readLevelData = false;

        int currentNumber = 0;
        int stringLevelDataNumber = 0;
        int row = 0, col = 0;
        int[] currentLevelData = null;
        String[] currentSplitData;

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

    public static int getLevelCount() {
        return levelMap.keySet().size();
    }

    public static Integer[] getAvailableLevelNumbersAsIntegerArray() {
        return levelMap.keySet().stream().toArray(Integer[]::new);
    }

    public static LevelData getLevelData(int number) {
        LevelData levelData = levelMap.get(number);
        if (levelData == null) return null;
        return new LevelData(levelData.number, levelData.levelData.clone(), levelData.row, levelData.col);
    }
}
