package org.torquemada.q.view.impl;

import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.view.contract.*;
import org.torquemada.q.view.impl.squares.Marble;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by torquemada on 11.11.16.
 * This is a panel for move ball and other effects.
 */
public class Dynamic implements IParent {

    private Pane dynamicField;

    @Autowired
    private IEngine engine;

    private List<? extends IChild> marbles;
    private Marble marble;

    public Dynamic() {
        super();
        dynamicField = new Pane();
        marbles = new ArrayList<>();
    }

    @Override
    public void recalculateWidth(Number newValue) {
        dynamicField.setPrefWidth(newValue.doubleValue());
    }

    @Override
    public void recalculateHeight(Number newValue) {
        dynamicField.setPrefHeight(newValue.doubleValue());
    }

    @Override
    public Pane getContainer() {
        return dynamicField;
    }

    @Override
    public void add(IChild child) {
        dynamicField.getChildren().add(child.view());

        double fromX = child.getCol() * dynamicField.getWidth() / engine.getColAmount();
        double fromY = child.getRow() * dynamicField.getHeight() / engine.getRowAmount();

        this.marble = (Marble) child;       // TODO: change to interface
        marble.setLocation(fromX, fromY);
    }

    @Override
    public void remove(IChild marble) {
        dynamicField.getChildren().remove(marble.view());
    }
}
