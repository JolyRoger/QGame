package org.torquemada.q.view.impl.effect;

import javafx.animation.AnimationTimer;
import org.torquemada.q.controller.animation.Vector2D;

/**
 * Created by torquemada on 27.11.16.
 * Effect, moves a sprite to a position.
 */
public class MoveEffect extends AbstractEffect {

    public MoveEffect(Vector2D from, Vector2D to, long duration) {

        timer = new AnimationTimer() {

            private double xDistance = to.x - from.x;
            private double yDistance = to.y - from.y;
            private double pixPerMsX = xDistance / duration;
            private double pixPerMsY = yDistance / duration;
            private double newX, newY;
            private boolean stopX, stopY;
            private long currentTime;

            @Override
            public void handle(long now) {
                if (currentTime == 0) {
                    component.setLocation(from.x, from.y);
                    currentTime = now;
                    return;
                }
                if (stopX && stopY) {
                    MoveEffect.this.stop();
                    return;
                }
                long timeElapsedMs = (now - currentTime) / 1000000;
                double newDistanceX = timeElapsedMs * pixPerMsX;
                double newDistanceY = timeElapsedMs * pixPerMsY;
                newX = from.x + newDistanceX;
                newY = from.y + newDistanceY;
                double stopXValue = (to.x - newX) / newDistanceX;
                double stopYValue = (to.y - newY) / newDistanceY;
                stopX = Double.isNaN(stopXValue) || stopXValue < 0;
                stopY = Double.isNaN(stopYValue) || stopYValue < 0;
                newX = stopX ? to.x : newX;
                newY = stopY ? to.y : newY;

                component.setLocation(newX, newY);
                component.display();
            }
        };
    }

}
