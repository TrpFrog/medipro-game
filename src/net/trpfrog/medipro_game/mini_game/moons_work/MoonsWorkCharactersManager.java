package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.data_structures.MovableSymbolManager;
import net.trpfrog.medipro_game.symbol.MovableSymbol;

import java.awt.*;

/**
 * ゲームに登場するオブジェクトの管理クラス。
 * @param <T> オブジェクトの型
 */
public class MoonsWorkCharactersManager<T extends MovableSymbol> extends MovableSymbolManager<T> {

    private final Rectangle mainViewRect = new Rectangle(0, 0,
            MainView.getInstance().getWidth(), MainView.getInstance().getHeight());

    /**
     * 地球とスポーンエリアの距離
     */
    public final double SPAWN_RADIUS = //500;
            Point.distance(0, 0, mainViewRect.width, mainViewRect.height) / 2 + 50;


    private Point selectOutOfBoundsPoint() {
        Point p = new Point();
        double angle = Math.PI * 2 * (Math.random());
        p.x = (int)(SPAWN_RADIUS * Math.cos(angle) + mainViewRect.getCenterX());
        p.y = (int)(SPAWN_RADIUS * Math.sin(angle) + mainViewRect.getCenterY());
        return p;
    }

    /**
     * オブジェクトが地球から十分に離れているかどうか(画面外に出たかどうか)を返します。
     * @param obj 判定するオブジェクト
     * @return オブジェクトが地球から十分に離れた画面外に出たか
     */
    public boolean isTooFarObject(MovableSymbol obj) {
        Rectangle rect = MainView.getInstance().getContentPane().getBounds();
        double cx = rect.getCenterX();
        double cy = rect.getCenterY();
        return obj.getPoint2D().distance(cx, cy) > SPAWN_RADIUS + 10;
    }

    /**
     * 地球に向かうキャラクターを追加します。
     * @param obj 新たに追加するオブジェクト
     */
    public void sendToEarth(T obj) {
        Point p = selectOutOfBoundsPoint();
        obj.setLocation(p.x, p.y);
        // Spawn on the circumference
        obj.setAngleDegrees(Math.toDegrees(Math.atan2(
                mainViewRect.getHeight()/2 - p.y,
                mainViewRect.getWidth()/2 - p.x
        )));
        add(obj);
    }

    /**
     * 地球から離れるキャラクターを追加します。
     * @param obj 新たに追加するオブジェクト
     */
    public void leaveFromEarth(T obj) {
        obj.setLocation(mainViewRect.getCenterX(),mainViewRect.getCenterY());
        obj.setAngleDegrees(Math.random() * 360);
        add(obj);
    }
}
