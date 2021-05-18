package com.labyrix.game.Models;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TextFieldFilter implements TextField.TextFieldFilter {

    private final char[] acceptedInputs;

    public TextFieldFilter() {
        acceptedInputs = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
    }

    @Override
    public boolean acceptChar(TextField textField, char c) {
        for (char toAccept : acceptedInputs){
            if (toAccept == c) return true;
        }
        return false;
    }
}

