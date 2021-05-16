package com.labyrix.game.Defusions;

public class BombDefuse {
    private String bombcode;
    private String userinput;

    public BombDefuse(){
        for (int i = 0; i < 4; i++) {
            int a = (int)(Math.random()* 9);
            bombcode += a;
        }
    }
    
    public boolean defuseBomb(){
        
        return true;
    }
}
