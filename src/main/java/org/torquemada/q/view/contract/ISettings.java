package org.torquemada.q.view.contract;

/**
 * Created by torquemada on 29.09.16.
 * Interface for settings panel.
 */
public interface ISettings extends IRegion {
    void init();
    void setLevelNumber(int levelNumber);
    void setNextLevelNumber();
    int getLevelNumber();
    void setPrevLevelNumber();
}
