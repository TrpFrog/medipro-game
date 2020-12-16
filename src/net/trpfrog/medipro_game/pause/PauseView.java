package net.trpfrog.medipro_game.pause;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.awt.*;

public class PauseView extends GameView {

    public PauseView(GameModel model) {
        super(model);
        setBackground(new Color(0x55000000, true));

        setLayout(null);
        MainView mainView = MainView.getInstance();

        JPanel pauseWindow = new PauseWindow();
        int w = 300, h = 200;
        int x = (mainView.getWidth() - w) / 2;
        int y = (mainView.getHeight() - h) / 2;
        pauseWindow.setBounds(x, y, w, h);

        add(pauseWindow);
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }
}
