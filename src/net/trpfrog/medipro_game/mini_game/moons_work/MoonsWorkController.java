package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.pause.EscapeToPause;
import net.trpfrog.medipro_game.scene.GameController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MoonsWorkController extends GameController implements MouseMotionListener, KeyListener {

    private MoonsWorkModel model;
    private MoonsWorkView view;

    public MoonsWorkController(MoonsWorkModel model, MoonsWorkView view) {
        super(model, view);
        this.model = model;
        this.view  = view;
        view.addMouseMotionListener(this);
        view.addKeyListener(new EscapeToPause());
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        model.getMoon().setLocation(e.getX(), e.getY());
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
