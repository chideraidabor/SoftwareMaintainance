/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weapon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Aram
 */
public abstract class weapon {
    
    Image weaImg;
    ImageView imageView;
    
    Pane layer;
    
    protected double x;
    protected double y;
    protected double left;
    protected double right;
    protected double top;
    protected double bottom;
    
    public weapon(){}
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getLeft(){
        return left;
    }
    
    public double getRight(){
        return right;
    }
    
    public double getTop(){
        return top;
    }
    
    public double getBottom(){
        return bottom;
    }
    
    public double getImgWidth(){
        return weaImg.getWidth();
    }
    
    public double getImgHeight(){
        return weaImg.getHeight();
    }
    
    public ImageView getImageView(){
        return imageView;
    }
    
    public void setLayer(Pane nLayer){
        layer = nLayer;
        addToLayer();
    }
    
    public void updateUI() {
        imageView.setX(x);
        imageView.setY(y);
        
        left = this.getX();
        right = this.getX() + this.getImgWidth();
        top = this.getY();
        bottom = this.getY() + this.getImgHeight();
    }
    
    public void addToLayer() {
        layer.getChildren().add(imageView);
    }
    
    public void removeFromLayer() {
        layer.getChildren().remove(imageView);
    }
    
}
