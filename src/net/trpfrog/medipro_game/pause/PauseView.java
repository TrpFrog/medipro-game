package net.trpfrog.medipro_game.pause;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.awt.*;

public class PauseView extends GameView {

    MainView mainView = MainView.getInstance();

    public PauseView(GameModel model) {
        super(model);
        setBackground(new Color(0x55000000, true));
        setLayout(null);

        JPanel pauseWindow = new PauseWindow();
        int w = 300, h = 200;
        int x = (mainView.getWidth() - w) / 2;
        int y = (mainView.getHeight() - h) / 2;
        pauseWindow.setBounds(x, y, w, h);
        setOpaque(false);

        add(pauseWindow);

        JPanel shadowPane = new JPanel();
        shadowPane.setBounds(0,0,mainView.getWidth(), mainView.getHeight());
        shadowPane.setBackground(new Color(0, 0, 0, 0.2f));
        add(shadowPane);
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }
}
