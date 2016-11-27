package org.torquemada.q.view.impl.squares;

import javafx.scene.Node;
import org.torquemada.q.view.contract.IParent;

public class Empty extends Square {

    public Empty(IParent pane) {
        super("/empty.png", pane);
    }

    @Override
    public void recalculateWidth(Number newValue) {
        getChildren().stream().filter(node -> node instanceof Marble).forEach(node -> {
            ((Marble) node).recalculateWidth(newValue);
        });
        super.recalculateWidth(newValue);
    }

    @Override
    public void recalculateHeight(Number newValue) {
        getChildren().stream().filter(node -> node instanceof Marble).forEach(node -> {
            ((Marble) node).recalculateHeight(newValue);
        });
        super.recalculateHeight(newValue);
    }

}
