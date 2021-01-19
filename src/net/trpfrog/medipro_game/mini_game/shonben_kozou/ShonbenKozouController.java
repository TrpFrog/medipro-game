package net.trpfrog.medipro_game.mini_game.shonben_kozou;

import net.trpfrog.medipro_game.pause.EscapeToPause;
import net.trpfrog.medipro_game.scene.GameController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ShonbenKozouController extends GameController implements MouseMotionListener, KeyListener {

    private ShonbenKozouModel model;
    private ShonbenKozouView view;

    public ShonbenKozouController(ShonbenKozouModel model, ShonbenKozouView view) {
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
