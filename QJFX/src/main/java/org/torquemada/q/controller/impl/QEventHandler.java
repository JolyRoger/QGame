package org.torquemada.q.controller.impl;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.view.contract.ISettings;

/**
 * Created by torquemada on 19.11.16.
 * Event handler for the game.
 */
public class QEventHandler implements EventHandler<KeyEvent> {

    @Autowired
    private IEngine engine;
    @Autowired
    private ISettings settingsPanel;

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                engine.notifySpace();
            } else if (keyEvent.getCode() == KeyCode.PAGE_UP) {
                settingsPanel.setNextLevelNumber();
            } else if (keyEvent.getCode() == KeyCode.PAGE_DOWN) {
                settingsPanel.setPrevLevelNumber();
            } else if (keyEvent.getCode() == KeyCode.B) {
                engine.loadLevel(settingsPanel.getLevelNumber());
            } else if (keyEvent.getCode() == KeyCode.UP) {
                engine.notifyUp();
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
                engine.notifyDown();
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                engine.notifyLeft();
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                engine.notifyRight();
            } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                engine.exit();
            }
            keyEvent.consume();
        }
    }
}
