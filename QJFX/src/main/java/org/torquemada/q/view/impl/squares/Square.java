package org.torquemada.q.view.impl.squares;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.q.view.contract.IChild;
import org.torquemada.q.view.contract.IParent;

/**
 * Created by torquemada on 5/28/16.
 * This class represents a game cell.
 */
public abstract class Square extends Pane implements IChild {

    @Getter
    protected int row, col;
    protected IParent parent;
    protected ImageView image;
    @Autowired
    private IParent staticField;

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

    @Override
    public Pane view() {
        return this;
    }
}
