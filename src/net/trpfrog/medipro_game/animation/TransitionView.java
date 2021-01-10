package net.trpfrog.medipro_game.animation;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.awt.*;

public class TransitionView extends GameView {

    private Timer timer = new Timer(10, e -> repaint());

    private TransitionModel model;

    protected TransitionView(TransitionModel model) {
        super(model);
        this.model = model;
        setOpaque(false);
    }

    private void paintHole(Graphics g) {
        var mv = MainView.getInstance();
        var holeRect = model.getHoleRectangle();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, mv.getWidth(), holeRect.y);
        g.fillRect(0, 0, holeRect.x, mv.getHeight());
        g.fillRect(holeRect.x + holeRect.width, 0, holeRect.x, mv.getHeight());
        g.fillRect(0, holeRect.y + holeRect.height, mv.getWidth(), holeRect.y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var mv = MainView.getInstance();
        var holeRect = model.getHoleRectangle();

        boolean animationFinished;

        int speed = 6;
        if(model.isFadeOut()) {
            holeRect.grow(-speed, (int)(-speed * holeRect.height / (double)holeRect.width));
            animationFinished = holeRect.width <= -1000;
        } else {
            holeRect.grow(speed, (int)(speed * mv.getHeight() / (double) mv.getWidth()));
            int limitSideLength = Math.max(mv.getWidth(), mv.getHeight()) + 1000;
            animationFinished = Math.max(holeRect.width, holeRect.height) >= limitSideLength;
        }

        if(animationFinished) {
            model.transit();
        }

        paintHole(g);
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
