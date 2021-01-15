package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkScene;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.space.map.SpaceMap2D;
import net.trpfrog.medipro_game.space.map.SpaceMapDrawer;
import net.trpfrog.medipro_game.space.symbols.EventStar;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.space.symbols.Star;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class SpaceView extends GameView{

    private SpaceModel model;
    private Image eventStarImage, bkgImage = Toolkit.getDefaultToolkit().getImage(Paths.get(".","resource","space_game","spaceMap.jpg").toString());
    private final int imageWidth  = 500;
    private final int imageHeight = 500;
    private MainView mainView = MainView.getInstance();
    private Rocket rocket;
    private Timer timer = new Timer(10, e->repaint());
    private EventStar eventStar;
    private SpaceMap2D spaceMap2D;
    private SpaceMapDrawer spaceMapDrawer;

    public SpaceView(SpaceModel model) {
        super(model);
        this.model = model;
        this.rocket = this.model.getRocket();
        rocket.setRelativeHitBox(RelativeHitBox.makeCircle(Math.max(rocket.getImageWidth(),rocket.getImageHeight())));
        spaceMap2D = this.model.getRocketFloorMap();
        eventStarImage = Toolkit.getDefaultToolkit().getImage(Paths.get(".","resource","space_game","EventStar.png").toString());
        eventStar = new EventStar(eventStarImage,60,new MoonsWorkScene());
        eventStar.setX(400); eventStar.setY(400);
        spaceMap2D.addSymbol((int)eventStar.getX(),(int)eventStar.getY(),eventStar);
        spaceMapDrawer = new SpaceMapDrawer(model);

    }
    private void drawBackground(Graphics g){
        int x0 = -(int)(rocket.getX()/2.0 - mainView.getWidth()/2.0);
        int y0 = -(int)(rocket.getY()/2.0 - mainView.getHeight()/2.0);
        x0 = (x0 % imageWidth + imageWidth) % imageWidth - imageWidth;
        y0 = (y0 % imageHeight + imageHeight) % imageHeight - imageHeight;
        for(int x = x0; x < mainView.getWidth(); x+= imageWidth){
            for(int y = y0; y < mainView.getHeight(); y+= imageHeight){
                g.drawImage(bkgImage,x,y,imageWidth,imageHeight,null);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g);
        spaceMapDrawer.draw(g2);
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
