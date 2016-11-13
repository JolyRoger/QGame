package org.torquemada.q.view.impl;

import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.view.contract.IBoard;
import org.torquemada.q.view.contract.Resizable;
import org.torquemada.q.view.impl.squares.Ball;
import org.torquemada.q.view.impl.squares.Marble;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by torquemada on 11.11.16.
 * This is a panel for move ball and other effects.
 */
public class Dynamic extends Pane implements Resizable {

    private List<Ball> allBalls;
    @Autowired
    private IEngine engine;

    public Dynamic() {
        super();
        allBalls = new ArrayList<>();
    }

    public void add(Ball ball) {
        allBalls.add(ball);
    }

    @Override
    public void recalculateWidth(Number newValue) {
        setWidth(newValue.doubleValue());
        allBalls.forEach(ball -> {
            Marble marble = ball.getMarble();
            int colAmount = engine.getColAmount();
            double newDoubleValue = newValue.doubleValue();
            double squareWidth = newDoubleValue / colAmount;
            double x = squareWidth * ball.getCol() + squareWidth / 2;
            marble.setLocation(x, marble.getLocation().y);
            marble.setScaleX(newDoubleValue / (colAmount * IBoard.SQUARE_SIZE));
            marble.display();
        });
    }

    @Override
    public void recalculateHeight(Number newValue) {
        setHeight(newValue.doubleValue());
        allBalls.forEach(ball -> {
            Marble marble = ball.getMarble();
            int rowAmount = engine.getRowAmount();
            double newDoubleValue = newValue.doubleValue();
            double squareHeight = newDoubleValue / rowAmount;
            double y = squareHeight * ball.getRow() + squareHeight / 2;
            marble.setLocation(marble.getLocation().x, y);
            marble.setScaleY(newDoubleValue / (rowAmount * IBoard.SQUARE_SIZE));
            marble.display();
        });
    }
}
