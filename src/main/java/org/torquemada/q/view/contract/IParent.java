package org.torquemada.q.view.contract;

import javafx.scene.layout.Pane;

/**
 * Created by torquemada on 17.11.16.
 * Parent interface for component containing game elements
 */
public interface IParent extends IResizable {
    Pane getContainer();
    void add(IChild child);
    void remove(IChild marble);
    void recalculateWidth(Number newValue);
    void recalculateHeight(Number newValue);
}
