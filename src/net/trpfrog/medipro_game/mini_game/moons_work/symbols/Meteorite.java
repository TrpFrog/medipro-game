package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkModel;
import net.trpfrog.medipro_game.symbol.MovableSymbol;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 隕石のシンボルを管理するクラス
 */
public class Meteorite extends MovableSymbol {

    public static final Image ROCK_IMG =
            getImage(Paths.get(".","resource","mini_game","moons_work","rock.png"));

    private static Image getImage(Path path) {
        return Toolkit.getDefaultToolkit().getImage(path.toString());
    }

    /**
     * 指定したモデルに image の見た目の隕石を座標(x,y) に生成します。
     * @param model 載せるModel
     * @param image 見た目
     * @param x 初期位置のx座標
     * @param y 初期位置のy座標
     */
    public Meteorite(MoonsWorkModel model, Image image, int x, int y) {
        super(x, y);
        setDrawer(g -> {
            Rectangle r = getHitJudgeRectangle();
            double t = ((System.currentTimeMillis() + hashCode()) / 1000.0) % 360;
            double angle = Math.toRadians(2 * 3.14 * t * 100);
            g.rotate(angle, r.getCenterX(), r.getCenterY());
            g.drawImage(image, r.x, r.y, r.width, r.height, null);
            g.rotate(-angle, r.getCenterX(), r.getCenterY());
        });
    }

}