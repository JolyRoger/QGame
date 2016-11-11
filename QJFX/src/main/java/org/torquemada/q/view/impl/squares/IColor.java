package org.torquemada.q.view.impl.squares;

import org.torquemada.q.model.contract.ValidColor;

/**
 * Created by torquemada on 11.11.16.
 */
public interface IColor {
    IColor withColor(ValidColor color);
    ValidColor getColor();
}
