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

        Rectangle drawRange = new Rectangle(x, y, mv.getWidth(), mv.getHeight());
        drawRange.grow(mv.getWidth()/2, mv.getHeight()/2);

        return drawRange;
    }

    /**
     * 指定した {@code Symbol} を中心に {@code FieldMap} のオブジェクトを描画します。
     * @param g 描画に使用する {@code Graphics2D}
     */
    @Override
    public void draw(Graphics2D g) {
        var rect = createDrawRangeRectangle();
        drawnMap.rangeSymbolStream(rect)
                .forEach(e -> e.createTranslatedDrawer(rect.x, rect.y).draw(g));
    }
}
