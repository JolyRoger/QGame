package org.torquemada.q.view.impl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.Starter;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.controller.impl.QEventHandler;
import org.torquemada.q.model.impl.Resources;
import org.torquemada.q.view.contract.IBoard;
import org.torquemada.q.view.contract.ILevel;
import org.torquemada.q.view.contract.IResizable;
import java.util.concurrent.CountDownLatch;

public class QBoard extends Application implements IBoard, IResizable {

    private static QBoard instance = null;

    private Stage stage;
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

    private static final CountDownLatch LATCH = new CountDownLatch(1);

    @Override
    public void initialize() {
        settingsPanel.init();
        stage = Starter.getStage();
        Resources.LevelData data = Resources.getLevelData(settingsPanel.getLevelNumber());
        BorderPane root = new BorderPane();

        root.setTop(settingsPanel);
        root.setCenter((QLevel) level);

// Magic number 29 means height of settingsPanel. It's 0 here in this method. Currently, I don't know how to calculate it.
        Scene scene = new Scene(root, data.col * SQUARE_SIZE, data.row * SQUARE_SIZE + 29);
        scene.widthProperty().addListener((observable, oldValue, newValue) -> recalculateWidth(newValue));
        scene.heightProperty().addListener((observable, oldValue, newValue) -> recalculateHeight(newValue.doubleValue() - settingsPanel.getHeight()));
//        case KeyEvent.VK_DOWN : engine.notifyDown(); break;
//        case KeyEvent.VK_RIGHT : engine.notifyRight(); break;
//        case KeyEvent.VK_UP : engine.notifyUp(); break;
//        case KeyEvent.VK_LEFT : engine.notifyLeft(); break;
//        case KeyEvent.VK_SPACE : engine.notifySpace(); break;
//        case KeyEvent.VK_B : engine.loadLevel(settingsPanel.getLevelNumber()); break;
//        case KeyEvent.VK_PAGE_UP : settingsPanel.setNextLevelNumber(); break;
//        case KeyEvent.VK_PAGE_DOWN : settingsPanel.setPrevLevelNumber(); break;
//        case KeyEvent.VK_ESCAPE : engine.exit(); break;
//        keyEventHandler = new QEventHandler();
        scene.setOnKeyPressed(keyEventHandler);     // FIXME
        stage.setScene(scene);
        stage.show();
    }

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

    @Override
    public void recalculateWidth(Number newValue) {
        level.recalculateWidth(newValue);
    }

    @Override
    public void recalculateHeight(Number newValue) {
        level.recalculateHeight(newValue);
    }

    public void setEventHandler(EventHandler<KeyEvent> eventHandler) {
        stage.getScene().setOnKeyPressed(eventHandler);
    }
}
