package org.torquemada.q.view.contract;

/**
 * Created by torquemada on 10.11.16.
 * For elements that could change their size.
 * Actually, all visible elements.
 */
public interface IResizable {
    void recalculateWidth(Number newValue);
    void recalculateHeight(Number newValue);
}
