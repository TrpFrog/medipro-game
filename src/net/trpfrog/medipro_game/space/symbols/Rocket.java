package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.Drawable;

import java.awt.*;

/**
 * 操作キャラクターであるロケットの情報を保持するクラス。
 * @author つまみ
 */
public class Rocket extends MovableSymbol {

    private int depth;
    private RocketAnimation animation = new RocketAnimation(this);

    public Rocket() {
        this.setDrawer(animation);
    }

    /**
     * ロケットが存在するマップ上の深さを返します。
     * @return ロケットのマップ上の深さ
     */
    public int getDepth() {
        return depth;
    }

    /**
     * ロケットが存在するマップ上の深さを変更します。
     * @param depth ロケットのマップ上の深さ
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * ロケットのアニメーションに関するメソッドを定義します。
     */
    public static class RocketAnimation implements Drawable {
        private final Rocket rocket;

        public RocketAnimation(Rocket rocket) {
            this.rocket = rocket;
        }

        public void damaged() {
            // TODO: 10秒間赤く点滅させるとか、スレッド走らせればできる？
            // わからんので調べてください、つまみより
        }


        @Override
        public void draw(Graphics2D g) {

        }
    }
}
