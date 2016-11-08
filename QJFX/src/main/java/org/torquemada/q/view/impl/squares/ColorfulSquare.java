package org.torquemada.q.view.impl.squares;


import javafx.scene.image.Image;
import org.torquemada.q.model.contract.ValidColor;

/**
 * Created by torquemada on 5/29/16.
 */
public abstract class ColorfulSquare extends Square {

    protected ValidColor color;

    public ColorfulSquare(Image image) {
        super(image);
    }

    public ColorfulSquare(String s) {
        super(s);
    }

    public ColorfulSquare withColor(ValidColor color) {
        this.color = color;
        return this;
    }
}
