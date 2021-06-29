package com.labyrix.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

public class MusicHandler {
    private static MusicHandler INSTANCE = null;
    private Music music1, music2;
    private ArrayList<FileHandle> fileHandles = new ArrayList<FileHandle>();
    private String[] musicFiles = new String[]{"music/mainTheme.wav","music/base.wav","music/fx1.wav","music/fx2.wav","music/fx3.wav","music/fx4.wav","music/fx5.wav"};

    private MusicHandler(){
        for(int audioFile = 0; audioFile<7;audioFile++){
            fileHandles.add(Gdx.files.internal(musicFiles[audioFile]));
        }
    }

    public static MusicHandler getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new MusicHandler();
        }
        return INSTANCE;
    }

    public Music getMusic1() {
        return music1;
    }

    public void setMusic1(Music music1) {
        this.music1 = music1;
    }

    public Music getMusic2() {
        return music2;
    }

    public void setMusic2(Music music2) {
        this.music2 = music2;
    }

    public void titleScreenMusic(){
        this.music1 = setSpecificMusic(this.fileHandles.get(0),0.5f);
        this.music2 = setSpecificMusic(this.fileHandles.get(1),0.5f);
        this.music1.play();
        this.music2.play();
    }

    public void lobbyScreenMusic(){
        setScreenMusic(1,4, 0.35f);
    }

    public void joinScreenMusic(){
        setScreenMusic(1,3, 0.4f);
    }

    public void gameScreenMusic(){
        setScreenMusic(0,6, 0.6f);
    }

    public void setScreenMusic(int majorNumber, int minorNumber, float volume){
        this.music1.stop();
        this.music2.stop();
        this.music1 = setSpecificMusic(this.fileHandles.get(majorNumber),volume);
        this.music2 = setSpecificMusic(this.fileHandles.get(minorNumber),volume);
        this.music1.play();
        this.music2.play();
    }

    private Music setSpecificMusic(FileHandle handle, float volume){
        Music music = Gdx.audio.newMusic(handle);
        music.setVolume(volume);
        music.setLooping(true);
        return music;
    }

    public void dispose(){
        this.music1.stop();
        this.music2.stop();
        this.music1.dispose();
        this.music2.dispose();
    }
}
