package com.labyrix.game.Defusions;

public class BombDefuse {
    String bombcode;
    String userinput;

    public BombDefuse(){
        for (int i = 0; i < 4; i++) {
            int a = (int)(Math.random()* 9);
            bombcode += a;
        }
    }
    
    public boolean defuseBomb(){
        //TODO Numpad with 9 Buttons
        return true;
    }
}
