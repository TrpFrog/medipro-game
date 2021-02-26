package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.util.ResourceLoader;

import java.awt.*;

public class BlackHole extends EventStar implements RocketEvent {

    private static Image image = ResourceLoader.readImage(
            "resource", "space_game", "blackhole.png"
    );

    private int radius;

    public BlackHole(int radius) {
        super(image, radius, null);
        this.radius = radius;
        setRelativeHitBox(RelativeHitBox.makeCircle(radius));
        setEvent(this);
    }

    private void gravitate(Rocket rocket) {
        double d = rocket.getPoint2D().distance(getPoint2D());
        double v = rocket.getSpeedPxPerSecond();
        double rx = rocket.getX(), ry = rocket.getY();

        double dx = Math.signum(getX() - rx) * Math.sqrt(Math.abs(getX() - rx)) / d * v;
        double dy = Math.signum(getY() - ry) * Math.sqrt(Math.abs(getY() - ry)) / d * v;

        rocket.translate(dx, dy);
    }

    @Override
    public void run(Rocket rocket, Star star) {
        double distance = rocket.getPoint2D().distance(getPoint2D());
        gravitate(rocket);
        if(distance < radius / 3.0) {
            System.out.println("warp");
            int x = (int) (rocket.getX() + 1000 * Math.random());
            int y = (int) (rocket.getY() + 1000 * Math.random());
            rocket.accessToWarpSystem()
                    .setDestination(x, y)
                    .warp();
        }
    }
}
