package org.torquemada.q.view.contract;

import org.torquemada.q.model.contract.ValidColor;

/**
 * Created by torquemada on 18.11.16.
 * Implements to set color if An element is colorful.
 */
public interface IColor {
    IColor withColor(ValidColor color);
}
