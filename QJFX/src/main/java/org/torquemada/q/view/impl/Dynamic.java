package org.torquemada.q.view.impl;

import javafx.scene.layout.Pane;
import org.torquemada.q.view.contract.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by torquemada on 11.11.16.
 * This is a panel for move ball and other effects.
 */
public class Dynamic implements IParent {

    private Pane dynamicField;

    private List<IChild> marbles;

    public Dynamic() {
        super();
        marbles = new ArrayList<>();
        dynamicField = new Pane();
    }

    @Override
    public void recalculateWidth(Number newValue) {
        dynamicField.setPrefWidth(newValue.doubleValue());
        marbles.forEach(marble -> marble.recalculateWidth(newValue));
    }

    @Override
    public void recalculateHeight(Number newValue) {
        dynamicField.setPrefHeight(newValue.doubleValue());
        marbles.forEach(marble -> marble.recalculateHeight(newValue));
    }

    @Override
    public Pane getContainer() {
        return dynamicField;
    }

    @Override
    public void add(IChild... child) {
        for (IChild marble : child) {
            marbles.add(marble);
            dynamicField.getChildren().add(marble.view());
        }
    }
}
