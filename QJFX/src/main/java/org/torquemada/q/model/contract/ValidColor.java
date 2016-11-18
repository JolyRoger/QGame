package org.torquemada.q.model.contract;

import javafx.scene.paint.Color;

/**
 * Created by torquemada on 6/2/16.
 * Possible colors of game marbles and looses.
 */
public enum ValidColor {
    BLUE, RED, WHITE, YELLOW, ORANGE, GREEN, ;

    public Color getPlatformColor() {
        switch (this) {
            case RED : return Color.RED;
            case WHITE : return Color.WHITE;
            case YELLOW : return Color.YELLOW;
            case ORANGE : return Color.ORANGE;
            case BLUE : return Color.BLUE;
            case GREEN : return Color.GREEN;
        }
        return Color.LIGHTGRAY;
    }
}
