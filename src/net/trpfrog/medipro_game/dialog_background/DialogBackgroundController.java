package net.trpfrog.medipro_game.dialog_background;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.scene.GameController;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * ダイアログの背景のController
 */
public class DialogBackgroundController extends GameController implements MouseListener {

    private DialogBackgroundModel model;
    private DialogBackgroundView view;

    public DialogBackgroundController(DialogBackgroundModel model, DialogBackgroundView view) {
        super(model, view);
        this.model = model;
        this.view = view;
        view.addMouseListener(this);
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!model.getDialogArea().contains(e.getPoint())) {
            SceneManager.getInstance().pop();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
