package net.trpfrog.medipro_game.mini_game.galaxy_express;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;
import net.trpfrog.medipro_game.mini_game.GameOverWindow;
import net.trpfrog.medipro_game.mini_game.galaxy_express.symbols.Station;
import net.trpfrog.medipro_game.mini_game.galaxy_express.symbols.Train;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.symbol.MovableSymbol;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GalaxyExpressView extends GameView {

    private GalaxyExpressModel model;
    private Timer paintTimer = new Timer(100, g->repaint());
    private Path path = Paths.get(".","resource","mini_game","galaxy_express","bkg1.jpg");
    private Image bgImage = Toolkit.getDefaultToolkit().getImage(path.toString());

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
