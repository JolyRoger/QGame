package org.torquemada.q.view.impl.effect;

import javafx.animation.AnimationTimer;

public class ScaleEffect extends AbstractEffect {


    public ScaleEffect(double startScale, double endScale, long duration) {


        timer = new AnimationTimer() {

            private double scale = endScale - startScale;
            private double scalePerMsX = scale / duration;
            private double newScale;
            private boolean stopScale;
            private long currentTime;
            private double x,y = 0;

            @Override
            public void handle(long now) {
                if (currentTime == 0) {
                    x = component.getLocation().x;
                    y = component.getLocation().y;
                    component.setScaleX(startScale);
                    component.setScaleY(startScale);
                    currentTime = now;
                    return;
                }
                if (stopScale) {
                    ScaleEffect.this.stop();
                    return;
                }

                long timeElapsedMs = (now - currentTime) / 1000000;
                double newDeltaScale = timeElapsedMs * scalePerMsX;
                newScale = startScale + newDeltaScale;
                double stopScaleValue = (endScale - newScale) / newDeltaScale;
                stopScale = Double.isNaN(stopScaleValue) || stopScaleValue < 0;
                newScale = stopScale ? endScale : newScale;
//                component.setPrefWidth(64);
                double nx = x + (component.getWidth() / 4 *  (1 - newScale));
                double ny = y + (component.getHeight() / 4 *  (1 - newScale));
                System.out.format("%.2f\t%.2f\t%.3f\t%.3f\n", x, component.getWidth(), newScale, nx);
                component.setLocation(nx, ny);
                component.setScaleX(newScale);
                component.setScaleY(newScale);
                component.display();
            }
        };
    }
}
