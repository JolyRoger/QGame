package org.torquemada.q.view.contract;

/**
 * Created by torquemada on 6/2/16.
 * Interface of game board. Size constants.
 */
public interface IBoard {
    int SQUARE_SIZE = 64;
    float MARBLE_FACTOR = 1.28f;
    float MARBLE_SIZE = SQUARE_SIZE / MARBLE_FACTOR;

    void initialize();
    void assignLevel(int number, int row, int col, int[] levelData);
    void select(int id, boolean select);
    void selectToMove(int selectedId);
    boolean isReadyToMove(int selectedId);
    void moveBall(int from, int to);
    void clearLevel();
}
