package org.torquemada.q.view.impl;

import org.torquemada.javafx.sample.Layer;
import org.torquemada.q.view.contract.Resizable;
import org.torquemada.q.view.impl.squares.Ball;
import org.torquemada.q.view.impl.squares.Marble;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by torquemada on 11.11.16.
 * This is a panel for move ball and other effects.
 */
public class Dynamic extends /*Canvas*/Layer implements Resizable {

    private List<Ball> allBalls;

    public Dynamic() {
        super(384, 384);
        allBalls = new ArrayList<>();
        setOnMouseClicked(e -> {
            System.out.println(allBalls);
        });
    }

    public void add(Ball ball) {
        allBalls.add(ball);
    }

    @Override
    public void recalculateWidth(Number newValue) {
        setWidth(newValue.doubleValue());
        allBalls.forEach(ball -> {
            Marble marble = ball.getMarble();
            double x = ball.getRow() * 64 + 64/2;
            marble.setLocation(x, marble.getLocation().y);
            marble.display();
        });
    }

    @Override
    public void recalculateHeight(Number newValue) {
        setHeight(newValue.doubleValue());
        allBalls.forEach(ball -> {
            Marble marble = ball.getMarble();
            double y = ball.getCol() * 64 + 64/2;
            marble.setLocation(marble.getLocation().x, y);
            marble.display();
        });
    }
}
