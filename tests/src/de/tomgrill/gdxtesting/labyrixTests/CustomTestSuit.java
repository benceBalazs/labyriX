package de.tomgrill.gdxtesting.labyrixTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        UnitTestExample.class,
        TestTrap.class,
        TestTrapEvent.class,
        TestBoard.class
})

        // chang paths:    "../android/assets/badlogic.jpg"

public class CustomTestSuit {

}
