package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

/**
 * 背景としての星の情報を持つクラス。
 */
public class Star extends Symbol {

    public Star() {
        this.setDrawer(new StarVisual());
    }

    public static Star getRandomStar() {
        return new Star();
    }

    public static class StarVisual implements Drawable {

        @Override
        public void draw(Graphics2D g) {

        }
    }
}

