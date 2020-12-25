package net.trpfrog.medipro_game.mini_game.race_game;

import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.awt.*;

public class RaceGameView extends GameView {

    private RaceGameModel model;
    private Timer paintTimer = new Timer(10, g -> repaint());

    public RaceGameView(RaceGameModel model) {
        super(model);
        this.model = model;
        setBackground(new Color(0x111111));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 背景のトラックを描画
        model.getBackground().getDrawer().draw((Graphics2D) g);

        // 車を描画
        model.getCar().getDrawer().draw((Graphics2D) g);
    }


    // paint用のタイマーを制御
    @Override
    public void suspend() {
        paintTimer.stop();
    }

    @Override
    public void resume() {
        paintTimer.start();
    }
}
