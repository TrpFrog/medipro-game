package net.trpfrog.medipro_game;

import java.awt.*;

public interface Drawable {
    /**
     * MainViewに描画するpaintComponentの中身を定義します。
     * @param g MainViewのpaintComponentから渡されるGraphics2D
     */
    void draw(Graphics2D g);
}
