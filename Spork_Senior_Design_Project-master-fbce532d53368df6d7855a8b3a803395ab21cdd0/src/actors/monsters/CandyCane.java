/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actors.monsters;

import java.awt.Toolkit;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mary Kutschke
 */
public class CandyCane extends MeleeMonster {
    public CandyCane(double nx, double ny){
        actorImg = new Image("/images/Candy-Cane-PNG-Clipart.png",
                             Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.1,
                             Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.1,
                             true, false);
        imageView = new ImageView(actorImg);
        
        if(nx > 0){
            x = nx;
        } else {
            x = 0.0;
        }
        
        if(ny > 0){
            y = ny;
        } else {
            y = 0.0;
        }
        
        top = getY();
        bottom = getY() + actorImg.getHeight();
        left = getX();
        right = getX() + actorImg.getWidth();
        
        setStats();
        
        setMoveBounds();
    }
    
    @Override
    public void move(){
        // Moves randomly
        if(collision || hitBound){
            changeDirection();
            setCollision(false);
        } else {
            // Move the same direction
        }
        
        super.move();
        super.checkBounds();
    }
    
    @Override
    public void changeDirection(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, 8); // Corresponds to directions in Direction enum in Actor.java
            
        switch(randomNum){
            case 0:
                setDy(-speed);
                break;
            case 1:
                setDy(-speed);
                setDx(speed);
                break;
            case 2:
                setDx(speed);
                break;
            case 3:
                setDy(speed);
                setDx(speed);
                break;
            case 4:
                setDy(speed);
                break;
            case 5:
                setDy(speed);
                setDx(-speed);
                break;
            case 6:
                setDx(-speed);
                break;
            case 7:
                setDy(-speed);
                setDx(-speed);
                break;
        }
    }
    
    @Override
    protected void setStats(){
        speed = 5;
        attack = 2.0;
        hp = 2.0;
        
        super.setStats();
    }
}
