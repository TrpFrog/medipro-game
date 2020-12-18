package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkModel;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

public class Moon extends Symbol {
    public Moon(MoonsWorkModel model) {
        translate(0, model.getCircleDrawArea().getHeight() / 2);

        int size = 30;
        setDrawer(g -> {
            g.setColor(Color.YELLOW);
            g.fillOval((int) getX() - size / 2, (int) getY() - size / 2, size, size);
        });

        createHitJudgementRectangle(20, 20);
    }
}
