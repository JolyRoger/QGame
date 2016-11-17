package org.torquemada.q.view.contract;

import javafx.scene.layout.Pane;
import org.torquemada.q.view.impl.squares.Square;

/**
 * Created by torquemada on 17.11.16.
 */
public interface IParent extends Resizable {
    Pane getContainer();
    void add(Square square);
    void recalculateWidth(Number newValue);
    void recalculateHeight(Number newValue);
}
