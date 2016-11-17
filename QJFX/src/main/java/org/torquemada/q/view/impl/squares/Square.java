package org.torquemada.q.view.impl.squares;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import org.torquemada.q.view.contract.IParent;
import org.torquemada.q.view.contract.Resizable;

/**
 * Created by torquemada on 5/28/16.
 * This class represents a game cell.
 */
public abstract class Square extends Pane implements Resizable {

    @Getter
    protected int row, col;
    protected IParent parent;
    protected ImageView image;

    public Square(String image, IParent pane) {
        super();
        this.image = new ImageView(image);
        parent = pane;
        getChildren().add(this.image);
    }

    public Square withAddress(int col, int row) {
        this.col = col;
        this.row = row;
        return this;
    }

    @Override
    public void recalculateHeight(Number newValue) {
        image.setFitHeight(newValue.doubleValue());
    }

    @Override
    public void recalculateWidth(Number newValue) {
        image.setFitWidth(newValue.doubleValue());
    }

    public void setToParent() {
        parent.add(this);
    }
}
