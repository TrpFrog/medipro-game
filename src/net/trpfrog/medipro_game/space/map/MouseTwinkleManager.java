package net.trpfrog.medipro_game.space.map;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.data_structures.SymbolManager;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.space.symbols.Twinkle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseTwinkleManager extends SymbolManager<Twinkle>
        implements MouseListener, MouseMotionListener, Drawable {

    private int duringMillis;
    private Rocket rocket;

    public MouseTwinkleManager(Component c,
                               int duringMillis,
                               Rocket rocket) {

        c.addMouseListener(this);
        c.addMouseMotionListener(this);
        addRemoveCondition(Twinkle::isOutdated);
        this.duringMillis = duringMillis;
        this.rocket = rocket;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        var mv = MainView.getInstance();
        double x = e.getX() - mv.getWidth()/2.0 + rocket.getX();
        double y = e.getY() - mv.getHeight()/2.0 + rocket.getY();
        add(new Twinkle(x, y, duringMillis));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        if(Math.random() < 0.3) return;
        mousePressed(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void draw(Graphics2D g) {
        for(var twinkle : this) {
            twinkle.createTranslatedDrawer((int)rocket.getX(), (int)rocket.getY()).draw(g);
        }
    }
}
