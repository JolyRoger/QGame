package org.torquemada.q.view.impl;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.view.contract.Resizable;
import org.torquemada.q.view.impl.squares.Ball;
import org.torquemada.q.view.impl.squares.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by torquemada on 11.11.16.
 * This is a panel for move ball and other effects.
 */
public class Dynamic extends Canvas implements Resizable {

    private List<Ball> allBalls;
    private GraphicsContext g;

    public Dynamic() {
        super(384, 384);
        allBalls = new ArrayList<>();
        g = getGraphicsContext2D();
//        g.setFill(Color.GREEN);
//        g.fillOval(384-50,384-50-29,50,50);
        setOnMouseClicked(e -> {
            System.out.println(allBalls);
        });
    }

    public void add(Ball ball, int row, int col) {
//        ball.getColor();
        allBalls.add(ball);
    }

    @Override
    public void recalculateWidth(Number newValue) {
        setWidth(newValue.doubleValue());
        for (Ball ball : allBalls) {
            g.setFill(ball.getColor().getPlatformColor());
            g.fillOval(ball.getLayoutX(), ball.getLayoutY(),64, 64);
        }
    }

    @Override
    public void recalculateHeight(Number newValue) {
        setHeight(newValue.doubleValue());
        for (Ball ball : allBalls) {
            System.out.println("x=" + ball.getLayoutX() + " y=" + ball.getLayoutY());
            g.setFill(ball.getColor().getPlatformColor());
            g.fillOval(ball.getLayoutX(), ball.getLayoutY(),64, 64);
        }
    }
}
