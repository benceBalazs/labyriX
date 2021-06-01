package com.labyrix.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

public class MusicHandler {
    private static MusicHandler INSTANCE = null;
    private Music major, minor, majorTemp, minorTemp;
    private ArrayList<FileHandle> fileHandles = new ArrayList<>();
    private String[] musicFiles = new String[]{"music/mainTheme.wav","music/base.wav","music/fx1.wav","music/fx2.wav","music/fx3.wav","music/fx4.wav","music/fx5.wav"};

    private MusicHandler(){
        for(int audioFile = 0; audioFile<7;audioFile++){
            fileHandles.set(audioFile, Gdx.files.internal(musicFiles[audioFile]));
        }
    }

    public static MusicHandler getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new MusicHandler();
        }
        return INSTANCE;
    }

    public void titleScreenMusic(){
        this.major = setSpecificMusic(this.major,this.fileHandles.get(0),0.5f);
        this.minor = setSpecificMusic(this.minor,this.fileHandles.get(1),0.5f);
    }

    public void lobbyScreenMusic(){
        setScreenMusic(0,0);
    }

    public void optionScreenMusic(){
        setScreenMusic(0,0);
    }

    public void gameScreenMusic(){
        setScreenMusic(0,0);
    }

    public void setScreenMusic(int majorNumber, int minorNumber){
        transitionMusic(this.major,this.minor,setSpecificMusic(this.majorTemp,this.fileHandles.get(majorNumber),
                0.0f),setSpecificMusic(this.minorTemp,this.fileHandles.get(minorNumber),0.0f));
        this.major = this.majorTemp;
        this.minor = this.minorTemp;
        this.majorTemp.stop();
        this.minorTemp.stop();
        this.majorTemp.dispose();
        this.minorTemp.dispose();
    }

    private void transitionMusic(Music majorOld,Music minorOld,Music majorNew,Music minorNew){
        for (int i = 0; i < 100; i++) {
            if (majorOld.getVolume()>0.0f) majorOld.setVolume(majorOld.getVolume()-0.01f);
            if (minorOld.getVolume()>0.0f) minorOld.setVolume(minorOld.getVolume()-0.01f);

            if (majorNew.getVolume()<0.5f) majorNew.setVolume(majorNew.getVolume()+0.01f);
            if (minorNew.getVolume()<0.5f) minorNew.setVolume(minorNew.getVolume()+0.01f);
        }
        majorOld.stop();
        minorOld.stop();
    }

    private Music setSpecificMusic(Music music, FileHandle handle, float volume){
        music = Gdx.audio.newMusic(handle);
        music.setVolume(volume);
        music.setLooping(true);
        return music;
    }

    public void dispose(){
        this.major.dispose();
        this.minor.dispose();
        this.majorTemp.dispose();
        this.minorTemp.dispose();
    }
}
