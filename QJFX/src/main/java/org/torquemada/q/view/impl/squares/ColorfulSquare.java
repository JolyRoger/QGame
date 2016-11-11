package org.torquemada.q.view.impl.squares;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.torquemada.q.model.contract.ValidColor;

/**
 * Created by torquemada on 5/29/16.
 * This is the game cell with a color.
 */
public abstract class ColorfulSquare extends Square {

    protected ValidColor color;
    private Canvas canvas;
    private GraphicsContext g;

    public ColorfulSquare(String image) {
        super(image);
        canvas = new Canvas();
        g = canvas.getGraphicsContext2D();
        getChildren().add(canvas);
        canvas.toBack();
    }

    public ColorfulSquare withColor(ValidColor color) {
        this.color = color;
        g.setFill(color.getPlatformColor());
        return this;
    }

    @Override
    public void recalculateWidth(Number newValue) {
        canvas.setWidth(newValue.doubleValue());
        g.fillRect(0,0,newValue.doubleValue(), canvas.getHeight());
        super.recalculateWidth(newValue);
    }

    @Override
    public void recalculateHeight(Number newValue) {
        canvas.setHeight(newValue.doubleValue());
        g.fillRect(0,0, canvas.getWidth(), newValue.doubleValue());
        super.recalculateHeight(newValue);

    }
}
