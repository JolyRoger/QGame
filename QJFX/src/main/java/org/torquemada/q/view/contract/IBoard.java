package org.torquemada.q.view.contract;

/**
 * Created by torquemada on 6/2/16.
 */
public interface IBoard {
    int SQUARE_SIZE = 64;
    void initialize();
    void assignLevel(int number, int row, int col, int[] levelData);
    void select(int id, boolean select);
    void selectToMove(int selectedId);
    boolean isReadyToMove(int selectedId);
    void moveBall(int from, int to);
    void clearLevel();
}
