package org.torquemada.q.view.impl.squares;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.torquemada.q.model.contract.ValidColor;

public class Loose extends ColorfulSquare {

    private GraphicsContext g;
    private Canvas canvas;

    public Loose(/*ValidColor color*/) {
        super("/empty.png");
        canvas = new Canvas();
        g = canvas.getGraphicsContext2D();
        getChildren().add(canvas);
        canvas.toBack();
    }

    @Override
    public Loose withColor(ValidColor color) {
//        g = canvas.getGraphicsContext2D();
        g.setFill(color.getPlatformColor());
        return (Loose) super.withColor(color);
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
