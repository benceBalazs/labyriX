package com.labyrix.game;

import com.badlogic.gdx.Gdx;

public class MovementDefuse {
    int successCount;
    int failCount;
    //TODO Image anzeigen

    public MovementDefuse(){
        successCount = 0;
        failCount = 0;
    }

    public boolean dontMove() throws InterruptedException {
        double time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 10000) {
            if (successCount > 75) {
                successCount = 0;
                return true;
            }
            else if (failCount > 10){
                return false;
            }

            if (Gdx.input.getAccelerometerX() < 11 && Gdx.input.getAccelerometerY() < 2 && Gdx.input.getAccelerometerZ() < 2 && Gdx.input.getAccelerometerX() > 8.5 && Gdx.input.getAccelerometerY() > -2 && Gdx.input.getAccelerometerZ() > -2) {
                successCount += 1;
                System.out.println("Succ : " + successCount);
            } else {
                failCount += 1;
                System.out.println("Fail :" + failCount);
            }
            Thread.sleep(100);
        }
        successCount = 0;

        return false;
    }
}
