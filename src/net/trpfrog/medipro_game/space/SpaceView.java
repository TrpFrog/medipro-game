package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.galaxy_express.GalaxyExpressScene;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkScene;
import net.trpfrog.medipro_game.mini_game.race_game.RaceGameScene;
import net.trpfrog.medipro_game.mini_game.shooting_star.ShootingStarScene;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.space.symbols.BlackHole;
import net.trpfrog.medipro_game.space.symbols.MiniGameStar;
import net.trpfrog.medipro_game.space.ui.MouseTwinkleManager;
import net.trpfrog.medipro_game.space.map.SpaceMap2D;
import net.trpfrog.medipro_game.space.map.SpaceMapDrawer;
import net.trpfrog.medipro_game.space.symbols.EventStar;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.space.field_mini_game.zodiac.ZodiacSign;
import net.trpfrog.medipro_game.space.ui.IndicatorUI;
import net.trpfrog.medipro_game.space.ui.MiniMapUI;
import net.trpfrog.medipro_game.space.ui.SpeedIndicatorUI;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.util.MusicPlayer;

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
    private EventStar moonWorkStar,raceGameStar,shootingStarStar,galaxyExpressStar,blackhole;

    private SpaceMap2D spaceMap2D;
    private SpaceMapDrawer spaceMapDrawer;

    private MiniMapUI miniMap;
    private SpeedIndicatorUI speedIndicator;
    private IndicatorUI indicator;

    private MouseTwinkleManager mouseTwinkleManager;

    private int mapCenterX,mapCenterY;

    public SpaceView(SpaceModel model) {
        super(model);
        this.model = model;
        this.rocket = this.model.getRocket();
        spaceMap2D = this.model.getRocketFloorMap();

        setBGM(MusicPlayer.SPACE_GAME_THEME);

        // マップの中心座標を設定
        mapCenterX = spaceMap2D.getWidth()/2;
        mapCenterY = spaceMap2D.getHeight()/2;

        // ロケットをマップの真ん中に配置
        rocket.setX(mapCenterX);
        rocket.setY(mapCenterY);

        // ロケットのアタリ判定
        int[] xpoints = {-30,-20,-15, 0,30,60, 30,  0,-15,-20};
        int[] ypoints = {  0, 20, 40,25,20, 0,-20,-25,-40,-20};
        Polygon shape = new Polygon(xpoints,ypoints,xpoints.length);
        var hitbox = new RelativeHitBox(shape);
        rocket.setRelativeHitBox(hitbox);

        // EventStarの作成
        moonWorkStar      = new MiniGameStar(100, MoonsWorkScene.class);
        raceGameStar      = new MiniGameStar(100, RaceGameScene.class);
        shootingStarStar  = new MiniGameStar(100, ShootingStarScene.class);
        galaxyExpressStar = new MiniGameStar(100, GalaxyExpressScene.class);
        blackhole = new BlackHole(500);
        ZodiacSign.buildAndRegister(new Rectangle(500, 500, 2000, 2000),
                5, model.get3DMap().get2DMap(1));

        // マップにEventStarのSymbolを追加
        int rX = (int)(Math.random()*mapCenterX)+1;
        int rY = (int)(Math.random()*mapCenterY)+1;
        spaceMap2D.addSymbol(mapCenterX+rX,mapCenterY-rY,moonWorkStar);
        spaceMap2D.addSymbol(mapCenterX-rX,mapCenterY-rY,raceGameStar);
        spaceMap2D.addSymbol(mapCenterX+rX,mapCenterY+rY,shootingStarStar);
        spaceMap2D.addSymbol(mapCenterX-rX,mapCenterY+rX,galaxyExpressStar);
        spaceMap2D.addSymbol(300, 300, blackhole);

        miniMap = new MiniMapUI(model, 7, MiniMapUI.LOWER_RIGHT);
        speedIndicator = new SpeedIndicatorUI(model.getRocket(), SpeedIndicatorUI.LOWER_LEFT);
        indicator = new IndicatorUI(model);

        spaceMapDrawer = new SpaceMapDrawer(model);

        mouseTwinkleManager = new MouseTwinkleManager(this, rocket);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        spaceMapDrawer.draw(g2);

        rocket.accessToWarpSystem().draw(g2);

        miniMap.draw(g2);
//        rocket.getDrawer().draw(g2);
        speedIndicator.draw(g2);
        mouseTwinkleManager.draw(g2);
        indicator.draw(g2);
    }

    @Override
    public void suspend() {
        timer.stop();
        mouseTwinkleManager.suspend();
    }

    @Override
    public void resume() {
        timer.start();
        rocket.setSpeedPxPerSecond(0);
        mouseTwinkleManager.resume();
    }
}
