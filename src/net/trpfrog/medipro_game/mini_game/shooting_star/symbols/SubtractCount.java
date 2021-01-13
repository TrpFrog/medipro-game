package net.trpfrog.medipro_game.mini_game.shooting_star.symbols;

import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

public class SubtractCount extends Symbol {

    private Font font;
    private float alpha = 0.8f;

    public SubtractCount(double x, double y) {
        super(x, y);
        font = new Font(Font.SANS_SERIF, Font.BOLD, 50);
        setDrawer(g -> {
            translate(0, -1);
            alpha -= 0.01f;
            if(alpha < 0) return;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g.setColor(Color.RED);
            g.setFont(font);
            g.drawString("-10", (int)getX(), (int)getY());
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        });
    }

    public float getAlpha() {
        return alpha;
    }
}
