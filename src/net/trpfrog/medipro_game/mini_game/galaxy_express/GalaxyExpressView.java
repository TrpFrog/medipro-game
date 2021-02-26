package net.trpfrog.medipro_game.mini_game.galaxy_express;

import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.util.ResourceLoader;

import javax.swing.*;
import java.awt.*;

public class GalaxyExpressView extends GameView {

    private GalaxyExpressModel model;
    private final Timer paintTimer = new Timer(100, g->repaint());
    private final Image bgImage = ResourceLoader
            .readImage(".","resource","mini_game","galaxy_express","bkg1.jpg");

    public GalaxyExpressView(GalaxyExpressModel model) {
        super(model);
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g.drawImage(bgImage,0,0,null);
        for(MovableSymbol train: model.getTrains()){train.getDrawer().draw(g2);}
    }

    @Override
    public void suspend() {
        paintTimer.stop();
    }

    @Override
    public void resume() {
        paintTimer.start();
    }
}
