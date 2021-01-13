package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.space.map.SpaceMap2D;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.space.symbols.Star;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpaceView extends GameView{

    private SpaceModel model;
    private Image image, bkgImage = Toolkit.getDefaultToolkit().getImage(Paths.get(".","resource","space_game","spaceMap.jpg").toString());
    private Point centerPoint;
    private final int imageWidth  = 500;
    private final int imageHeight = 500;
    MainView mainView = MainView.getInstance();
    private Rocket rocket;
    private Timer timer = new Timer(10, e->repaint());
    private Star star;

    public SpaceView(SpaceModel model) {
        super(model);
        this.model = model;
        this.rocket = this.model.getRocket();
        centerPoint = new Point(mainView.getWidth()/2, mainView.getHeight()/2);
        SpaceMap2D currentMap = this.model.getRocketFloorMap();
        star = Star.getRandomStar();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // 背景の描写
        int x0 = (int)(rocket.getX()/2.0 - mainView.getWidth()/2.0);
        int y0 = (int)(rocket.getY()/2.0 - mainView.getHeight()/2.0);
        x0 = (x0 % imageWidth + imageWidth) % imageWidth - imageWidth;
        y0 = (y0 % imageHeight + imageHeight) % imageHeight - imageHeight;
        for(int x = x0; x < mainView.getWidth(); x+= imageWidth){
            for(int y = y0; y < mainView.getHeight(); y+= imageHeight){
                g.drawImage(bkgImage,x,y,imageWidth,imageHeight,null);
            }
        }
        rocket.getDrawer().draw(g2); // ロケットの描写
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
