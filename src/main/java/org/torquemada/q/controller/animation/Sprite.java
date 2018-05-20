package org.torquemada.q.controller.animation;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.torquemada.q.view.impl.effect.AbstractEffect;

public abstract class Sprite extends Region {

    protected Vector2D location;
    protected Vector2D velocity;
    protected Vector2D acceleration;

    protected double maxForce = Settings.SPRITE_MAX_FORCE;
    protected double maxSpeed = Settings.SPRITE_MAX_SPEED;

    Node view;

    // view dimensions
    double width;
    double height;
    double centerX;
    double centerY;

    double angle;

    Pane layer = null;

    public Sprite(Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        this.location = location;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.width = width;
        this.height = height;
        this.centerX = width / 2;
        this.centerY = height / 2;
        setPrefSize(width, height);
    }

    public Sprite(Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height, Paint color) {
        this(location, velocity, acceleration, width, height);
        this.view = createView(color);
        // add view to this node
        getChildren().add(view);
    }

    public Sprite(Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height, Paint color) {
        this(location, velocity, acceleration, width, height, color);
        this.layer = layer;
        // add this node to layer
        layer.getChildren().add( this);
    }

    public Sprite( Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        this(layer, location, velocity, acceleration, width, height, Color.BEIGE);
    }

    public Node createView(Paint color) { return null; }        // to be override

    public Node createView() { return null; };                  // to be override

    public void applyForce(Vector2D force) {
        acceleration.add(force);
    }

    public void move() {

        // set velocity depending on acceleration
//        System.out.println("V=" + velocity.x + ":" + velocity.y);
        velocity.add(acceleration);
//        System.out.println("V=" + velocity.x + ":" + velocity.y);

        // limit velocity to max speed
        velocity.limit(maxSpeed);

        // change location depending on velocity
        location.add(velocity);

        // angle: towards velocity (ie target)
        angle = velocity.heading2D();

        // clear acceleration
        acceleration.multiply(0);
    }

    /**
     * Move sprite towards target
     */
    public void seek(Vector2D target) {

        Vector2D desired = Vector2D.subtract(target, location);

        // The distance is the magnitude of the vector pointing from location to target.

        double d = desired.magnitude();
        desired.normalize();

        // If we are closer than 100 pixels...
        if (d < Settings.SPRITE_SLOW_DOWN_DISTANCE) {

            // ...set the magnitude according to how close we are.
            double m = Utils.map(d, 0, Settings.SPRITE_SLOW_DOWN_DISTANCE, 0, maxSpeed);
            desired.multiply(m);

        } 
        // Otherwise, proceed at maximum speed.
        else {
            desired.multiply(maxSpeed);
        }

        // The usual steering = desired - velocity
        Vector2D steer = Vector2D.subtract(desired, velocity);
        steer.limit(maxForce);

        applyForce(steer);

    }

    /**
     * Update node position
     */
    public void display() {

        relocate(location.x - centerX, location.y - centerY);

        setRotate(Math.toDegrees( angle));

    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getLocation() {
        return location;
    }

    public void setLocation( double x, double y) {
        location.x = x;
        location.y = y;
    }

    public void setAnimationEffect(AbstractEffect effect) {
        effect.setComponent(this);
    }

    public void setLocationOffset( double x, double y) {
        location.x += x;
        location.y += y;
    }

}