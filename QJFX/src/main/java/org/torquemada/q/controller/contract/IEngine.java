package org.torquemada.q.controller.contract;

/**
 * Created by torquemada on 6/2/16.
 */
public interface IEngine {
    void run();

    void notifyLeft();
    void notifyUp();
    void notifyRight();
    void notifyDown();
    void notifySpace();

    void reloadLevel();
    void loadLevel(int levelNumber);

    void exit();
}
