package org.torquemada.q.controller.contract;

/**
 * Created by torquemada on 6/2/16.
 * Common game engine interface. To handle a game.
 */
public interface IEngine {
    void run();

    void notifyLeft();
    void notifyUp();
    void notifyRight();
    void notifyDown();
    void notifySpace();

    int getRowAmount();
    int getColAmount();
    int getCurrentLevel();

    void reloadLevel();
    void loadLevel(int levelNumber);

    void exit();
}
