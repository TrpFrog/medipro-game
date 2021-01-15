package net.trpfrog.medipro_game.mini_game.galaxy_express;

import net.trpfrog.medipro_game.mini_game.galaxy_express.symbols.Station;
import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.awt.*;

public class GalaxyExpressView extends GameView {

    private GalaxyExpressModel model;
    private Station station1 = Station.getStation("UNKO");
    private Timer paintTimer = new Timer(10, g->repaint());

    public GalaxyExpressView(GalaxyExpressModel model) {
        super(model);
        this.model = model;
        setBackground(Color.pink);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        model.getTrain().getDrawer().draw(g2);
        station1.getDrawer().draw(g2);
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
