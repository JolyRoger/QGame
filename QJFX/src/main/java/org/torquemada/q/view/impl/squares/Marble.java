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
import org.torquemada.javafx.move.Sprite;
import org.torquemada.javafx.move.Vector2D;

/**
 * Created by torquemada on 13.11.16.
 * The animated ball on the board. Can move.
 */
public class Marble extends Sprite {

    public Marble(Pane layer, Color color) {
        super(layer, new Vector2D(0,0), new Vector2D(10,10), new Vector2D(0,0), 50, 50, color);
    }

    @Override
    public Node createView(Paint color) {
        return new ImageView(createMarbleImage(50d, color));
    }

    private Image createMarbleImage(double radius, Paint fill) {
        Circle circle = new Circle(24d, Color.CHOCOLATE);
        circle.setStroke(Color.BLACK);
        circle.setFill(fill);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        int imageWidth = (int) radius;
        int imageHeight = (int) radius;

        WritableImage wi = new WritableImage( imageWidth, imageHeight);
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
