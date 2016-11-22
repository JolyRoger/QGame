package org.torquemada.q.view.impl.squares;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.animation.Sprite;
import org.torquemada.q.controller.animation.Vector2D;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.model.contract.ValidColor;
import org.torquemada.q.view.contract.*;

/**
 * Created by torquemada on 13.11.16.
 * The animated ball on the board. Can move.
 */
public class Marble extends Sprite implements IChild, IColor {

    @Getter
    private int col, row;
    @Autowired
    private IEngine engine;
    @Autowired
    private SelectingFrame frame;

    public Marble() {
        super(new Vector2D(0,0), new Vector2D(10,10), new Vector2D(0,0),
                IBoard.SQUARE_SIZE, IBoard.SQUARE_SIZE);
    }

    @Override
    public Node createView(Paint color) {
        double location = (IBoard.SQUARE_SIZE - IBoard.MARBLE_SIZE) / 2;

        ImageView img = new ImageView(createMarbleImage(IBoard.MARBLE_SIZE, color));
        img.setX(location);
        img.setY(location);
        select(false);
/*
        Rectangle rect = new Rectangle(IBoard.SQUARE_SIZE, IBoard.SQUARE_SIZE, Color.TRANSPARENT);
        rect.setStrokeWidth(5);
        rect.setArcWidth(5);
        rect.setArcHeight(5);
        rect.setStroke(Color.GREEN);
*/
        return img;
    }

    public void select(boolean show) {
        if (show) {
            getChildren().add(frame.view());
            frame.view().toBack();
        } else {
            getChildren().remove(frame.view());
        }
    }

    private Image createMarbleImage(double radius, Paint fill) {
        Circle circle = new Circle(radius/2-1, Color.CHOCOLATE);
        circle.setStroke(Color.BLACK);
        circle.setFill(fill);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        WritableImage wi = new WritableImage((int) radius, (int) radius);
        circle.snapshot(parameters, wi);

        return wi;
    }

    /**
     * Move sprite towards target
     */
    @Override
    public void seek(Vector2D target) {
        Vector2D desired = Vector2D.subtract(target, location);
        desired.normalize();

        // The usual steering = desired - velocity
        Vector2D steer = Vector2D.subtract(desired, velocity);
        steer.limit(maxForce);

        applyForce(steer);
    }

    @Override
    public Marble withAddress(int col, int row) {
        this.col = col;
        this.row = row;
        return this;
    }

    @Override
    public void recalculateWidth(Number newValue) {
        int colAmount = engine.getColAmount();
        double newDoubleValue = newValue.doubleValue();
        double squareWidth = newDoubleValue / colAmount;
        double x = squareWidth * col + squareWidth / 2;
        setLocation(x, getLocation().y);
        setScaleX(newDoubleValue / (colAmount * IBoard.SQUARE_SIZE));
        display();
    }

    @Override
    public void recalculateHeight(Number newValue) {
        int rowAmount = engine.getRowAmount();
        double newDoubleValue = newValue.doubleValue();
        double squareHeight = newDoubleValue / rowAmount;
        double y = squareHeight * row + squareHeight / 2;
        setLocation(getLocation().x, y);
        setScaleY(newDoubleValue / (rowAmount * IBoard.SQUARE_SIZE));
        display();
    }

    @Override
    public Marble view() {
        return this;
    }

    @Override
    public Marble withColor(ValidColor color) {
        Node view = createView(color.getPlatformColor());
        getChildren().add(view);
        return this;
    }

    public void go(int col, int row, boolean toLoose) {
        int divisor = 10;
        final double[] x = {getLocation().x};
        final double[] y = {getLocation().y};
        double targetX = col * getWidth() * getScaleX() + getWidth() * getScaleX() / 2;
        double targetY = row * getHeight() * getScaleY() + getHeight() * getScaleY() / 2;
        double remainedDistanceX = targetX - x[0];
        double remainedDistanceY = targetY - y[0];
        double incrementX = remainedDistanceX / divisor;
        double incrementY = remainedDistanceY / divisor;
        final boolean[] proceedX = {true};
        final boolean[] proceedY = {true};
        int from = this.row * engine.getColAmount() + this.col;
        int to = row * engine.getColAmount() + col;
        this.col = col;
        this.row = row;

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (proceedX[0]) {
                    x[0] += incrementX;
                    proceedX[0] = Math.abs(x[0] - targetX) > Math.abs(incrementX);
                }
                if (proceedY[0]) {
                    y[0] += incrementY;
                    proceedY[0] = Math.abs(y[0] - targetY) > Math.abs(incrementY);
                }
                setLocation(x[0], y[0]);
                if (!proceedX[0] && !proceedY[0]) {
                    setLocation(targetX, targetY);
                    stop();
                    if (toLoose) {
                        engine.ballInLoose(from, to);
                        frame.select(false);
                        setVisible(false);
                    }
                    frame.show(true);
                }
                display();
            }
        };

        gameLoop.start();
    }
}
