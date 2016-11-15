package org.torquemada.q.view.impl.squares;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.model.contract.ValidColor;
import org.torquemada.q.view.impl.Dynamic;

public class Ball extends ColorfulSquare {

    private boolean selected = false;
    private boolean selectedToMove = false;
    @Getter
    protected Marble marble;

    @Autowired
    private Dynamic dynamic;

    public Ball() {
        super("/empty.png");
    }

    public void select(boolean select) {
        this.selected = select;
        selectedToMove = false;
    }

    public void selectToMove() {
        selectedToMove = !selectedToMove;
    }

    public boolean isReadyToMove() {
        return selectedToMove;
    }

    @Override
    public ColorfulSquare withColor(ValidColor color) {
        super.withColor(color);
        marble = new Marble(dynamic, color.getPlatformColor());
        return this;
    }
}
