package com.labyrix.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.kryonet.Client;
import com.labyrix.game.LabyrixMain;
import com.labyrix.game.NetworkModels.UncoverRequest;

import java.util.ArrayList;

public class Uncover {
    private final LabyrixMain labyrixMain;
    private Table table;
    private Skin skin;
    private Client client;
    private ArrayList<Player> otherplayers = new ArrayList<Player>();
    private ArrayList<Image> playerImages = new ArrayList<Image>();
    private ArrayList<TextField> textFields = new ArrayList<TextField>();

    public Uncover(Player player, ArrayList<Player> players, Client client){
        this.labyrixMain = LabyrixMain.getINSTANCE();
        this.client = client;

        skin = new Skin();
        this.skin.addRegions(labyrixMain.getAssets().get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", labyrixMain.getFontMedium());
        this.skin.load(Gdx.files.internal("ui/uiskins.json"));

        table = new Table(skin);
        table.setX(player.getPosition().x - 180);
        table.setY(player.getPosition().y);

        for (Player p : players) {
            if (p.getId() != player.getId()){
                otherplayers.add(p);
            }
        }
        for (Player p : otherplayers) {
            playerImages.add(new Image(p.getPlayerImage().getImg()));
            textFields.add(new TextField(p.getName(),skin));
        }

        for (Image i : playerImages) {
            addListener(i);
            table.add(i).size(Gdx.graphics.getWidth()/6 - 50,Gdx.graphics.getHeight()/4);
        }
        table.row();
        for (TextField t : textFields) {
            t.setAlignment(1);
            t.setDisabled(true);
            table.add(t).size(Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()/10);
        }
    }

    public void addListener(Image i){
        i.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                client.sendTCP(new UncoverRequest());
                System.out.println("Sent UncoverRequest");
                table.clear();
            }
        });
    }

    public Table getTable() {
        return table;
    }
}
