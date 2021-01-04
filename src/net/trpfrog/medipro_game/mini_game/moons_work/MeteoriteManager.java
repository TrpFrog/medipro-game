package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.ExplosionAnimation;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.Meteorite;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.Rocket;
import net.trpfrog.medipro_game.symbol.ImageAnimationSymbol;
import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.symbol.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 多数の隕石とその爆破アニメーションを管理するクラス
 * @author つまみ
 */
public class MeteoriteManager extends Symbol implements Suspendable {

    private List<MovableSymbol> obstacles = Collections.synchronizedList(new LinkedList<>());
    private List<Rocket> rockets = Collections.synchronizedList(new LinkedList<>());
    private List<ExplosionAnimation> explosionAnimations = Collections.synchronizedList(new LinkedList<>());

    private Rectangle mainViewRect = new Rectangle(0, 0,
            MainView.getInstance().getWidth(), MainView.getInstance().getHeight());

    private final Timer moveObstaclesTimer, slowTimer;
    private final MoonsWorkModel model;

    /**
     * MoonsWorkModelからマネージャを作成します。
     * @param model MoonsWorkModel
     */
    public MeteoriteManager(MoonsWorkModel model) {
        this.model = model;
        setDrawer(g -> {
            obstacles.forEach(e -> e.getDrawer().draw(g));
            rockets.forEach(e -> e.getDrawer().draw(g));
        });

        // 隕石の移動Timer
        moveObstaclesTimer = new Timer(40, e -> {
            moveEachObstacles(40);
            moveEachRockets(40);
        });

        // 隕石の追加・再生済みの爆発アニメーションの回収
        slowTimer = new Timer(100, e -> {
            addRocksRandomly();
            addRocketsRandomly();
            removeObsoleteObjects();
            model.getAlert().setAlertLevel(model.getEarth().dangerousLevel());
        });
    }

    /**
     * 障害物リストを返します。
     * @return 障害物のリスト
     */
    public List<MovableSymbol> getObstacles() {
        return obstacles;
    }

    public List<Rocket> getRockets() {
        return rockets;
    }

    /**
     * 爆発アニメーションのリストを返します。
     * @return 爆発アニメーションのリスト
     */
    public List<ExplosionAnimation> getExplosionAnimations() {
        return explosionAnimations;
    }

    /**
     * 隕石を指定した時間分だけ動かす
     * @param milliseconds ミリ秒
     */
    public void moveEachObstacles(int milliseconds) {
        var it = obstacles.iterator();

        while(it.hasNext()) {
            var obj = it.next();
            Area hitBox = obj.getAbsoluteHitBox();

            // Collision to the moon
            if(model.getMoon().touches(obj)) {

                if(obj instanceof Meteorite) {
                    model.getCounter().increment();
                } else if (obj instanceof Rocket) {
                    model.getRocketLiveCount().decrement();
                }

                registerExplodedObstacle(hitBox.getBounds());
                it.remove();

            }

            // Meteorite collision to the earth
            else if(obj instanceof Meteorite && model.getEarth().touches(obj)) {
                model.getEarth().explode(true);
                it.remove();
            }

            // Landed rocket on the earth
            else if(obj instanceof Rocket &&
                    model.getEarth().getAbsoluteHitBox().contains(hitBox.getBounds())) {

                model.getRocketLiveCount().increment((Rocket) obj);
                it.remove();
            }

            else {
                obj.moveMilliseconds(milliseconds);
            }
        }
    }

    public void moveEachRockets(int milliseconds) {
        var it = rockets.iterator();
        while(it.hasNext()) {
            var obj = it.next();
            if(model.getMoon().touches(obj)) {
                model.getRocketLiveCount().decrement();
                registerExplodedObstacle(obj.getAbsoluteHitBox().getBounds());
                it.remove();
            } else {
                obj.moveMilliseconds(milliseconds);
            }
        }
    }

    // 隕石がスポーンする円
    private final double SPAWN_RADIUS = //500;
            Point.distance(0, 0, mainViewRect.width, mainViewRect.height) / 2 + 50;

    private double cx = mainViewRect.getCenterX();
    private double cy = mainViewRect.getCenterY();


    private void removeObsoleteObjects() {
        // 地球から離れすぎたロケットをリストから削除
        rockets.removeIf(e -> {
            double dist = e.getPoint2D().distance(cx, cy);
            return dist > SPAWN_RADIUS + 10;
        });

        // 爆破が終わったアニメーションをリストから削除
        explosionAnimations.removeIf(ImageAnimationSymbol::hasPlayed);
    }

    // 爆発アニメーションを再生
    private void registerExplodedObstacle(Rectangle r) {
        r.grow(20, 20); // 実際よりアニメーションを大きくする
        var expAnime = new ExplosionAnimation();
        expAnime.setLocation(r.x, r.y);
        expAnime.setFps(40);
        expAnime.setRelativeHitBox(RelativeHitBox.makeRectangle(r.width, r.height));
        expAnime.start();
        explosionAnimations.add(expAnime);
    }

    // 出発地点を決める
    private Point selectOutOfBoundsPoint() {
        Point p = new Point();
        double angle = Math.PI * 2 * (Math.random());
        p.x = (int)(SPAWN_RADIUS * Math.cos(angle) + mainViewRect.getCenterX());
        p.y = (int)(SPAWN_RADIUS * Math.sin(angle) + mainViewRect.getCenterY());
        return p;
    }

    private static final double CHANCE_OF_HEALING_ROCKET = 0.1;
    private static final double CHANCE_OF_ROCKET = 0.1;
    private static final double CHANCE_OF_METEORITE = 0.1;


    // 岩(障害物)をランダムに生成する
    private void addRocksRandomly() {
        if(Math.random() < CHANCE_OF_METEORITE) {
            var p = selectOutOfBoundsPoint();
            MovableSymbol obj;
            if(Math.random() > CHANCE_OF_HEALING_ROCKET) {
                obj = new Meteorite(p.x, p.y);
                obj.setRelativeHitBox(RelativeHitBox.makeCircle(26));
            } else {
                obj = new Rocket(p.x, p.y);
                obj.setRelativeHitBox(RelativeHitBox.makeRectangle(80, 50));
            }

            // 円状にスポーン
            obj.setAngleDegrees(Math.toDegrees(Math.atan2(
                    mainViewRect.getHeight()/2 - p.y,
                    mainViewRect.getWidth()/2 - p.x
            )));

            // 移動速度を設定
            obj.setSpeedPxPerSecond(100);
            obstacles.add(obj);
        }
    }

    // 岩(障害物)をランダムに生成する
    private void addRocketsRandomly() {
        if(Math.random() < CHANCE_OF_ROCKET) {
            var p = new Point((int)mainViewRect.getWidth() /2,
                              (int)mainViewRect.getHeight()/2);
            var obj = new Rocket(p.x, p.y);

            // 円状にスポーン
            obj.setAngleDegrees(Math.random() * 360);

            obj.setRelativeHitBox(RelativeHitBox.makeRectangle(80, 50));

            // 移動速度を設定
            obj.setSpeedPxPerSecond(100);
            rockets.add(obj);
        }
    }

    @Override
    public void suspend() {
        moveObstaclesTimer.stop();
        slowTimer.stop();
    }

    @Override
    public void resume() {
        moveObstaclesTimer.start();
        slowTimer.start();
    }
}