package org.torquemada.q.view.impl.effect;

import javafx.animation.AnimationTimer;
import lombok.Setter;
import org.torquemada.q.controller.animation.Sprite;

public abstract class AbstractEffect {
    protected AnimationTimer timer;
    @Setter
    protected Sprite component;
    @Setter
    protected Runnable callback;

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
        callback.run();
    }

}
