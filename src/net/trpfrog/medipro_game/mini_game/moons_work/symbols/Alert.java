package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.util.stream.Stream;

public class Alert extends Symbol implements Drawable {

    private int width, height;
    private Rectangle rect;

    private boolean alertEnabled = false;

    public Alert() {
        MainView mv = MainView.getInstance();
        width = mv.getWidth();
        height = mv.getHeight();
        rect = new Rectangle(0, 0, width, height);
        rect.translate((width - rect.width/2)/2, (height - rect.height/2)/2);
        setLocation(width/2.0, height/2.0);
        setRelativeHitBox(RelativeHitBox.makeRectangle(mv.getWidth(), mv.getHeight()));
        setDrawer(this);
    }

    public void setAlertEnabled(boolean alertEnabled) {
        this.alertEnabled = alertEnabled;
    }

    @Override
    public void draw(Graphics2D g) {
        if(!alertEnabled) return;
//        Rectangle r = new Rectangle(0, 0, width, height);
//        int t = 255;
//        int alpha = (int)((Math.sin(2 * 2 * Math.PI * System.currentTimeMillis() / 1000.0) + 1) * 20);
//        g.setColor(new Color (255, 0, 0, alpha));
//        g.fill(r);
    }
}
