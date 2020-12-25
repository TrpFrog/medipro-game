package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkModel;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.symbol.Symbol;

import javax.swing.*;
import java.awt.*;

public class Moon extends Symbol {

    private MoonsWorkModel model;

    public Moon(MoonsWorkModel model) {
        this.model = model;
        int size = 15;
        setDrawer(g -> {
            g.setColor(Color.YELLOW);
            g.fillOval((int) getX() - size, (int) getY() - size, size * 2, size * 2);
        });
        setRelativeHitBox(RelativeHitBox.makeCircle(size));
    }

    @Override
    public void setLocation(double x, double y) {
        Point p = (Point) model.getCenterPoint().clone();

        double angle = Math.atan2(y - p.y, x - p.x);
        double r = model.getCircleDrawArea().getWidth() / 2;
        p.translate((int)(r * Math.cos(angle)), (int)(r * Math.sin(angle)));

        super.setLocation(p.x, p.y);
    }

}