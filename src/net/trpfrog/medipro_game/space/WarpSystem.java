package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.awt.geom.Line2D;

public class WarpSystem implements Drawable {

    private final SpaceModel model;
    private final Rocket rocket;
    private boolean warped = false;
    private boolean warping = false;
    private boolean turned = false;

    private int x, y;
    double angle;
    private float alpha = 0;
    private int warpStarLength = 1;

    public WarpSystem(SpaceModel model, Rocket rocket) {
        this.model = model;
        this.rocket = rocket;
    }

    public boolean isWarped() {
        return warped;
    }

    public boolean isWarping() {
        return warping;
    }

    private void faceToWarpPoint() {
        rocket.setAngleDegrees(angle);
        if (turned) return;

        double dx = x - rocket.getX();
        double dy = y - rocket.getX();

        double toOtherAngleDegrees = Math.toDegrees(Math.atan2(dy, dx));
        double rocketToOtherAngleDegrees =
                ((toOtherAngleDegrees - rocket.getAngleDegrees()) % 360 + 360) % 360;

        double turnDt = 2;
        if (rocketToOtherAngleDegrees < 2 * turnDt) {
            rocket.faceTo(x, y);
            angle = rocket.getAngleDegrees();
            turned = true;
            return;
        }

        if (rocketToOtherAngleDegrees > 180) {
            angle = (angle - turnDt + 360) % 360;
        } else {
            angle = (angle + turnDt) % 360;
        }
        System.out.println(angle);
        alpha = Math.min(1, alpha + 0.05f);
    }

    private Line2D createLineStar(Symbol star) {
        var mv = MainView.getInstance();
        var line = new Line2D.Double(
                mv.getWidth() / 2.0,
                mv.getHeight() / 2.0,
                warpStarLength * rocket.calcSightLineX() + mv.getWidth() / 2.0,
                warpStarLength * rocket.calcSightLineY() + mv.getHeight() / 2.0);
        line.x1 += star.getX() - rocket.getX();
        line.y1 += star.getY() - rocket.getY();
        line.x2 += star.getX() - rocket.getX();
        line.y2 += star.getY() - rocket.getY();
        return line;
    }

    public WarpSystem setDestination(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public void warp() {
        if (warping) return;
        if (warpStarLength > 10000) {
            warping = false;
            model.resume();
        } else {
            warping = true;
            angle = rocket.getAngleDegrees();
            model.suspend();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (!warping) return;
        var mv = MainView.getInstance();
        var gStar = (Graphics2D) g.create();

        gStar.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        gStar.setColor(Color.BLACK);
        gStar.fillRect(0, 0, mv.getWidth(), mv.getHeight());

        faceToWarpPoint();
        rocket.setLocation(x, y);
        alpha = Math.max(0, alpha - 1 / 400f);

        if (alpha <= 2 / 10000f) {
            warping = false;
            warped = true;
            turned = false;
            model.resume();
            return;
        }

        Rectangle range = new Rectangle(
                (int) rocket.getX() - mv.getWidth(),
                (int) rocket.getY() - mv.getHeight(),
                mv.getWidth() * 3,
                mv.getHeight() * 3
        );

        gStar.setStroke(new BasicStroke(5));
        gStar.setColor(Color.WHITE);
        model.getRocketFloorMap()
                .rangeSymbolStream(range)
                .map(this::createLineStar)
                .forEach(gStar::draw);

        warpStarLength += 5;
    }
}
