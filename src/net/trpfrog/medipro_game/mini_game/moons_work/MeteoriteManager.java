package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.ExplosionAnimation;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.Meteorite;
import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;

import javax.swing.*;
import java.awt.geom.Area;
import java.util.List;
import java.util.function.Consumer;

/**
 * 多数の隕石とその爆破アニメーションを管理するクラス
 * @author つまみ
 */
public class MeteoriteManager
        extends MoonsWorkCharactersManager<MovableSymbol> implements Suspendable {

    private final ExplosionManager explosionAnimations = new ExplosionManager();
    private static final double CHANCE_OF_METEORITE = 0.09;
    private final Timer spawnTimer;

    /**
     * MoonsWorkModelからマネージャを作成します。
     * @param model MoonsWorkModel
     */
    public MeteoriteManager(MoonsWorkModel model) {

        // 月が隕石を破壊したかどうかの判定関数
        Consumer<MovableSymbol> scoreAdder = obj -> {
            if(model.getMoon().touches(obj)) {
                model.getCounter().increment();
                Area hitBox = obj.getAbsoluteHitBox();
                explosionAnimations.add(hitBox.getBounds());
            }
        };

        // ゲームオーバー判定関数
        Consumer<MovableSymbol> gameOverCondition = obj -> {
            if(obj instanceof Meteorite && model.getEarth().touches(obj)) {
                model.getEarth().explode(true);
            }
        };

        addRemovingHook(scoreAdder);
        addRemovingHook(gameOverCondition);

        addRemoveCondition(obj -> model.getEarth().touches(obj));
        addRemoveCondition(obj -> model.getMoon().touches(obj));
        addRemoveCondition(super::isTooFarObject);

        spawnTimer = new Timer(100, e -> spawn());
    }

    /**
     * 爆発アニメーションのリストを返します。
     * @return 爆発アニメーションのリスト
     */
    public List<ExplosionAnimation> getExplosionAnimations() {
        return explosionAnimations;
    }

    private void spawn() {
        if(Math.random() < CHANCE_OF_METEORITE) {
            MovableSymbol obj = new Meteorite(-100, -100);
            obj.setRelativeHitBox(RelativeHitBox.makeCircle(26));
            obj.setSpeedPxPerSecond(100);
            sendToEarth(obj);
        }
    }

    @Override
    public void suspend() {
        spawnTimer.stop();
        stop();
    }

    @Override
    public void resume() {
        spawnTimer.start();
        start();
    }
}