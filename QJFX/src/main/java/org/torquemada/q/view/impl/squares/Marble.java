package org.torquemada.q.view.impl.squares;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.torquemada.javafx.move.Sprite;
import org.torquemada.javafx.move.Vector2D;
import org.torquemada.q.view.contract.IBoard;
import org.torquemada.q.view.contract.IParent;

/**
 * Created by torquemada on 13.11.16.
 * The animated ball on the board. Can move.
 */
public class Marble extends Sprite {

    private Canvas sf;
    private boolean selected = true;

    public Marble(IParent layer, Color color) {
        super(layer.getContainer(), new Vector2D(0,0), new Vector2D(10,10), new Vector2D(0,0),
                IBoard.SQUARE_SIZE, IBoard.SQUARE_SIZE, color);
    }

    @Override
    public Node createView(Paint color) {
        double location = (IBoard.SQUARE_SIZE - IBoard.MARBLE_SIZE) / 2;

        sf = new Canvas(IBoard.SQUARE_SIZE, IBoard.SQUARE_SIZE);
        GraphicsContext g = sf.getGraphicsContext2D();
        g.setLineWidth(10);
        g.setStroke(Color.RED);
        g.strokeRoundRect(0,0,IBoard.SQUARE_SIZE, IBoard.SQUARE_SIZE, 25, 25);

        ImageView img = new ImageView(createMarbleImage(IBoard.MARBLE_SIZE, color));
        img.setX(location);
        img.setY(location);

/*
        Rectangle rect = new Rectangle(IBoard.SQUARE_SIZE, IBoard.SQUARE_SIZE, Color.TRANSPARENT);
        rect.setStrokeWidth(5);
        rect.setArcWidth(5);
        rect.setArcHeight(5);
        rect.setStroke(Color.GREEN);
*/

        Pane pane = new Pane(sf, img/*, rect*/);

        pane.setOnMouseClicked(e -> select(!selected));
        return pane;
    }

    public void select(boolean show) {
        selected = show;
        sf.setVisible(show);
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
}
