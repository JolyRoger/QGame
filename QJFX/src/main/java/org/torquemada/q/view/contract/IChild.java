package org.torquemada.q.view.contract;

import javafx.scene.Node;

/**
 * Created by torquemada on 19.11.16.
 * Implements if an element could be added to a parent container.
 */
public interface IChild extends IAddress, IResizable {
    Node view();
}
