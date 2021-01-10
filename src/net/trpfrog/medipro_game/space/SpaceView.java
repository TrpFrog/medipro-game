package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.space.map.SpaceMap2D;
import net.trpfrog.medipro_game.space.symbols.Rocket;

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

    public SpaceView(SpaceModel model) {
        super(model);
        this.model = model;
        this.rocket = this.model.getRocket();
        centerPoint = new Point(mainView.getWidth()/2, mainView.getHeight()/2);
        this.rocket.setX(mainView.getWidth()/2);
        this.rocket.setY(mainView.getHeight()/2);

        SpaceMap2D currentMap = this.model.getRocketFloorMap();
        currentMap.addSymbol(centerPoint,this.rocket);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        rocket.getDrawer().draw(g2);
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }
}
