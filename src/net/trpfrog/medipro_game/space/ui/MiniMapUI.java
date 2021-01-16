package net.trpfrog.medipro_game.space.ui;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.space.SpaceModel;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

public class MiniMapUI implements Drawable {

    /**
     * ミニマップを画面左上に描画するための定数です。
     */
    public static final int UPPER_LEFT  = 0b00;

    /**
     * ミニマップを画面右上に描画するための定数です。
     */
    public static final int UPPER_RIGHT = 0b10;

    /**
     * ミニマップを画面左下に描画するための定数です。
     */
    public static final int LOWER_LEFT  = 0b01;

    /**
     * ミニマップを画面右下に描画するための定数です。
     */
    public static final int LOWER_RIGHT = 0b11;

    private final SpaceModel model;
    private final int chunkSquareLength;
    private final Rectangle drawRange;
    private final Rocket rocket;

    private final float alpha = 0.3f;

    /**
     * ミニマップを初期化します。
     * @param model SpaceModel
     * @param chunkSquareLength 1チャンクを何pxで描画するか
     * @param position ミニマップの位置, このクラスの定数を使用してください。
     */
    public MiniMapUI(SpaceModel model, int chunkSquareLength, int position) {
        this.model = model;
        this.rocket = model.getRocket();
        var map = model.getRocketFloorMap();

        this.chunkSquareLength = chunkSquareLength;
        int width = map.getNumberOfHorizontalChunks() * chunkSquareLength;
        int height = map.getNumberOfVerticalChunks() * chunkSquareLength;

        int margin = 10;
        drawRange = new Rectangle(margin, margin, width, height);

        var contentPane = MainView.getInstance().getContentPane();
        if((position >> 1 & 1) == 1) {
            drawRange.translate(contentPane.getWidth() - margin * 2 - width, 0);
        }
        if((position & 1) == 1){
            drawRange.translate(0, contentPane.getHeight() - margin * 2 - height);
        }
    }

    /**
     * マップ上のx座標をミニマップ上のx座標に変換します。
     * @param originX マップ上のx座標
     * @return ミニマップ上のx座標
     */
    public int toMiniMapX(int originX) {
        var map = model.getRocketFloorMap();
        int x = (int)(originX * drawRange.getWidth() / map.getWidth());
        x = Math.max(0, x);
        x = Math.min(x, drawRange.width);
        return x + drawRange.x;
    }

    /**
     * マップ上のy座標をミニマップ上のy座標に変換します。
     * @param originY マップ上のy座標
     * @return ミニマップ上のy座標
     */
    public int toMiniMapY(int originY) {
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

    private void drawRocketLocation(Graphics2D g) {
        var map = model.getRocketFloorMap();
        boolean outOfMap = !map.isWithin((int)rocket.getX(), (int)rocket.getY());
        int r = 5;

        if(outOfMap) {
            double distanceFromTheVerticalMapBorder = Math.min(
                    Math.abs(rocket.getX()),
                    Math.abs(rocket.getX() - map.getWidth())
            );
            double distanceFromTheHorizontalMapBorder = Math.min(
                    Math.abs(rocket.getY()),
                    Math.abs(rocket.getY() - map.getHeight())
            );
            int distanceFromTheMapBorder = (int)Math.max(
                    distanceFromTheVerticalMapBorder,
                    distanceFromTheHorizontalMapBorder
            );
            r = Math.max(r - distanceFromTheMapBorder / 500, 2);
        }

        drawSymbolPoint(g, rocket, r, Color.RED);
    }

    /**
     * ミニマップを描画します。
     * @param g MainViewのpaintComponentから渡されるGraphics2D
     */
    @Override
    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        drawMapBase(g);
        drawEventStarLocation(g);
        drawRocketLocation(g);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
    }
}
