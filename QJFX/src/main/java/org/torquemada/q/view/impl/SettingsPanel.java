package org.torquemada.q.view.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.model.impl.Resources;
import org.torquemada.q.view.contract.IRegion;
import org.torquemada.q.view.contract.ISettings;

/**
 * Created by torquemada on 31.10.16.
 * Panel of controls to switch or reload level.
 */
public class SettingsPanel extends AnchorPane implements ISettings, IRegion {

    private int levelNumber;
    private Integer[] levelModel;
    private ComboBox<Integer> levelSelector;

    @Autowired
    private IEngine engine;

    public static final String SET_LEVEL_ID = "set.level.button";

    public SettingsPanel() {
        levelNumber = 1;
    }

    @Override
    public void init() {
        Button reloadBtn = new Button("Перезагрузи уровень");
        reloadBtn.setId(SET_LEVEL_ID);
        reloadBtn.setFocusTraversable(false);
        reloadBtn.setOnAction(event -> {
            engine.loadLevel(levelNumber);
        });

        levelModel = Resources.getAvailableLevelNumbersAsIntegerArray();
        ObservableList<Integer> options = FXCollections.observableArrayList(levelModel);
        levelSelector = new ComboBox<>(options);
        levelSelector.setFocusTraversable(false);
        levelSelector.setValue(levelNumber);
        levelSelector.setOnAction(event -> {
            levelNumber = (Integer) ((ComboBox) event.getSource()).getSelectionModel().getSelectedItem();
        });

        AnchorPane.setLeftAnchor(reloadBtn, 0d);
        AnchorPane.setRightAnchor(levelSelector, 0d);
        AnchorPane.setRightAnchor(reloadBtn, 50d);
        getChildren().addAll(reloadBtn, levelSelector);
    }

    @Override
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
        for (Integer ln : levelModel) {
            if (ln == levelNumber) {
                levelSelector.setValue(levelNumber);
                break;
            }
        }
    }

    @Override
    public void setNextLevelNumber() {
        setLevelNumber(levelNumber+1);
    }

    @Override
    public int getLevelNumber() {
        return levelNumber;
    }

    @Override
    public void setPrevLevelNumber() {
        setLevelNumber(levelNumber-1);
    }

    @Override
    public SettingsPanel region() {
        return this;
    }
}
