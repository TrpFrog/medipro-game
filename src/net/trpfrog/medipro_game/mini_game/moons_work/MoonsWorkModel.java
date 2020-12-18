package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.Earth;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.ExplosionAnimation;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.Moon;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.symbol.ImageAnimationSymbol;
import net.trpfrog.medipro_game.symbol.Symbol;
import net.trpfrog.medipro_game.util.GifConverter;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Thread.sleep;

public class MoonsWorkModel extends GameModel {

    private Point centerPoint;
    private Rectangle circleDrawArea;
    private Symbol moon;
    private Earth earth;
    private MeteoriteManager meteoriteManager;

    private Timer peaceCheckTimer;
    private ImageAnimationSymbol finalEarthExplosion;

    public MoonsWorkModel() {
        MainView mv    = MainView.getInstance();
        centerPoint    = new Point(mv.getWidth()/2, mv.getHeight()/2);

        // 月の軌道を設定
        int r          = (int)(Math.min(mv.getWidth()/2, mv.getHeight()/2) * 0.6);
        circleDrawArea = new Rectangle(centerPoint.x - r, centerPoint.y - r, r * 2, r * 2);

        moon  = new Moon(this);
        earth = new Earth(this);
        meteoriteManager = new MeteoriteManager(this);

        // 地球の安全を見守るタイマー
        peaceCheckTimer = new Timer(100, e -> checkPeaceOfEarth());

        // 動作に時間がかかるので、地球爆破アニメーションをここで初期化
        Path gifPath = Paths.get(".","resource","mini_game","moons_work","explosion-2.gif");
        finalEarthExplosion = new ImageAnimationSymbol(GifConverter.toImageList(gifPath));

        addSymbol(moon);
        addSymbol(earth);
        addSymbol(finalEarthExplosion);
    }

    public MeteoriteManager getMeteoriteManager() {
        return meteoriteManager;
    }

    // 地球に隕石が当たっていないかを確認し、当たっていたら爆破します
    private void checkPeaceOfEarth() {
        if(meteoriteManager.getObstacles().stream().anyMatch(e -> earth.isTouched(e))){
            // ここでタイマーを止めないと2度実行してしまう
            peaceCheckTimer.stop();
            meteoriteManager.suspend();
            explodeTheEarth();
        }
    }

    // 地球を爆破します
    private void explodeTheEarth() {
        MainView mv = MainView.getInstance();

        // 爆発アニメーションの設定
        finalEarthExplosion.setFps(10);
        finalEarthExplosion.setLocation(centerPoint.x, centerPoint.y);
        finalEarthExplosion.createHitJudgementRectangle(mv.getWidth(), mv.getHeight());
        finalEarthExplosion.start();

        // 8秒後に爆破
        Timer t = new Timer(8000, e -> SceneManager.getInstance().pop());
        t.setRepeats(false);
        t.start();
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public Rectangle getCircleDrawArea() {
        return circleDrawArea;
    }

    public Symbol getMoon() {
        return moon;
    }

    @Override
    public void suspend() {
        meteoriteManager.suspend();
        peaceCheckTimer.stop();
    }

    @Override
    public void resume() {
        meteoriteManager.resume();
        peaceCheckTimer.start();
    }
}

