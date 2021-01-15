package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;
import net.trpfrog.medipro_game.mini_game.GameOverWindow;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.*;
import net.trpfrog.medipro_game.scene.GameModel;

import java.awt.*;

public class MoonsWorkModel extends GameModel {

    private Point centerPoint;
    private Background background;
    private Rectangle circleDrawArea;
    private Moon moon;
    private Earth earth;
    private DefenceCounter counter;
    private RocketLiveCount rocketLiveCount;
    private MeteoriteManager meteoriteManager;
    private Alert alert;

    private boolean playing = true;

    public MoonsWorkModel() {
        MainView mv    = MainView.getInstance();
        centerPoint    = new Point(mv.getWidth()/2, mv.getHeight()/2);

        // 月の軌道を設定
        int r          = 200;
        circleDrawArea = new Rectangle(centerPoint.x - r, centerPoint.y - r, r * 2, r * 2);

        background       = new Background();
        moon             = new Moon(this);
        earth            = new Earth(this);
        meteoriteManager = new MeteoriteManager(this);
        counter          = new DefenceCounter(earth.getAbsoluteHitBox().getBounds());
        rocketLiveCount  = new RocketLiveCount(this);
        alert            = new Alert();

        addSymbol(moon);
        addSymbol(earth);
        addSymbol(counter);
        addSymbol(rocketLiveCount);
    }

    public void endGame() {
        playing = false;
        var window = new GameOverWindow("GAME OVER",
                getCounter().getValue(),
                new Color(0xB5762200, true));
        var scene = new DialogBackgroundScene(window, false);
        SceneManager.getInstance().push(scene);
    }

    public MeteoriteManager getMeteoriteManager() {
        return meteoriteManager;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public Rectangle getCircleDrawArea() {
        return circleDrawArea;
    }

    public Moon getMoon() {
        return moon;
    }

    public Earth getEarth() {
        return earth;
    }

    public DefenceCounter getCounter() {
        return counter;
    }

    public Background getBackground() {
        return background;
    }

    public RocketLiveCount getRocketLiveCount() {
        return rocketLiveCount;
    }

    public Alert getAlert() {
        return alert;
    }

    public boolean isPlaying() {
        return playing;
    }

    @Override
    public void suspend() {
        meteoriteManager.suspend();
        earth.suspend();
    }

    @Override
    public void resume() {
        meteoriteManager.resume();
        earth.resume();
    }
}

