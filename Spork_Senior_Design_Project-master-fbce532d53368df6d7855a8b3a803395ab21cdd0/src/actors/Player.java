/*
* SE Project: SPORK
* Authors: Kevin Kauffman, Glenn Sweithelm
* Character - Creates a singleton character and handles the stats for the
*             player's character created
* Change Log
* /////////////////////////////////////////////////////////////////////////////
* Date       Contributer    Change
* 18Jan18    Kevin          Seperated CharacterGUI and Character Class
* 31Jan18    Kevin          Determined Parameters and Added Checking to Setters
* 18Feb18    Kevin          Created Actor Superclass for Character & Monsters
* 19Feb18    Kevin          Added collision checking for obstacles.
* 12Mar18    Kevin          Added functionality to determine if actor is a player
* 22Mar18    Kevin          Overrode new changeDirection method
* 29Mar18    Kevin          Added setToCenter method
* 30Mar18    Kevin          Updated Stats Variable Types & Overrode New Methods
*                           Player Handles Progress & Exp Bar
*                           Updated Damage Functionality
* 31Mar18    Kevin          Monsters can't move until player does first
* 31Mar18    Glenn          Added Inventory
* 03Apr18    Kevin          Add functionality to change arenas
*                           Fixed Monster Bounce Error
*                           Added realistic stats
* 15Apr18    Kevin          Added Combat Functionality
*/

package actors;

