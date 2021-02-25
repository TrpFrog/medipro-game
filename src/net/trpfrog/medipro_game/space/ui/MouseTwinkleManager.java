package net.trpfrog.medipro_game.space.ui;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.data_structures.SymbolManager;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.space.symbols.Twinkle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.function.UnaryOperator;

import javax.swing.*;

public class MouseTwinkleManager extends SymbolManager<Twinkle>
        implements MouseListener, Drawable, Suspendable {

    private int duringMillis = 400;
    private Rocket rocket;
    private Timer timer = new Timer(10, e -> showNewTwinkle());
    private final Random random = new Random();

    public MouseTwinkleManager(Component c,
                               Rocket rocket) {

        c.addMouseListener(this);
        addRemoveCondition(Twinkle::isOutdated);
        this.rocket = rocket;
    }

    private void showNewTwinkleFromPoint(Point p, int range, UnaryOperator<Double> animation) {
        int dx = (int)(2 * range * Math.random()) - range;
        int dy = (int)(2 * range * Math.random()) - range;
        int x  = p.x - MainView.getInstance().getWidth() /2 + (int) rocket.getX() + dx;
        int y  = p.y - MainView.getInstance().getHeight()/2 + (int) rocket.getY() + dy;
        add(new Twinkle(x, y, 400, animation));
    }

    private void showNewTwinkle() {

        if(Math.random() - 0.05 > rocket.getSpeedPxPerSecond() / rocket.getMaxSpeed()) return;

        var mv = MainView.getInstance();

        PointerInfo pi = MouseInfo.getPointerInfo();
        Point pointerLocation = pi.getLocation();
        SwingUtilities.convertPointFromScreen(pointerLocation, mv.getContentPane());

        UnaryOperator<Double> shineAnimation = t -> {
            double growTime = 0.3, maxLength = 10;
            if(t < growTime) {
                return maxLength * t / growTime;
            } else {
                return maxLength * Math.cos((t - growTime) / (1 - growTime) * Math.PI / 2);
            }
        };

        int range = 10;
//        for (int i = 0; i < 3; i++) {
            showNewTwinkleFromPoint(pointerLocation, range, shineAnimation);
//        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        timer.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        timer.stop();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void draw(Graphics2D g) {
        for(var twinkle : this) {
            twinkle.createTranslatedDrawer((int)rocket.getX(), (int)rocket.getY()).draw(g);
        }
    }

    @Override
    public void suspend() {
        timer.stop();
    }

    @Override
    public void resume() {

    }
}
