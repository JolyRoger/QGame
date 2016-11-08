package org.torquemada.q.view.impl.squares;

import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.model.contract.ValidColor;

public class Ball extends ColorfulSquare {

    @Autowired
    private IEngine engine;
    private boolean selected = false;
    private boolean selectedToMove = false;

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
}
