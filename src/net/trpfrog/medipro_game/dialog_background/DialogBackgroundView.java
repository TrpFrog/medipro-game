package net.trpfrog.medipro_game.dialog_background;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.awt.*;

/**
 * ダイアログの背景のView
 */
public class DialogBackgroundView extends GameView {

    private MainView mainView = MainView.getInstance();
    private JPanel dialogPane;

    protected DialogBackgroundView(DialogBackgroundModel model, JPanel dialogPane) {
        super(model);

        this.dialogPane = dialogPane;

        setBackground(new Color(0x55000000, true));
        setLayout(null);
        setOpaque(false);
        setFocusable(true);

        int mainW   = MainView.getInstance().getWidth();
        int mainH   = MainView.getInstance().getHeight();
        Rectangle dialogArea = model.getDialogArea();

        dialogPane.setBounds(dialogArea);
        add(dialogPane);

        JPanel shadowPane = new JPanel();
        shadowPane.setBounds(0,0,mainView.getWidth(), mainView.getHeight());
        shadowPane.setBackground(new Color(0, 0, 0, 0.2f));
        add(shadowPane);
    }

    @Override
    public void suspend() {
        if(dialogPane instanceof Suspendable) ((Suspendable)dialogPane).suspend();
    }

    @Override
    public void resume() {
        if(dialogPane instanceof Suspendable) ((Suspendable)dialogPane).resume();
    }
}
