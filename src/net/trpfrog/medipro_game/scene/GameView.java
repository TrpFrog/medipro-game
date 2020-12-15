package net.trpfrog.medipro_game.scene;

import net.trpfrog.medipro_game.Drawable;

import java.awt.*;

/**
 * ゲームシーンのViewを定義するインタフェース。
 * ゲームシーンのViewは必ずこのインタフェースを実装してください。
 * @author つまみ
 */
public interface GameView extends GameMVC, Drawable {
    /**
     * Viewが透明性を持つかを返します
     * @return Viewが透明性を持つか
     */
    default boolean hasTransparency() {
        return false;
    }
}
