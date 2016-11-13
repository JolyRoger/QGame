package org.torquemada.q.view.impl.squares;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import org.torquemada.q.view.contract.Resizable;

/**
 * Created by torquemada on 5/28/16.
 * This class represents a game cell.
 */
public abstract class Square extends Pane implements Resizable {

    @Getter
    protected int row, col;

    protected ImageView image;

    public Square(String image) {
        super();
        this.image = new ImageView(image);
        getChildren().add(this.image);
    }

    public Square withAddress(int row, int col) {
        this.row = row;
        this.col = col;
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
}
