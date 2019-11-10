/*
 * SE Project: SPORK
 * Authors: Kevin Kauffman, Glenn Sweithelm
 * LevelOneRoomOne - Handles the functionality of the game
 * Change Log
 * ////////////////////////////////////////////////////////////////////////////
 * Date       Contributer    Change
 * 24jan18    Kevin          Changed from Animation_Demo to ArenaOne and created
 *                              background and adjusted boundaries.
 * 30Jan18    Kevin          Made Stage FullScreen & Fixed Boundaries
 * 06Feb18    Kevin          Made ball size and position dynamic
 *                           Added fixed, generic obstacle - to be dynamic later
 * 07Feb18    Kevin          Added get extremes function for circles - may need
 *                              updated later - and get bounds function for arena
 * 08Feb18    Kevin          Changed nested if to switch
 * 18Feb18    Kevin          Added sprite to game & updated movement
 * 19Feb18    Kevin          Added collision checking
 * 20Feb18    Glenn          Added a HUD for HP and XP
 * 06Mar18    Kevin          Added first monster
 * 06Mar18    Glenn          Added Escape Menu
 * 22Mar18    Kevin          Added Some Comments
 * 22Mar18    Kevin          Added new Monster (Donut)
 * 29Mar18    Kevin          Updated to LevelOneRoomOne & Moved Game Handling to
 *                              its own file
 *                           Also updated tabs to fit NetBeans standards
 * 30Mar18    Kevin          Updated Based on new Superclasses
 *                           Added new Damage Functionality
 *                           Added new Death Functionality
 * 31Mar18    Kevin          Fixed Healthbar Issue
 * 31Mar18    Glenn          Added Toothpick
 * 03Apr18    Kevin          Fixed shared lists error & added functionality to
 *                              change arenas.
 *                           Updated doorList with new rooms
 */

package arena.room;

import Items.Item;
import Items.Toothpick;
import obstacle.*;
import java.awt.Toolkit;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import actors.*;
import actors.monsters.*;
import arena.Arena;
import gameHandler.GameHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// May change this to simply "Room" when creating maps randomly and having a level subclass (i.e. there is one room class and it is filled randomly with stuff based on the level instead of having multiple room classes.
public class LevelOneRoomOne extends Arena{
    private boolean justStarting = true;
    private static LevelOneRoomOne roomOne = new LevelOneRoomOne();
    
    private enum Dir{
        TOP, BOTTOM, LEFT, RIGHT
    }
    
    public static LevelOneRoomOne getInstance(){
        return roomOne;
    }
    
    private LevelOneRoomOne(){}
    
    @Override
    public void start(Stage stage, Scene scene) {
        init();
        currScene = scene;
        root = new Pane();
        currScene.getStylesheets().add(LevelOneRoomOne.class.getResource("../ArenaOne.css").toExternalForm());
        
        setSettings(root, scene, stage);
        if(justStarting){
            player.setToCenter();
            justStarting = false;
        }
        setObjects(root);

        //Displaying the contents of the stage
        currStage.show();
        GameHandler.startGame();
    }
    
    @Override
    protected void init(){
        doorList[0] = RoomThree.getInstance();
        doorList[1] = RoomFive.getInstance();
        doorList[2] = NoRoom.getInstance();
        doorList[3] = RoomTwo.getInstance();
        
        monsList.clear();
        obsList.clear();
        itemList.clear();
        
        // Add Monsters - this will be changed to random in later iterations
        Actor gummiWorm = new GummiWorm((Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.30), (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.30));
        monsList.add(gummiWorm);
        Actor donut = new Donut((Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.50), (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.70));
        monsList.add(donut);
        Actor chocolate = new Chocolate((Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.60), (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.50));
        monsList.add(chocolate);
        
        // Add Obstacles - this will be changed to random in later iterations
        Obstacle cinRoll = new CinnamonRoll((Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.35), (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.50));
        obsList.add(cinRoll);
        Obstacle gumDrops = new GumDrops((Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.70), (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.30));
        obsList.add(gumDrops);
        
        // Add Items - this will be changed to random in later iterations
        Item toothPick = new Toothpick((Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.25), (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.25));
        itemList.add(toothPick);
        
        mp3MusicFile = new Media(LevelOneRoomOne.class.getResource("../Level1.mp3").toExternalForm()); 
        musicPlayer = new MediaPlayer(mp3MusicFile);
    }
}