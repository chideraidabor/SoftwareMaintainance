/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Weapon;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 *
 * @author Aram
 */
public class weapon {
    
    public static void main(String[] args){
        
    ArrayList<String> weaponsList = new ArrayList<String> ();
    
    weaponsList.add("weapon 1");
    weaponsList.add("weapon 2");
    weaponsList.add("weapon 3");
    
    displayArrayList (weaponsList);
    }
    
     public static void displayArrayList (ArrayList<String> tobedisp){
        for (int i = 0; i<= tobedisp.size(); i++){
            System.out.println(tobedisp.get(i));
        }
     }
}
