package net.trpfrog.medipro_game.space.symbols.zodiac;

import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.awt.geom.Line2D;

public class ZodiacLineSymbol extends Symbol {
    private final ZodiacSign zodiacSign;

    public ZodiacLineSymbol(ZodiacSign zodiacSign, int x0, int y0, int x1, int y1) {
        this.zodiacSign = zodiacSign;
        setLocation(x0, y0);
        faceTo(x1, y1);

        int shortenDistance = 50;
        final int lineX0 = (int)(x0 + calcSightLineX() * shortenDistance);
        final int lineY0 = (int)(y0 + calcSightLineY() * shortenDistance);
        final int lineX1 = (int)(x1 - calcSightLineX() * shortenDistance);
        final int lineY1 = (int)(y1 - calcSightLineY() * shortenDistance);

        setAngleDegrees(0);
        setLocation(0, 0);

        setDrawer(g -> {
            var originStroke = g.getStroke();
            g.setStroke(new BasicStroke(3));
            g.setColor(zodiacSign.lineColor);
            Line2D line = new Line2D.Double(lineX0 + (int) getX(), lineY0 + (int) getY(),
                    lineX1 + (int) getX(), lineY1 + (int) getY());
            g.draw(line);
            g.setStroke(originStroke);
        });
    }
}
