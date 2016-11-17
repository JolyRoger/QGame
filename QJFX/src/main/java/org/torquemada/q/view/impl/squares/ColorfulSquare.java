package org.torquemada.q.view.impl.squares;

import lombok.Getter;
import org.torquemada.q.model.contract.ValidColor;
import org.torquemada.q.view.contract.IParent;

/**
 * Created by torquemada on 5/29/16.
 * This is the game cell with a color.
 */
public abstract class ColorfulSquare extends Square {

    @Getter
    protected ValidColor color;

    public ColorfulSquare(String image, IParent pane) {
        super(image, pane);
    }

    @Override
    public void recalculateWidth(Number newValue) {
        super.recalculateWidth(newValue);
    }

    @Override
    public void recalculateHeight(Number newValue) {
        super.recalculateHeight(newValue);
    }

    public ColorfulSquare withColor(ValidColor color) {
        this.color = color;
        return this;
    }
}
