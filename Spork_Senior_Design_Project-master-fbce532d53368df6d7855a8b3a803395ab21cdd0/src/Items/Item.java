/*
 * SE Project: SPORK
 * Authors: Kevin Kauffman, Glenn Sweithelm
 * Item - Abstract superclass for items
 * Change Log
 * /////////////////////////////////////////////////////////////////////////////
 * Date       Contributer    Change
 * 31Mar18    Glenn          Initial Item Superclass created
 * 15Apr18    Kevin          Allowed items to be horizontal and Vertical
*/

package Items;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Item {
    protected Image itemHorizontalImg;
    protected Image itemVerticalImg;
    protected ImageView imageView;
    protected Pane layer;
    protected double attack;
    protected double x;
    protected double y;
    
    public ImageView getImageView(){
        return imageView;
    }
    
    public void updateUI() {
        imageView.setX(x);
        imageView.setY(y);
    }
    
    public void addToLayer() {
        layer.getChildren().add(imageView);
    }
    
    public void setLayer(Pane nLayer) {
        layer = nLayer;
        addToLayer();
    }
    
    public void removeFromLayer() {
        layer.getChildren().remove(imageView);
    }
    
    public void setHorizontal(){
        imageView.setImage(itemHorizontalImg);
    }
    
    public void setVertical(){
        imageView.setImage(itemVerticalImg);
    }
}
