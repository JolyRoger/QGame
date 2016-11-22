package org.torquemada.q.view.impl;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.Starter;
import org.torquemada.q.view.contract.IBoard;
import org.torquemada.q.view.contract.ILevel;
import org.torquemada.q.view.contract.IResizable;
import org.torquemada.q.view.contract.ISettings;

public class QBoard implements IBoard, IResizable {

    private Stage stage;

    @Autowired
    private EventHandler<KeyEvent> keyEventHandler;
    @Setter
    @Autowired
    private ILevel level;

    @Setter
    @Autowired
    private ISettings settingsPanel;

    @Override
    public void initialize() {
        settingsPanel.init();
        stage = Starter.getStage();
//        Resources.LevelData data = Resources.getLevelData(settingsPanel.getLevelNumber());
        BorderPane root = new BorderPane();

        root.setTop(settingsPanel.region());
        root.setCenter((QLevel) level);

        Scene scene = new Scene(root);
        scene.widthProperty().addListener((observable, oldValue, newValue) -> recalculateWidth(newValue));
        scene.heightProperty().addListener((observable, oldValue, newValue) -> recalculateHeight(newValue.doubleValue() -
                settingsPanel.region().getHeight()));
        scene.setOnKeyPressed(keyEventHandler);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void assignLevel(int number, int row, int col, int[] levelData) {
        level.setDimension(row, col);
        level.setLevelData(levelData);
        level.init();

        stage.setWidth(SQUARE_SIZE * col);
        stage.setHeight(SQUARE_SIZE * row + settingsPanel.region().getHeight());
        recalculateWidth(SQUARE_SIZE * col);
        recalculateHeight(SQUARE_SIZE * row);

        stage.setTitle("Q-Game. Level " + number);
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
