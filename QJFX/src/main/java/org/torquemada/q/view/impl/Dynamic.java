package org.torquemada.q.view.impl;

import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.view.contract.*;
import org.torquemada.q.view.impl.squares.Marble;

import javax.xml.ws.soap.Addressing;
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
//        marbles.forEach(marble -> marble.recalculateWidth(newValue));
    }

    @Override
    public void recalculateHeight(Number newValue) {
        dynamicField.setPrefHeight(newValue.doubleValue());
//        marbles.forEach(marble -> marble.recalculateHeight(newValue));
    }

    @Override
    public Pane getContainer() {
        return dynamicField;
    }

    @Override
    public void add(IChild child) {
        this.marble = (Marble) child;
        dynamicField.getChildren().add(marble.view());

        marble.setLocation(dynamicField.getWidth() / engine.getColAmount(), dynamicField.getHeight() / engine.getRowAmount());
        marble.display();
    }
}
