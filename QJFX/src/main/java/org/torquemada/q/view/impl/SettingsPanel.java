package org.torquemada.q.view.impl;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.view.contract.ISettings;
import org.torquemada.q.view.contract.Resizable;

import javax.swing.*;

/**
 * Created by torquemada on 31.10.16.
 * Panel of controls to switch or reload level.
 */
public class SettingsPanel extends BorderPane implements ISettings, Resizable {

    private int levelNumber;
    private String[] levelModel;

    @Autowired
    private IEngine engine;

    public static final String SET_LEVEL_ID = "set.level.button";

    public SettingsPanel() {
    }

    void init() {
        Button reloadBtn = new Button("Перезагрузи уровень");
        reloadBtn.setId(SET_LEVEL_ID);
        setCenter(reloadBtn);
        ComboBox<Integer> cbox = new ComboBox<>();
        setRight(cbox);
    }

    @Override
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
        for (String ln : levelModel) {
            if (Integer.parseInt(ln) == levelNumber) {
//                SwingUtilities.invokeLater(() -> spinner.setValue(ln));
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
        return 1;
//        return levelNumber;
    }

    @Override
    public void setPrevLevelNumber() {
        setLevelNumber(levelNumber-1);
    }

    @Override
    public void recalculateHeight(Number newValue) {

    }

    @Override
    public void recalculateWidth(Number newValue) {
        for (Node node : getChildren()) {
            if (node != null && SET_LEVEL_ID.equals(node.getId())) {
                ((Button) node).setPrefWidth(newValue.intValue());
            }
        }
    }
}
