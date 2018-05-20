package org.torquemada.q.view.contract;

/**
 * Created by torquemada on 6/2/16.
 * Common level interface.
 */
public interface ILevel extends IResizable {
    void setDimension(int row, int col);
    void setLevelData(int[] levelData);
    void select(int id, boolean select);
    void init();
    void selectToMove(int selectedId);
    void moveBall(int from, int to, boolean toLoose);
    boolean isReadyToMove(int selectedId);
    void clear();
}
