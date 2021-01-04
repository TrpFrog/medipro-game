package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

public class Alert extends Symbol implements Drawable {

    private Rectangle rect;

    private int alertLevel = 0;

    private LinearGradientPaint linearGradientPaint;
    private float[] gradientRadio = {0.0f, 1.0f};
    private Color[] gradientColor = {Color.RED, new Color(255, 0, 0, 0)};

    public Alert() {
        MainView mv = MainView.getInstance();
        int width = mv.getWidth();
        int height = mv.getHeight();
        rect = new Rectangle(0, 0, (int)(width * 0.6), (int)(height * 0.6));
        rect.translate((width - rect.width)/2, (height - rect.height)/2);
        setLocation(width/2.0, height/2.0);
        setRelativeHitBox(RelativeHitBox.makeRectangle(mv.getWidth(), mv.getHeight()));
        setDrawer(this);

        linearGradientPaint = new LinearGradientPaint(
                0, 0, width, 0,
                new float[] {0.0f, 1.0f},
                new Color[] {new Color(255, 0, 0), new Color(255, 0, 0, 0)}
        );
    }

    public void setAlertLevel(int alertLevel) {
        this.alertLevel = alertLevel;
    }

    @Override
    public void draw(Graphics2D g) {
        if(alertLevel == 0) return;

        double t = System.currentTimeMillis() / 1000.0;
        int alpha = (int)((Math.sin(2 * 2 * t * alertLevel) + 1) * 40);
        gradientColor[0] = new Color(255, 0, 0, alpha);

        LinearGradientPaint grd;

        int mvw = MainView.getInstance().getWidth();
        int mvh = MainView.getInstance().getHeight();

        grd = new LinearGradientPaint(0, 0, 0, rect.y, gradientRadio, gradientColor);
        g.setPaint(grd);
        g.fillRect(0, 0, mvw, rect.y);

        grd = new LinearGradientPaint(0, 0, rect.x, 0, gradientRadio, gradientColor);
        g.setPaint(grd);
        g.fillRect(0, 0, rect.x, mvh);

        grd = new LinearGradientPaint(0, mvh,0, rect.y + rect.height,  gradientRadio, gradientColor);
        g.setPaint(grd);
        g.fillRect(0, rect.y + rect.height, mvw, rect.y);

        grd = new LinearGradientPaint(mvw, 0,rect.x + rect.width, 0,  gradientRadio, gradientColor);
        g.setPaint(grd);
        g.fillRect(rect.x + rect.width, 0, rect.x, mvh);
    }
}
