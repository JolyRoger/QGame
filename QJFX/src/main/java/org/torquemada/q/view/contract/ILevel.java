package org.torquemada.q.view.contract;

/**
 * Created by torquemada on 6/2/16.
 */
public interface ILevel {
    void setDimension(int row, int col);
    void setLevelData(int[] levelData);
    void select(int id, boolean select);
    void init();
    void selectToMove(int selectedId);
    void moveBall(int from, int to);
    void removeBall(int from);
    boolean isReadyToMove(int selectedId);
    void clear();
}
