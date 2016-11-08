package org.torquemada.q.view.contract;

/**
 * Created by torquemada on 29.09.16.
 */
public interface ISettings {
    void setLevelNumber(int levelNumber);
    void setNextLevelNumber();
    int getLevelNumber();
    void setPrevLevelNumber();
}
