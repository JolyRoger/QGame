package org.torquemada.q.view.contract;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.torquemada.q.view.impl.squares.Square;

/**
 * Created by torquemada on 17.11.16.
 * Parent interface for component containing game elements
 */
public interface IParent extends IResizable {
    Pane getContainer();
    void add(IChild... children);
    void recalculateWidth(Number newValue);
    void recalculateHeight(Number newValue);
}