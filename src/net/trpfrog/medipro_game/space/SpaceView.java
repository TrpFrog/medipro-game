package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.galaxy_express.GalaxyExpressScene;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkScene;
import net.trpfrog.medipro_game.mini_game.race_game.RaceGameScene;
import net.trpfrog.medipro_game.mini_game.shooting_star.ShootingStarScene;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.space.map.SpaceMap2D;
import net.trpfrog.medipro_game.space.map.SpaceMapDrawer;
import net.trpfrog.medipro_game.space.symbols.EventStar;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.space.symbols.zodiac.ZodiacSign;
import net.trpfrog.medipro_game.space.ui.IndicatorUI;
import net.trpfrog.medipro_game.space.ui.MiniMapUI;
import net.trpfrog.medipro_game.space.ui.SpeedIndicatorUI;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class SpaceView extends GameView{

    private SpaceModel model;
    private Image bkgImage = Toolkit.getDefaultToolkit().getImage(
            Paths.get(".","resource","space_game","spaceMap.jpg").toString());
    private final int imageWidth  = 500;
    private final int imageHeight = 500;
    private MainView mainView = MainView.getInstance();
    private Rocket rocket;
    private Timer timer = new Timer(10, e->repaint());
    private EventStar moonWorkStar,raceGameStar,shootingStarStar,galaxyExpressStar;
    private SpaceMap2D spaceMap2D;
    private SpaceMapDrawer spaceMapDrawer;
    private MiniMapUI miniMap;
    private SpeedIndicatorUI speedIndicator;
    private IndicatorUI indicator;
    private int mapCenterX,mapCenterY;

    public SpaceView(SpaceModel model) {
        super(model);
        this.model = model;
        this.rocket = this.model.getRocket();
        rocket.setRelativeHitBox(RelativeHitBox.makeCircle(60));
        spaceMap2D = this.model.getRocketFloorMap();

        mapCenterX = spaceMap2D.getWidth()/2;
        mapCenterY = spaceMap2D.getHeight()/2;

        // ロケットをマップの真ん中に配置
        rocket.setX(mapCenterX);
        rocket.setY(mapCenterY);

        // EventStarの作成
        moonWorkStar      = EventStar.createSceneTransitionStar(60, MoonsWorkScene.class);
        raceGameStar      = EventStar.createSceneTransitionStar(60, RaceGameScene.class);
        shootingStarStar  = EventStar.createSceneTransitionStar(60, ShootingStarScene.class);
        galaxyExpressStar = EventStar.createSceneTransitionStar(60, GalaxyExpressScene.class);
        ZodiacSign.buildAndRegister(new Rectangle(500, 500, 2000, 2000),
                12, model.get3DMap().get2DMap(1));

        // マップにEventStarのSymbolを追加
        int rX = (int)(Math.random()*mapCenterX)+1;
        int rY = (int)(Math.random()*mapCenterY)+1;
        spaceMap2D.addSymbol(mapCenterX+rX,mapCenterY-rY,moonWorkStar);
        spaceMap2D.addSymbol(mapCenterX-rX,mapCenterY-rY,raceGameStar);
        spaceMap2D.addSymbol(mapCenterX+rX,mapCenterY+rY,shootingStarStar);
        spaceMap2D.addSymbol(mapCenterX-rX,mapCenterY+rX,galaxyExpressStar);

        miniMap = new MiniMapUI(model, 7, MiniMapUI.LOWER_RIGHT);
        speedIndicator = new SpeedIndicatorUI(model.getRocket(), SpeedIndicatorUI.LOWER_LEFT);
        indicator = new IndicatorUI(model);

        spaceMapDrawer = new SpaceMapDrawer(model);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        spaceMapDrawer.draw(g2);
        miniMap.draw(g2);
        speedIndicator.draw(g2);
        indicator.draw(g2);
    }

    @Override
    public void suspend() {
        model.getBGMClip().stop();
        timer.stop();
    }

    @Override
    public void resume() {
        model.getBGMClip().setFramePosition(0);
        model.getBGMClip().loop(Clip.LOOP_CONTINUOUSLY);
        timer.start();
    }
}
