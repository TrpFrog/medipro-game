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
     * 指定した {@code Symbol} を中心に {@code FieldMap} のオブジェクトを描画します。
     * @param g 描画に使用する {@code Graphics2D}
     */
    @Override
    public void draw(Graphics2D g) {
        MainView mv = MainView.getInstance();

        Rectangle drawRange = new Rectangle(0, 0, mv.getWidth(), mv.getHeight());
        drawRange.translate(mv.getWidth()/2, mv.getHeight()/2);
        drawRange.translate((int) player.getX(), (int) player.getY());
        drawRange.grow(mv.getWidth()/2, mv.getHeight()/2);

        drawnMap.rangeSymbolStream(drawRange)
                .forEach(e -> e.getDrawer().draw(g));
    }
}
