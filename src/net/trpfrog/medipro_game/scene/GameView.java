package net.trpfrog.medipro_game.scene;

import java.awt.*;

/**
 * ゲームシーンのViewを定義するインタフェース。
 * ゲームシーンのViewは必ずこのインタフェースを実装してください。
 * @author つまみ
 */
public interface GameView extends GameMVC {
    /**
     * MainViewに描画するpaintComponentの中身を定義します。
     * @param g MainViewのpaintComponentから渡されるGraphics2D
     */
    void draw(Graphics2D g);
}
