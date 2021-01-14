package net.trpfrog.medipro_game.mini_game.shooting_star;

import net.trpfrog.medipro_game.pause.EscapeToPause;
import net.trpfrog.medipro_game.scene.GameController;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShootingStarController extends GameController implements KeyListener, MouseListener {

    private ShootingStarModel model;
    private ShootingStarView  view;

    public ShootingStarController(ShootingStarModel model, ShootingStarView view) {
        super(model, view);
        this.model = model;
        this.view = view;

        // view に listener を add する
        view.addKeyListener(new EscapeToPause());
        view.addKeyListener(this);
        view.addMouseListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private boolean spaceKeyPressed = false;

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !spaceKeyPressed) {
            model.getGameTimer().start();
            model.addStar();
            spaceKeyPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            spaceKeyPressed = false;
        }
    }

    @Override
    public void suspend() {}

    @Override
    public void resume() {}

    @Override
    public void mouseClicked(MouseEvent e) {
        model.getGameTimer().start();
        model.addStar();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
