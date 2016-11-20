package org.torquemada.q.controller.impl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.torquemada.q.Config;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.model.contract.Direction;
import org.torquemada.q.model.impl.Resources;
import org.torquemada.q.view.contract.IBoard;
import org.torquemada.q.view.contract.ILevel;
import org.torquemada.q.view.contract.ISettings;
import org.torquemada.q.view.impl.QBoard;
import org.torquemada.q.view.impl.SettingsPanel;

import java.util.ArrayList;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

/**
 * Created by torquemada on 6/2/16.
 * Engine of the game. Calculates and manages it.
 */
public class QEngine implements IEngine {

    private int selectedId;
    private boolean selectedToMove;
    @Getter
    private int currentLevel;
    @Getter
    private int rowAmount, colAmount;
    private int [] levelData;

    private ArrayList<Integer> balls = new ArrayList<>();
    private ArrayList<Integer> looses = new ArrayList<>();

    @Lazy
    @Autowired
    private IBoard board;

    @Autowired
    private ISettings settings;

    @Override
    public void run() {
        currentLevel = 1;
        reloadLevel();
        board.initialize();
    }

    @Override
    public void reloadLevel() {
        balls.clear();
        looses.clear();
        clearLevel();
        Resources.LevelData data = Resources.getLevelData(currentLevel);
        levelData = data.levelData;
        rowAmount = data.row;
        colAmount = data.col;

        for (int i = 0; i < levelData.length; i++) {
            if (isBall(levelData[i])) balls.add(i);
            if (isLoose(levelData[i])) looses.add(i);
        }

        board.assignLevel(data.number, rowAmount, colAmount, levelData);
        selectedId = balls.get(0);
        notifySelect(selectedId);
    }

    @Override
    public void loadLevel(int levelNumber) {
        currentLevel = levelNumber;
        settings.setLevelNumber(levelNumber);
        reloadLevel();
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    private void win() {
        Resources.LevelData data = Resources.getLevelData(currentLevel+1);
        if (data == null) currentLevel = 0;
        loadLevel(currentLevel+1);
    }

    private void clearLevel() {
        selectedToMove = false;
        board.clearLevel();
    }

    private void roll(int initial, IntPredicate cond, IntUnaryOperator step) {
        int from = selectedId;
        int to = selectedId;

        for (int i = initial; cond.test(i); i = step.applyAsInt(i)) {
            if (levelData[i] == 0) { to = i; continue; }
            if (levelData[i] == levelData[selectedId] * 11) {
                to = i;
                ballInLoose(from, to);
                return;
            }
            break;
        }

        int tmp = levelData[from];
        levelData[from] = levelData[to];
        levelData[to] = tmp;
        selectedId = to;
        balls.remove((Integer) from);
        balls.add(to);
        board.moveBall(from, to);
    }

    private void ballInLoose(int from, int to) {
        levelData[from] = 0;
        balls.remove((Integer) from);
        board.moveBall(from, -1);
        if (balls.size() > 0) {
            selectedToMove = false;
            notifySelect(balls.get(0));
        } else {
            win();
        }
    }

    private boolean isBall(int i) {
        return i>1 && i<10;
    }
    private boolean isLoose(int i) {
        return i>10 && i%11 == 0;
    }

    private void notifySelect(int id) {
        board.select(selectedId, false);
        selectedId = id;
        board.select(selectedId, true);
    }

    /*
     * Select-deselect to move
     */
    @Override
    public void notifySpace() {
        board.selectToMove(selectedId);
        selectedToMove = board.isReadyToMove(selectedId);
    }

    @Override
    public void notifyLeft() {
        if (selectedToMove) {
            roll(selectedId-1, i -> i / colAmount == selectedId / colAmount, i -> --i);
        } else {
            notifySelect(QUtils.calculatePreferredToSelect(balls, selectedId, colAmount, Direction.Left));
        }
    }

    @Override
    public void notifyUp() {
        if (selectedToMove) {
            roll(selectedId - colAmount, i -> i >= 0, i -> i - colAmount);
        } else {
            notifySelect(QUtils.calculatePreferredToSelect(balls, selectedId, colAmount, Direction.Up));
        }
    }

    @Override
    public void notifyRight() {
        if (selectedToMove) {
            roll(selectedId+1, i -> i / colAmount == selectedId / colAmount, i -> ++i);
        } else {
            notifySelect(QUtils.calculatePreferredToSelect(balls, selectedId, colAmount, Direction.Right));
        }
    }

    @Override
    public void notifyDown() {
        if (selectedToMove) {
            roll(selectedId + colAmount, i -> i < levelData.length, i -> i + colAmount);
        } else {
            notifySelect(QUtils.calculatePreferredToSelect(balls, selectedId, colAmount, Direction.Down));
        }
    }

    public static void main(String[] args) throws InterruptedException {

        new Thread("Run QGame") {
            @Override
            public void run() {
                Application.launch(QBoard.class);
            }
        }.start();

        QBoard board = QBoard.waitAndGetInstance();
        Resources.loadResources();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        board.setLevel(context.getBean(ILevel.class));
        board.setSettingsPanel(context.getBean(SettingsPanel.class));
        board.setEngine(context.getBean(IEngine.class));
//        board.setEventHandler((QEventHandler) context.getBean("eventHandler"));

        context.getBeanFactory().registerSingleton("qboard", board);
        Platform.runLater(context.getBean(IEngine.class)::run);
    }
}
