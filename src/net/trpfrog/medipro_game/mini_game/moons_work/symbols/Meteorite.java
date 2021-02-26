package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.util.ResourceLoader;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 隕石のシンボルを管理するクラス
 */
public class Meteorite extends MovableSymbol {

    public static final Image ROCK_IMG = ResourceLoader
            .readImage(".","resource","mini_game","moons_work","firerock.png");

    /**
     * 指定したモデルに image の見た目の隕石を座標(x,y) に生成します。
     * @param x 初期位置のx座標
     * @param y 初期位置のy座標
     */
    public Meteorite(int x, int y) {
        super(x, y);
        setDrawer(g -> {
            Rectangle r = getRelativeHitBox().getBounds();
            r.translate((int) getX(), (int) getY());
            double cx = r.getCenterX();
            double cy = r.getCenterY();
            r.x -= r.width * 1.8;
            r.width *= 2.8;
//            double t = ((System.currentTimeMillis() + hashCode()) / 1000.0) % 360;
            double angle = Math.toRadians(getAngleDegrees());
            g.rotate(angle, cx, cy);
            g.drawImage(ROCK_IMG, r.x, r.y, r.width, r.height, null);
            g.rotate(-angle, cx, cy);
        });
    }

}