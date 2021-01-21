package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.symbol.Symbol;
import net.trpfrog.medipro_game.util.TimeLimited;

import java.awt.*;

public class Twinkle extends Symbol implements TimeLimited {

    private int dx, dy;
    private TimeLimited.Impl limit;

    private Color randWhiterRGB() {
        return new Color(
                200 + (int)(55 * Math.random()),
                200 + (int)(55 * Math.random()),
                200 + (int)(55 * Math.random())
        );
    }

    public Twinkle(double x, double y, int duringMillis) {
        super(x, y);

        int range = 20;
        dx = (int)(- range + range * 2 * Math.random());
        dy = (int)(- range + range * 2 * Math.random());

        limit = new TimeLimited.Impl(duringMillis);

        setDrawer(g -> {
            if(isOutdated()) return;

            double passedTimeRatio = (getDeadline() - System.currentTimeMillis()) / (double)duringMillis;
            int l = (int)(10 * Math.sin(passedTimeRatio * Math.PI));
            g.setColor(randWhiterRGB());

            g.drawLine((int)getX() + dx - l,(int)getY() + dy,
                    (int)getX() + dx + l, (int)getY() + dy);
            g.drawLine((int)getX() + dx, (int)getY() + dy - l,
                    (int)getX() + dx, (int)getY() + dy + l);
        });
    }

    @Override
    public boolean isOutdated() {
        return limit.isOutdated();
    }

    @Override
    public long getDeadline() {
        return limit.getDeadline();
    }
}
