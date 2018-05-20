package org.torquemada.q.view.impl;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
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
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

        level.setDimension(row, col);
        level.setLevelData(levelData);
        level.init();

        double stageWidth = SQUARE_SIZE * col;
        double stageHeight = SQUARE_SIZE * row;
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight + settingsPanel.region().getHeight());
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

        recalculateWidth(stageWidth);
        recalculateHeight(stageHeight - settingsPanel.region().getHeight() + 5);

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
