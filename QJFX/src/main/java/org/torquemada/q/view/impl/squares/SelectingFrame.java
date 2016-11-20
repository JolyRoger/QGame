package org.torquemada.q.view.impl.squares;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.torquemada.q.view.contract.IAddress;
import org.torquemada.q.view.contract.IBoard;
import org.torquemada.q.view.contract.IChild;

/**
 * Created by torquemada on 19.11.16.
 * The frame selecting a square with marble to be moved.
 */
public class SelectingFrame implements IChild {

    @Getter
    private Marble marble;
    private Canvas sf;
    @Getter
    private boolean select;

    public SelectingFrame() {
        sf = new Canvas(IBoard.SQUARE_SIZE, IBoard.SQUARE_SIZE);
        select(select);
    }

    public void setMarble(Marble marble) {
        if (this.marble != null) this.marble.select(false);
        marble.select(true);
        this.marble = marble;
    }

    public void select(boolean isRed) {
        select = isRed;
        GraphicsContext g = sf.getGraphicsContext2D();
        g.setLineWidth(10);
        g.setStroke(isRed ? Color.RED : Color.WHITE);
        g.strokeRoundRect(0,0,IBoard.SQUARE_SIZE, IBoard.SQUARE_SIZE, 25, 25);
    }

    @Override
    public Node view() {
        return sf;
    }

    @Override
    public IAddress withAddress(int col, int row) {
        return this;
    }

    @Override
    public int getCol() {
        return marble.getCol();
    }

    @Override
    public int getRow() {
        return marble.getRow();
    }

    @Override
    public void recalculateWidth(Number newValue) {

    }

    @Override
    public void recalculateHeight(Number newValue) {

    }

    public void show(boolean visible) {
        sf.setVisible(visible);
    }
}
