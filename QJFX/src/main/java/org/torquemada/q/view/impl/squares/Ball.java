package org.torquemada.q.view.impl.squares;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.torquemada.q.model.contract.ValidColor;
import org.torquemada.q.view.contract.IParent;

public class Ball extends ColorfulSquare {

    private boolean selected = false;
    private boolean selectedToMove = false;
    @Getter
    protected Marble marble;
    @Autowired @Qualifier("dynamicField")
    private IParent dynamicField;

    public Ball(IParent pane) {
        super("/empty.png", pane);
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
        return this;
    }
}
