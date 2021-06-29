import com.labyrix.game.MusicHandler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(GdxTestRunner.class)
public class TestMusicHandler {

    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    @Test
    public void MusicHandler_checkTitleScreenMusicWorking() {
        scheduledThreadPoolExecutor.schedule(new Runnable() {
            private final MusicHandler musicHandler = MusicHandler.getINSTANCE();
            public void run() {
                musicHandler.titleScreenMusic();
                Assert.assertSame(true,this.musicHandler.getMusic1().isLooping());
                Assert.assertSame(true,this.musicHandler.getMusic2().isLooping());
                Assert.assertSame(0.5f,this.musicHandler.getMusic1().getVolume());
                Assert.assertSame(0.5f,this.musicHandler.getMusic2().getVolume());
            }
        }, 1, TimeUnit.SECONDS);
    }

    @Test
    public void MusicHandler_checkLobbyScreenMusicWorking() {
        scheduledThreadPoolExecutor.schedule(new Runnable() {
            private final MusicHandler musicHandler = MusicHandler.getINSTANCE();
            public void run() {
                musicHandler.titleScreenMusic();
                Assert.assertSame(true,this.musicHandler.getMusic1().isLooping());
                Assert.assertSame(true,this.musicHandler.getMusic2().isLooping());
                Assert.assertSame(0.35f,this.musicHandler.getMusic1().getVolume());
                Assert.assertSame(0.35f,this.musicHandler.getMusic2().getVolume());
            }
        }, 1, TimeUnit.SECONDS);
    }

    @Test
    public void MusicHandler_checkJoinScreenMusicWorking() {
        scheduledThreadPoolExecutor.schedule(new Runnable() {
            private final MusicHandler musicHandler = MusicHandler.getINSTANCE();
            public void run() {
                musicHandler.titleScreenMusic();
                Assert.assertSame(true,this.musicHandler.getMusic1().isLooping());
                Assert.assertSame(true,this.musicHandler.getMusic2().isLooping());
                Assert.assertSame(0.4f,this.musicHandler.getMusic1().getVolume());
                Assert.assertSame(0.4f,this.musicHandler.getMusic2().getVolume());
            }
        }, 1, TimeUnit.SECONDS);
    }

    @Test
    public void MusicHandler_checkGameScreenMusicWorking() {
        scheduledThreadPoolExecutor.schedule(new Runnable() {
            private final MusicHandler musicHandler = MusicHandler.getINSTANCE();
            public void run() {
                musicHandler.titleScreenMusic();
                Assert.assertSame(true,this.musicHandler.getMusic1().isLooping());
                Assert.assertSame(true,this.musicHandler.getMusic2().isLooping());
                Assert.assertSame(0.6f,this.musicHandler.getMusic1().getVolume());
                Assert.assertSame(0.6f,this.musicHandler.getMusic2().getVolume());
            }
        }, 1, TimeUnit.SECONDS);
    }
}
