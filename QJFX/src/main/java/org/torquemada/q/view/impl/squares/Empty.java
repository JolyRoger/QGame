package org.torquemada.q.view.impl.squares;

import org.torquemada.q.view.contract.IParent;

public class Empty extends Square {

    public Empty(IParent pane) {
        super("/empty.png", pane);
    }

    @Override
    public void recalculateWidth(Number newValue) {
        super.recalculateWidth(newValue);
        getChildren().stream().filter(node -> node instanceof Marble).forEach(node -> {
            ((Marble) node).recalculateWidth(newValue);
        });
    }

    @Override
    public void recalculateHeight(Number newValue) {
        super.recalculateHeight(newValue);
        getChildren().stream().filter(node -> node instanceof Marble).forEach(node -> {
            ((Marble) node).recalculateHeight(newValue);
        });
    }

}
