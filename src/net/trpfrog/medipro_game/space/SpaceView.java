package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.space.ui.MouseTwinkleManager;
import net.trpfrog.medipro_game.space.map.SpaceMap2D;
import net.trpfrog.medipro_game.space.map.SpaceMapDrawer;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.space.field_mini_game.zodiac.ZodiacSign;
import net.trpfrog.medipro_game.space.ui.IndicatorUI;
import net.trpfrog.medipro_game.space.ui.MiniMapUI;
import net.trpfrog.medipro_game.space.ui.SpeedIndicatorUI;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.util.MusicPlayer;
import net.trpfrog.medipro_game.util.ResourceLoader;

import javax.swing.*;
import java.awt.*;

public class SpaceView extends GameView{

    private SpaceModel model;
    private Image bkgImage = ResourceLoader.readImage(
            ".","resource","space_game","spaceMap.jpg"
    );
    private final int imageWidth  = 500;
    private final int imageHeight = 500;
    private MainView mainView = MainView.getInstance();
    private Rocket rocket;
    private Timer timer = new Timer(10, e->repaint());

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

        // 星座の登録
        ZodiacSign.buildAndRegister(new Rectangle(500, 500, 2000, 2000),
                12, model.get3DMap().get2DMap(1));


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
        rocket.getDrawer().draw(g2);
        mouseTwinkleManager.draw(g2);
        miniMap.draw(g2);
        speedIndicator.draw(g2);
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
