package net.trpfrog.medipro_game.space.ui;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.space.SpaceModel;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

public class MiniMapUI implements Drawable {

    public static final int UPPER_LEFT  = 0b00;
    public static final int UPPER_RIGHT = 0b10;
    public static final int LOWER_LEFT  = 0b01;
    public static final int LOWER_RIGHT = 0b11;

    private final SpaceModel model;
    private final int chunkSquareLength;
    private final Rectangle drawRange;
    private final Rocket rocket;

    private final float alpha = 0.3f;

    public MiniMapUI(SpaceModel model, int chunkSquareLength, int direction) {
        this.model = model;
        this.rocket = model.getRocket();
        var map = model.getRocketFloorMap();

        this.chunkSquareLength = chunkSquareLength;
        int width = map.getNumberOfHorizontalChunks() * chunkSquareLength;
        int height = map.getNumberOfVerticalChunks() * chunkSquareLength;

        int margin = 10;
        drawRange = new Rectangle(margin, margin, width, height);

        var contentPane = MainView.getInstance().getContentPane();
        if((direction >> 1 & 1) == 1) {
            drawRange.translate(contentPane.getWidth() - margin * 2 - width, 0);
        }
        if((direction & 1) == 1){
            drawRange.translate(0, contentPane.getHeight() - margin * 2 - height);
        }
    }

    private int toMiniMapX(int originX) {
        var map = model.getRocketFloorMap();
        int x = (int)(originX * drawRange.getWidth() / map.getWidth());
        x = Math.max(0, x);
        x = Math.min(x, drawRange.width);
        return x + drawRange.x;
    }

    private int toMiniMapY(int originY) {
        var map = model.getRocketFloorMap();
        int y = (int)(originY * drawRange.getHeight() / map.getHeight());
        y = Math.max(0, y);
        y = Math.min(y, drawRange.height);
        return y + drawRange.y;
    }

    private void drawSymbolPoint(Graphics2D g, Symbol symbol, int r, Color color) {
        int x = toMiniMapX((int)symbol.getX());
        int y = toMiniMapY((int)symbol.getY());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
        g.setColor(color);
        g.fillOval(x - r,  y - r, 2 * r, 2 * r);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
    }

    private void drawEventStarLocation(Graphics2D g) {
        model.getRocketFloorMap()
                .getEventStars()
                .forEach(e -> drawSymbolPoint(g, e, 3, Color.YELLOW));
    }

    private void drawMapBase(Graphics2D g) {
        int border = 1;
        drawRange.grow(border, border);
        g.setColor(Color.WHITE);
        g.fill(drawRange);
        drawRange.grow(-border, -border);
        g.setColor(new Color(0, 5, 28));
        g.fill(drawRange);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        drawMapBase(g);
        drawEventStarLocation(g);
        drawSymbolPoint(g, rocket, 5, Color.RED);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
    }
}
