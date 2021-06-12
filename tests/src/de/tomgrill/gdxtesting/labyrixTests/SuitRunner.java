package de.tomgrill.gdxtesting.labyrixTests;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.labyrix.game.LabyrixMain;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import jdk.nashorn.internal.objects.Global;

public class SuitRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(CustomTestSuit.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
