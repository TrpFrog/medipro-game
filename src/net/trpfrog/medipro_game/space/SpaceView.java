package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.space.map.SpaceMap2D;
import net.trpfrog.medipro_game.space.symbols.Background;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.space.symbols.Star;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpaceView extends GameView {

    private SpaceModel model;
    private Image image;
    private Point centerPoint;
    private final int imageWidth  = 500;
    private final int imageHeight = 500;
    MainView mainView = MainView.getInstance();
    private Rocket rocket;
    private Timer timer = new Timer(10, e->repaint());
    private Star star; // 見えてる星をしまっておく配列
    private Background background;

    public SpaceView(SpaceModel model) {
        super(model);
        this.model = model;
        this.rocket = this.model.getRocket();
        centerPoint = new Point(mainView.getWidth()/2, mainView.getHeight()/2);
        SpaceMap2D currentMap = this.model.getRocketFloorMap();
        //currentMap.addSymbol(centerPoint,this.rocket);
        star = Star.getRandomStar();
        background = new Background();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        background.getDrawer().draw(g2);
        rocket.getDrawer().draw(g2);
        star.getDrawer().draw(g2);

    }

    @Override
    public void suspend() {
        timer.stop();
    }

    @Override
    public void resume() {
        timer.start();
    }
}
