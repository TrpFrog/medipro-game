package net.trpfrog.medipro_game.fieldmap;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

/**
 * 指定した {@code Symbol} を中心として {@code FieldMap} のオブジェクトを描画するクラスです。
 * @author つまみ
 */
public class MapDrawer implements Drawable {

    private Symbol player;
    private FieldMap drawnMap;

    /**
     * マップ描画クラスを初期化します。
     * @param drawnMap 描画するマップ
     * @param player 描画の中心となる {@code Symbol}
     */
    public MapDrawer(FieldMap drawnMap, Symbol player) {
        this.drawnMap = drawnMap;
        this.player = player;
    }

    /**
     * 描画の中心となる {@code Symbol} を返します。
     * @return 描画の中心となる {@code Symbol}
     */
    public Symbol getPlayer() {
        return player;
    }

    /**
     * 描画に使用する {@code FieldMap} を返します。
     * @return 描画に使用する {@code FieldMap}
     */
    public FieldMap getDrawnMap() {
        return drawnMap;
    }

    /**
     * 描画に使用する {@code FieldMap} を差し替えます。
     * @param drawnMap 描画に使用する {@code FieldMap}
     */
    public void setDrawnMap(FieldMap drawnMap) {
        this.drawnMap = drawnMap;
    }

    /**
     * 描画範囲を示す {@code Rectangle} を生成します。
     * @return 描画範囲を示す {@code Rectangle}
     */
    public Rectangle createDrawRangeRectangle() {
        MainView mv = MainView.getInstance();

        int x = (int) player.getX() - mv.getWidth()/2;
        int y = (int) player.getY() - mv.getHeight()/2;

        return new Rectangle(x, y, mv.getWidth(), mv.getHeight());
    }

    /**
     * 指定した {@code Symbol} を中心に {@code FieldMap} のオブジェクトを描画します。
     * @param g 描画に使用する {@code Graphics2D}
     */
    @Override
    public void draw(Graphics2D g) {
        var rect = createDrawRangeRectangle();
        int x = rect.x;
        int y = rect.y;
        rect.grow(5000, 5000);

        player.createTranslatedDrawer((int)player.getX(), (int)player.getY()).draw(g);

        drawnMap.rangeSymbolStream(rect)
                .filter(e -> !player.equals(e))
                .forEach(e -> e.createTranslatedDrawer((int)player.getX(), (int)player.getY()).draw(g));
    }
}
