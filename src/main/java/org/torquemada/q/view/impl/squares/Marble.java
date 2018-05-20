package org.torquemada.q.view.impl.squares;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.animation.Sprite;
import org.torquemada.q.controller.animation.Vector2D;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.model.contract.ValidColor;
import org.torquemada.q.view.contract.IBoard;
import org.torquemada.q.view.contract.IChild;
import org.torquemada.q.view.contract.IColor;
import org.torquemada.q.view.impl.Dynamic;
import org.torquemada.q.view.impl.effect.MoveEffect;

import java.util.List;

/**
 * Created by torquemada on 13.11.16.
 * The animated ball on the board. Can move.
 */
public class Marble extends Sprite implements IChild, IColor {

    @Getter @Setter
    private int col, row;
    @Autowired
    private IEngine engine;
    @Autowired
    private SelectingFrame frame;
    @Autowired
    private Dynamic dynamic;
    @Autowired
    private List<Square> squares;
    @Getter
    private double wwidth;
    @Getter
    private double hheight;

    public Marble() {
        super(new Vector2D(0,0), new Vector2D(10,10), new Vector2D(0,0), 0, 0);
    }

    @Override
    public Node createView(Paint color) {
        double location = (IBoard.SQUARE_SIZE - IBoard.MARBLE_SIZE) / 2;
        ImageView img = new ImageView(createMarbleImage(IBoard.MARBLE_SIZE, color));
        img.setX(location);
        img.setY(location);
        select(false);

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

    @Override
    public Marble withAddress(int col, int row) {
        this.col = col;
        this.row = row;
        return this;
    }

    @Override
    public void recalculateWidth(Number newValue) {
        double newDoubleValue = newValue.doubleValue();
        double scaleX = newDoubleValue / IBoard.SQUARE_SIZE;
        setScaleX(scaleX);
        wwidth = newDoubleValue;
        display();
    }

    @Override
    public void recalculateHeight(Number newValue) {
        double newDoubleValue = newValue.doubleValue();
        double scaleY = newDoubleValue / IBoard.SQUARE_SIZE;
        setScaleY(scaleY);
        hheight = newDoubleValue;
        display();
    }

    @Override
    public Marble withColor(ValidColor color) {
        Node view = createView(color.getPlatformColor());
        getChildren().add(view);
        return this;
    }

    public void go(int col, int row, Runnable callback) {
        double width = ((Pane) getParent()).getWidth() / engine.getColAmount();
        double height = ((Pane) getParent()).getHeight() / engine.getRowAmount();

        MoveEffect moveEffect = new MoveEffect(new Vector2D(getCol() * width, getRow() * height),
                                               new Vector2D(   col   * width,    row   * height), 100);

        setAnimationEffect(moveEffect);
        moveEffect.setCallback(callback);
        moveEffect.start();
    }

    @Override
    public Marble view() {
        return this;
    }
}
