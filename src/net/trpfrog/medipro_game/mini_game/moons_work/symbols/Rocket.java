package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
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
        initHitBox();
    }

    private void initHitBox() {
        int[] xPoints = {-30,-20,-15, 0,30,60, 30,  0,-15,-20};
        int[] yPoints = {  0, 20, 40,25,20, 0,-20,-25,-40,-20};
        for(int i = 0; i < xPoints.length; i++) xPoints[i] = xPoints[i] * 7 / 10;
        for(int i = 0; i < yPoints.length; i++) yPoints[i] = yPoints[i] * 7 / 10;
        Polygon shape = new Polygon(xPoints, yPoints, xPoints.length);
        var hitBox = new RelativeHitBox(shape);
        setRelativeHitBox(hitBox);
    }

    @Override
    public void draw(Graphics2D g) {
        Rectangle r = new Rectangle(80, 50);
        r.translate(-r.width / 2, -r.height / 2);
        r.translate((int) getX(), (int) getY());
        double cx = r.getCenterX();
        double cy = r.getCenterY();
        double angle = Math.toRadians(getAngleDegrees());
        g.rotate(angle, cx, cy);
        g.drawImage(ROCKET_IMG, r.x, r.y, r.width, r.height, null);
        g.rotate(-angle, cx, cy);
    }
}