import Items.Item;
import input.Input;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends Actor {
    //Instantiates Singleton
    private static Player player = new Player();
    
    private double exp;
    private String username;
    private String job; //These are classes (ex: Bar-pear-ian)
    
    public double minExp;
    public double maxExp;
    
    private Input input;
    
    private static ProgressBar healthBar = new ProgressBar(1F);
    private static ProgressBar xpBar = new ProgressBar(0F);
    
    private Image attackButtonReadyImage = new Image("/images/attackButtonReadySprite.png", getScreenWidth() * 0.1, getScreenHeight() * 0.1, true, false); 
    private Image attackButtonActiveImage = new Image("/images/attackButtonActiveSprite.png", getScreenWidth() * 0.1, getScreenHeight() * 0.1, true, false); 
    private ImageView attackButtonView = new ImageView(attackButtonActiveImage); 
    
    private static ArrayList<Item> inventory = new ArrayList(10);
    
    private boolean hasMoved = false;
    private boolean isFirst = true;
    private boolean canAttack = false;
    private boolean removeItem = false;
    private boolean checkAttack = false;
    
    private final Timer timer = new Timer();
    
    private Direction lastDir;
    
    // constructor for the Singleton. The stats will vary, so begins with nothing
    // Parameters will change as development continues
    private Player(){
        actorImg = new Image("/images/chefSprite.png", getScreenWidth() * 0.1, getScreenHeight() * 0.1, true, false);
        imageView = new ImageView(actorImg);
        
        setStats();
        
        setMoveBounds();
    }
    
    //singleton function to get instance
    public static Player getInstance(){
        return player;
    }
    
    //all getters and setters
    public String getUsername(){
        return username;
    }
    
    public double getExp(){
        return exp;
    }
    
    public double getMaxExp(){
        return maxExp;
    }
    public double getMinExp(){
        return minExp;
    }
    
    public Input getInput(){
        return input;
    }
    
    public ProgressBar getHpBar(){
        return healthBar;
    }
    
    public ProgressBar getExpBar(){
        return xpBar;
    }
    
    public ImageView getAttackView(){
        return attackButtonView;
    }

    //To Change Min and Max values, see variables at top of class.
    public void setExp(int number){
        if(number < minExp){
            exp = minExp;
        } else if (number > maxExp){
            exp = maxExp;
        } else {
            exp = number;
        }
    }

    public void setUsername(String name){
        username = name;
    }
    
    public void setInput(Input nInp){
        input = nInp;
    }
    
    public void processInput(){
        // Vertical
        if(input.isMoveUp()){
            setDy(-speed);
        } else if(input.isMoveDown()){
            setDy(speed);
        } else {
            setDy(0);
        }
        
        // Horizontal
        if(input.isMoveLeft()){
            setDx(-speed);
        } else if(input.isMoveRight()){
            setDx(speed);
        } else {
            setDx(0);
        }
        
        if(input.isAttacking() && canAttack){
            canAttack = false;
            showEquipped();
            stopMovement(); 
            timer.schedule(new TimerTask(){ 
                @Override 
                public void run(){ 
                    removeItem = true; 
                } 
            }, 1*1000/5); 
        }
    }
    
    public void setCanAttack(boolean nBool){
        canAttack = nBool;
    }
    
    public void updateButtons(){
        if(canAttack){
            attackButtonView.setImage(attackButtonReadyImage);
        } else {
            attackButtonView.setImage(attackButtonActiveImage);
        }
        
        if(inventory.isEmpty()){
            canAttack = false;
        }
        
        if(removeItem){
            hideEquipped();
        }
    }
    
    private void showEquipped(){
        Item equipped = inventory.get(0); 
        Image equippedImg = equipped.getImageView().getImage(); 
         
        direction(equipped, equippedImg);
         
        layer.getChildren().add(equipped.getImageView()); 
        
        checkAttack = true;
    }

    private void direction(Item equipped, Image equippedImg) {
        // TODO: This is going to need some updating - going to want to update the directions of the images
        switch(lastDir){
            case N:
                setHorizontal(equipped, equippedImg);
                break;
            case NE: 
                setVertical(equipped);
                break;
            case E: 
                setVertical(equipped);
                break;
            case SE: 
                setVertical(equipped);
                break;
            case S: 
                setHorizontal2(equipped);
                break;
            case SW: 
                setVertical2(equipped, equippedImg);
                break;
            case W: 
                setVertical2(equipped, equippedImg);
                break;
            case NW: 
                setVertical2(equipped, equippedImg);
                break;
            default:
                equipped.getImageView().setX(getX() + (getImage().getWidth() / 2.0));
                equipped.getImageView().setY(getY() - equippedImg.getHeight());
        }
    }

    private void setHorizontal2(Item equipped) {
        equipped.setHorizontal();
        equipped.getImageView().setX(getX() + (getImage().getWidth() / 2.0));
        equipped.getImageView().setY(getY() + getImage().getHeight());
    }

    private void setHorizontal(Item equipped, Image equippedImg) {
        equipped.setHorizontal();
        equipped.getImageView().setX(getX() + (getImage().getWidth() / 2.0));
        equipped.getImageView().setY(getY() - equippedImg.getHeight());
    }

    private void setVertical2(Item equipped, Image equippedImg) {
        equipped.setVertical();
        equipped.getImageView().setX(getX() - equippedImg.getWidth());
        equipped.getImageView().setY(getY() + (getImage().getHeight() / 2.0));
    }

    private void setVertical(Item equipped) {
        equipped.setVertical();
        equipped.getImageView().setX(getX() + getImage().getWidth());
        equipped.getImageView().setY(getY() + (getImage().getHeight() / 2.0));
    }
    
    private void hideEquipped(){
       layer.getChildren().remove(inventory.get(0).getImageView());
        canAttack = true;
        removeItem = false;
        startMovement();
        checkAttack = false;
    }
    
    public boolean getCheckAttack(){
        return checkAttack;
    }
    
    public void checkHit(Actor nMonst){
        if(inventory.get(0).getImageView().getBoundsInParent().intersects(nMonst.getImageView().getBoundsInParent())){
            nMonst.getDamagedBy(this);
            nMonst.bounce(checkDir(), getSpeed() * 10.0);
        }
    }
    
    @Override
    public void move(){
        super.move();
        
        if(!hasMoved){
            if ((Double.compare(getY(), getNewY()) != 0) ||
                (Double.compare(getX(), getNewX()) != 0)){
                hasMoved = true;
            }
        } // else do nothing
        
        super.checkBounds();
        
        if(checkDir() != Direction.NONE){
            lastDir = checkDir();
        }
    }
    
    @Override
    public void checkRemovability(){
        //Do nothing for now - Character will not be removed from arena
        //TODO: make game over / restart at check point
    }
    
    @Override
    public void changeDirection(){
        // Do nothing, movement is handled by the player's input
    }
    
    public void setToCenter(){
        setX(SCREEN_CENTER_X);
        setY(SCREEN_CENTER_Y);
    }
    private static final double SCREEN_CENTER_Y = getScreenHeight() * 0.5;
    private static final double SCREEN_CENTER_X = getScreenWidth() * 0.5;

    private static double getScreenHeight() throws HeadlessException {
        return Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    }

    private static double getScreenWidth() throws HeadlessException {
        return Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    }
    
    public void changeArena(Direction dir){
        switch(dir){
            case N:
                setX(getScreenWidth() * 0.45);
                setY(maxY);
                break;
            case E:
                setX(minX);
                setY(getScreenHeight() * 0.45);
                break;
            case S:
                setX(getScreenWidth() * 0.45);
                setY(minY);
                break;
            case W:
                setX(maxX);
                setY(getScreenHeight() * 0.45);
                break;
        }
        
        resetMoved();
    }
    
    @Override
    public boolean isPlayer(){
        return true;
    }
    
    @Override
    public boolean isMonster(){
        return false;
    }
    
    public static void updateHpBar(double amount){
        healthBar.setProgress(amount);
    }
   
   public static void updateXpBar(double amount){
        xpBar.setProgress(amount);
        if(xpBar.getProgress() >= 1){
            xpBar.setProgress(0); // Will need to determine how xp bar will behave when leveling up
        }
    }
   
    @Override
    public void getDamagedBy(Actor monster) {
        super.getDamagedBy(monster);
        updateHpBar(getHp()/getMaxHp());
    }
    
    @Override
    protected void setStats(){
        // Override min stats - these will be dependent on class eventually
        maxHp = 1000.0;
        minDef = 1.0;
        maxDef = 10.0;
        minAtt = 1.0;
        maxAtt = 1.0; // Attack will be based on weapon
        minExp = 0.0;
        maxExp = 10.0;
        
        hp = maxHp;
        defense = maxDef;
        attack = maxAtt;
        exp = minExp;
        username = "";
        job = "Chef"; // TODO: To be changed when we add more class
        speed = 10.0;
    }
    
    public boolean hasMoved(){
        return hasMoved;
    }
    
    public boolean isFirst(){
        return isFirst;
    }
    
    public void notFirst(){
        isFirst = false;
    }
    
    public void resetMoved(){
        hasMoved = false;
        isFirst = true;
        dx = x;
        dy = y;
    }
    
    public void addItem(Item nItem){
        if(inventory.size() < 12){
            inventory.add(nItem);
            nItem.removeFromLayer();
        } // else do nothing
    }
    
    public ArrayList<Item> getInventory(){
        return inventory;
    }
}


