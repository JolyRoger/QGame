package org.torquemada.q.view.impl.squares;

import org.torquemada.q.view.contract.IParent;

public class Empty extends Square {

    public Empty(IParent pane) {
        super("/empty.png", pane);
    }
}
