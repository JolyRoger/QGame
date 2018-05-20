package org.torquemada.q.view.contract;

/**
 * Created by torquemada on 18.11.16.
 * Implements if an element knows about their row and column.
 */
public interface IAddress {
    IAddress withAddress(int col, int row);
    int getCol();
    int getRow();
}
