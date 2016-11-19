package org.torquemada.q.view.impl;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.view.contract.IChild;
import org.torquemada.q.view.contract.IParent;
import org.torquemada.q.view.impl.squares.Square;

import java.util.List;

/**
 * Created by torquemada on 17.11.16.
 * Static fields: looses, walls, and game field.
 */
public class Static implements IParent {

    private GridPane staticField;
    @Autowired
    private IEngine engine;

    public Static() {
        staticField = new GridPane();
    }

    @Override
    public Pane getContainer() {
        return staticField;
    }

    @Override
    public void add(List<? extends IChild> children) {
        children.forEach(square -> staticField.add(square.view(), square.getCol(), square.getRow()));
    }

    @Override
    public void recalculateWidth(Number newValue) {
        for (Node node : staticField.getChildren()) {
            Square square = (Square) node;
            square.recalculateWidth(newValue.intValue() / engine.getColAmount());
        }
    }

    @Override
    public void recalculateHeight(Number newValue) {
        for (Node node : staticField.getChildren()) {
            Square square = (Square) node;
            square.recalculateHeight(newValue.intValue() / engine.getRowAmount());
        }
    }
}
