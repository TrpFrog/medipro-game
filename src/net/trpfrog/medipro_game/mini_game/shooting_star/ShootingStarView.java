package net.trpfrog.medipro_game.mini_game.shooting_star;

import net.trpfrog.medipro_game.mini_game.shooting_star.symbols.ShootingStar;
import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.awt.*;

public class ShootingStarView extends GameView {

    private ShootingStarModel model;
    private Timer paintTimer = new Timer(10, g -> repaint());

    public ShootingStarView(ShootingStarModel model) {
        super(model);
        this.model = model;
        setBackground(new Color(0x111111));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        model.getBackground().getDrawer().draw(g2);
        model.getScoreCounter().getDrawer().draw(g2);

        model.getStars().removeIf(e -> {
            if(e.isLooked(model.getCouple())) {
                return e.getSubtractCount().getAlpha() < 0 && e.isOutdated();
            } else {
                return e.isOutdated();
            }
        });
        model.getStars().forEach(e -> e.getDrawer().draw(g2));
        model.getCouple().getDrawer().draw(g2);

        model.getGameTimer().draw(g2);
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
