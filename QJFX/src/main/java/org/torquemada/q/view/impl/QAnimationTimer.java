package org.torquemada.q.view.impl;

import javafx.animation.AnimationTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.view.impl.squares.Marble;
import org.torquemada.q.view.impl.squares.SelectingFrame;

public class QAnimationTimer extends AnimationTimer {
    @Autowired
    private IEngine engine;
    @Autowired
    private SelectingFrame frame;
    private boolean toLoose;
    private Marble marble;
    private int from, to;
    private boolean proceedX, proceedY;
    private double x, y;
    private double incrementX, incrementY, targetX, targetY;

    public void calculate(Marble marble, boolean toLoose, int targetCol, int targetRow) {
        this.marble = marble;
        this.toLoose = toLoose;

        int divisor = 10;
        x = marble.getLocation().x;
        y = marble.getLocation().y;
        targetX = targetCol * marble.getWidth() * marble.getScaleX() + marble.getWidth() * marble.getScaleX() / 2;
        targetY = targetRow * marble.getHeight() * marble.getScaleY() + marble.getHeight() * marble.getScaleY() / 2;
        double remainedDistanceX = targetX - x;
        double remainedDistanceY = targetY - y;
        incrementX = remainedDistanceX / divisor;
        incrementY = remainedDistanceY / divisor;
        proceedX = true;
        proceedY = true;
        from = marble.getRow() * engine.getColAmount() + marble.getCol();
        to = targetRow * engine.getColAmount() + targetCol;
        marble.setCol(targetCol);
        marble.setRow(targetRow);
    }

    @Override
    public void handle(long now) {
        if (proceedX) {
            x += incrementX;
            proceedX = Math.abs(x - targetX) > Math.abs(incrementX);
        }
        if (proceedY) {
            y += incrementY;
            proceedY = Math.abs(y - targetY) > Math.abs(incrementY);
        }
        marble.setLocation(x, y);
        if (!proceedX && !proceedY) {
            marble.setLocation(targetX, targetY);
            stop();
            if (toLoose) {
                engine.ballInLoose(from, to);
                frame.select(false);
            }
            frame.show(true);
        }
        marble.display();
    }

}

