package net.trpfrog.medipro_game.mini_game.galaxy_express;

import net.trpfrog.medipro_game.mini_game.galaxy_express.symbols.Train;
import net.trpfrog.medipro_game.pause.EscapeToPause;
import net.trpfrog.medipro_game.scene.GameController;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GalaxyExpressController extends GameController implements KeyListener {

    private GalaxyExpressModel model;
    private GalaxyExpressView view;
    private final Timer timer = new Timer(10,null);
    private Train train;

    public GalaxyExpressController(GalaxyExpressModel model, GalaxyExpressView view){
        super(model,view);
        this.model = model;
        this.view = view;
        this.train = model.getTrain();
        view.addKeyListener(new EscapeToPause());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

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
