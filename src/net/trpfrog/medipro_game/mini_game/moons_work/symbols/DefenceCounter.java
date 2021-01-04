package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class DefenceCounter extends Symbol implements Drawable {
    private int count = 0;
    private Rectangle drawRange;
    private Font font;

    public DefenceCounter(Rectangle drawRange) {
        this.drawRange = drawRange;
        this.font = new Font(Font.SANS_SERIF, Font.BOLD, 10);
        setDrawer(this);
    }

    private void setAppreciateFontSize(Graphics g) {
        double h = font.getSize();
        double w = g.getFontMetrics(font).stringWidth(count + "");

        Rectangle2D r = new Rectangle2D.Double(drawRange.x, drawRange.y,
                drawRange.width, h * (drawRange.width / w));

        if(drawRange.contains(r)) {
            this.font = new Font(Font.SANS_SERIF, Font.BOLD, (int)(r.getHeight() * 0.5));
        } else {
            this.font = new Font(Font.SANS_SERIF, Font.BOLD, (int)(drawRange.height * 0.5));
        }
    }

    public void increment() {
        count++;
    }

    public void decrement() { count--; }

    public void setCount(int count) { this.count = count; }

    public int getValue() {
        return count;
    }

    @Override
    public void draw(Graphics2D g) {
        setAppreciateFontSize(g);
        double w = g.getFontMetrics(font).stringWidth(count + "");
        double h = font.getSize();
        double x = drawRange.x + (drawRange.getWidth()  - w) / 2;
        double y = drawRange.y + (drawRange.getHeight() + h) / 2;
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(count + "", (int)x, (int)y);
    }
}
