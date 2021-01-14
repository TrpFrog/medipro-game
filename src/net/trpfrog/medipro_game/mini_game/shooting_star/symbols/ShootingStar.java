package net.trpfrog.medipro_game.mini_game.shooting_star.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.shooting_star.ShootingStarModel;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

public class ShootingStar extends Symbol implements Drawable {

    private static int centerX = MainView.getInstance().getWidth() / 2;
    private static int centerY = MainView.getInstance().getWidth() * 2;

    public static final int START_ANGLE = 180;
    public static final int END_ANGLE   = 270;

    private Rectangle drawRange;
    private boolean lookedByCouple = false;
    private SubtractCount subtractCount;
    private Heart heart;
    private ShootingStarModel model;

    public ShootingStar(ShootingStarModel model) {
        setAngleDegrees(180);
        setDrawer(this);
        this.model = model;

        int radius = (int)(MainView.getInstance().getWidth() * 1.5
                + Math.random() * MainView.getInstance().getWidth() * 0.5);

        MainView mv = MainView.getInstance();
        drawRange = new Rectangle(centerX, centerY, 0, 0);
        drawRange.grow(radius, radius);
        setX(centerX);
        setY(centerY);
    }

    public boolean isOutdated() {
        return START_ANGLE < getAngleDegrees() && getAngleDegrees() <= END_ANGLE;
    }

    private SubtractCount createSubtractCount() {
        int margin = 100;
        return new SubtractCount(
                (MainView.getInstance().getWidth()  - margin * 2) * Math.random() + margin/2.0,
                (MainView.getInstance().getHeight() - margin * 2) * Math.random() + margin/2.0
        );
    }

    private Heart createHeart() {
        return new Heart(
                model.getCouple().getX() + Math.random() * 300,
                model.getCouple().getY() + Math.random() * 250
        );
    }

    public boolean isLooked(Couple couple) {
        if(lookedByCouple) return true;
        lookedByCouple =  couple.looksAtTheSky() &&
                45 <= getAngleDegrees() && getAngleDegrees() <= 180 - 45;

        if(lookedByCouple) {
            subtractCount = createSubtractCount();
            heart = createHeart();
            model.setScore(Math.max(0, model.getScore() - 10));
        }

        return lookedByCouple;
    }

    public void turn() {
        if(isOutdated()) return;
        this.turnClockwiseDegrees(2);
    }

    public SubtractCount getSubtractCount() {
        return subtractCount;
    }

    public Heart getHeart() {
        return heart;
    }

    @Override
    public void draw(Graphics2D g) {
        if(subtractCount != null) subtractCount.getDrawer().draw(g);
        if(heart != null) heart.getDrawer().draw(g);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(5));
        g.drawArc(drawRange.x, drawRange.y, drawRange.width, drawRange.height,
                (int)getAngleDegrees(), 5);
    }
}
