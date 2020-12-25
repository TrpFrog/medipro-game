package net.trpfrog.medipro_game.mini_game.race_game.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

public class Background extends Symbol implements Drawable {

    public Background() {
        MainView mv = MainView.getInstance();
        setLocation(mv.getWidth()/2.0, mv.getHeight()/2.0);
        setDrawer(this);
    }

    // トラックを引く
    public void drawTrack(Graphics2D g, int size) {
        var stroke = g.getStroke();
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.WHITE);
        g.drawArc((int)getX() - size, (int)getY() - size/2, size, size, 90, 180);
        g.drawLine((int)getX() - size/2, (int)getY() - size/2, (int)getX() + size/2, (int)getY() - size/2);
        g.drawLine((int)getX() - size/2, (int)getY() + size/2, (int)getX() + size/2, (int)getY() + size/2);
        g.drawArc((int)getX(), (int)getY() - size/2, size, size, 90, -180);
        g.setStroke(stroke);
    }

    @Override
    public void draw(Graphics2D g) {
        drawTrack(g, 350);
        drawTrack(g, 300);
    }
}
