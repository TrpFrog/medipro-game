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
import net.trpfrog.medipro_game.space.ui.MiniMapUI;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;

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

    private MoonsWorkScene mwScene = new MoonsWorkScene();
    private RaceGameScene rgScene = new RaceGameScene();
    private ShootingStarScene ssScene = new ShootingStarScene();
    private GalaxyExpressScene geScene = new GalaxyExpressScene();

    public SpaceView(SpaceModel model) {
        super(model);
        this.model = model;
        this.rocket = this.model.getRocket();
        rocket.setRelativeHitBox(RelativeHitBox.makeCircle(60));
        spaceMap2D = this.model.getRocketFloorMap();
        // EventStarの作成
        moonWorkStar = new EventStar(mwScene.getStarImage(),60,mwScene);
        raceGameStar = new EventStar(rgScene.getStarImage(),60,rgScene);
        shootingStarStar = new EventStar(ssScene.getStarImage(),60,ssScene);
        galaxyExpressStar = new EventStar(geScene.getStarImage(),60,geScene);
        // マップにEventStarのSymbolを追加
        spaceMap2D.addSymbol(-300,0,moonWorkStar);
        spaceMap2D.addSymbol(300,0,raceGameStar);
        spaceMap2D.addSymbol(0,200,shootingStarStar);
        spaceMap2D.addSymbol(0,-200,galaxyExpressStar);

        miniMap = new MiniMapUI(model, 7, MiniMapUI.LOWER_RIGHT);

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
        miniMap.draw(g2);
    }

    @Override
    public void suspend() {
        model.getBGMClip().stop();
        timer.stop();
    }

    @Override
    public void resume() {
        model.getBGMClip().setFramePosition(0);
        model.getBGMClip().start();
        timer.start();
    }
}
