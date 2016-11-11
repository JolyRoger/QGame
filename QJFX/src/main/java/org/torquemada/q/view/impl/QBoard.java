package org.torquemada.q.view.impl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.model.impl.Resources;
import org.torquemada.q.view.contract.IBoard;
import org.torquemada.q.view.contract.ILevel;
import org.torquemada.q.view.contract.Resizable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class QBoard extends Application implements IBoard {

    private static QBoard instance = null;
    private final static int SQUARE_SIZE = 64;
    private Stage stage;

    @Setter
    @Autowired
    private ILevel level;

    @Setter
    @Autowired
    private IEngine engine;

    @Setter
    @Autowired
    private SettingsPanel settingsPanel;

    private static final CountDownLatch LATCH = new CountDownLatch(1);

    @Override
    public void initialize() {
        settingsPanel.init();
        Resources.LevelData data = Resources.getLevelData(settingsPanel.getLevelNumber());
        BorderPane root = new BorderPane();

        root.setTop(settingsPanel);
        root.setCenter((QLevel) level);

        Scene scene = new Scene(root, data.col * SQUARE_SIZE, data.row * SQUARE_SIZE);

        scene.widthProperty().addListener((observable, oldValue, newValue) -> orderWidth(newValue/*, data.col*/));
        scene.heightProperty().addListener((observable, oldValue, newValue) -> orderHeight(newValue.doubleValue() - settingsPanel.getHeight()/*, data.row*/));

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void assignLevel(int number, int row, int col, int[] levelData) {
        level.setDimension(row, col);
        level.setLevelData(levelData);
        level.init();
        orderWidth(SQUARE_SIZE * col);
        orderHeight(SQUARE_SIZE * row);
//        setTitle("Q-Game. Level " + number);
    }

    @Override
    public void select(int id, boolean select) {
        level.select(id, select);
    }

    @Override
    public void selectToMove(int selectedId) {
        level.selectToMove(selectedId);
    }

    @Override
    public boolean isReadyToMove(int selectedId) {
        return level.isReadyToMove(selectedId);
    }

    @Override
    public void moveBall(int from, int to) {
        if (to == -1) level.removeBall(from);
        else level.moveBall(from, to);
    }

    @Override
    public void clearLevel() {
        level.clear();
//        revalidate();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setOnCloseRequest((e) -> {
                Platform.exit();
                System.exit(0);
        });
        instance = this;
        LATCH.countDown();
    }

    public static QBoard waitAndGetInstance() throws InterruptedException {
        LATCH.await();
        return instance;
    }

    private void orderWidth(Number newValue) {
        settingsPanel.recalculateWidth(newValue);
        ((Resizable) level).recalculateWidth(newValue);
    }

    private void orderHeight(Number newValue) {
        ((Resizable) level).recalculateHeight(newValue);
    }
}
