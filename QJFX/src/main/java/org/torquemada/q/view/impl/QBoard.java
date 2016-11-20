package org.torquemada.q.view.impl;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.Starter;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.model.impl.Resources;
import org.torquemada.q.view.contract.IBoard;
import org.torquemada.q.view.contract.ILevel;
import org.torquemada.q.view.contract.IResizable;

public class QBoard implements IBoard, IResizable {

    private static QBoard instance = null;

    private Stage stage;
    private Scene scene;
    @Autowired
    private EventHandler<KeyEvent> keyEventHandler;
    @Setter
    @Autowired
    private ILevel level;

    @Setter
    @Autowired
    private IEngine engine;

    @Setter
    @Autowired
    private SettingsPanel settingsPanel;

    @Override
    public void initialize() {
        settingsPanel.init();
        stage = Starter.getStage();
        Resources.LevelData data = Resources.getLevelData(settingsPanel.getLevelNumber());
        BorderPane root = new BorderPane();

        root.setTop(settingsPanel);
        root.setCenter((QLevel) level);

// Magic number 29 means height of settingsPanel. It's 0 here in this method. Currently, I don't know how to calculate it.
        scene = new Scene(root, data.col * SQUARE_SIZE, data.row * SQUARE_SIZE + 29);
        scene.widthProperty().addListener((observable, oldValue, newValue) -> recalculateWidth(newValue));
        scene.heightProperty().addListener((observable, oldValue, newValue) -> recalculateHeight(newValue.doubleValue() - settingsPanel.getHeight()));
        scene.setOnKeyPressed(keyEventHandler);
        stage.setScene(scene);
        stage.show();
    }

    private void show() {}

    @Override
    public void assignLevel(int number, int row, int col, int[] levelData) {
        level.setDimension(row, col);
        level.setLevelData(levelData);
        level.init();

        recalculateWidth(SQUARE_SIZE * col);
        recalculateHeight(SQUARE_SIZE * row);

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
    public void moveBall(int from, int to, boolean toLoose) {
        level.moveBall(from, to, toLoose);
    }

    @Override
    public void clearLevel() {
        level.clear();
//        revalidate();
    }

    @Override
    public void recalculateWidth(Number newValue) {
        level.recalculateWidth(newValue);
    }

    @Override
    public void recalculateHeight(Number newValue) {
        level.recalculateHeight(newValue);
    }
}
