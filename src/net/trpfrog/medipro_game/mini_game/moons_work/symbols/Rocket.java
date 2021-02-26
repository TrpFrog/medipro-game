package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.util.ResourceLoader;

import java.awt.*;

public class Rocket extends MovableSymbol implements Drawable {
    public static final Image ROCKET_IMG = ResourceLoader
                    .readImage(".","resource","mini_game","moons_work","rocket.png");

    private boolean returnedToEarth = false;
    private boolean leavingEarth = false;

    public boolean isReturnedToEarth() {
        return returnedToEarth;
    }

    public void setReturnedToEarth(boolean returnedToEarth) {
        this.returnedToEarth = returnedToEarth;
    }

    public boolean isLeavingEarth() {
        return leavingEarth;
    }

    public void setLeavingEarth(boolean leavingEarth) {
        this.leavingEarth = leavingEarth;
    }

    public Rocket(int x, int y) {
        super(x, y);
        setDrawer(this);
    }

    @Override
    public void draw(Graphics2D g) {
        Rectangle r = getRelativeHitBox().getBounds();
        r.translate((int) getX(), (int) getY());
        double cx = r.getCenterX();
        double cy = r.getCenterY();
        double angle = Math.toRadians(getAngleDegrees());
        g.rotate(angle, cx, cy);
        g.drawImage(ROCKET_IMG, r.x, r.y, r.width, r.height, null);
        g.rotate(-angle, cx, cy);
    }
}
