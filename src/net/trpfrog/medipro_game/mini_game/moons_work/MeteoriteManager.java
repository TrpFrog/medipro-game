package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.ExplosionAnimation;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.Meteorite;
import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;

import javax.swing.*;
import java.awt.geom.Area;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * 多数の隕石とその爆破アニメーションを管理するクラス
 * @author つまみ
 */
public class MeteoriteManager
        extends MoonsWorkCharactersManager<MovableSymbol> implements Suspendable {

    private final ExplosionManager explosionAnimations = new ExplosionManager();
    private static final double CHANCE_OF_METEORITE = 0.09;
    private static final int METEORITE_SPEED = 120 * MoonsWorkModel.busyLevel;
    private static final int SPAWN_FREQUENCY = 100 / MoonsWorkModel.busyLevel;
    private final Timer spawnTimer;
    private MoonsWorkModel model;


    /**
     * MoonsWorkModelからマネージャを作成します。
     * @param model MoonsWorkModel
     */
    public MeteoriteManager(MoonsWorkModel model) {

        Consumer<MovableSymbol> scoreAdder = obj -> {
            if(model.getMoon().touches(obj)) {
                model.getCounter().increment();
            }
        };

        Consumer<MovableSymbol> explosionRegistration = obj -> {
            if(model.getMoon().touches(obj)) {
                Area hitBox = obj.getAbsoluteHitBox();
                explosionAnimations.add(hitBox.getBounds());
            }
        };

        Consumer<MovableSymbol> gameOverCondition = obj -> {
            if(obj instanceof Meteorite && model.getEarth().touches(obj)) {
                model.getEarth().explode(true);
            }
        };

        Consumer<MovableSymbol> cleaning = obj -> {
            if(explosionAnimations.size() > 10) {
                explosionAnimations.cleanup();
            }
        };

        addRemovingHook(scoreAdder);
        addRemovingHook(explosionRegistration);
        addRemovingHook(gameOverCondition);
        addRemovingHook(cleaning);

        addRemoveCondition(obj -> model.getEarth().touches(obj));
        addRemoveCondition(obj -> model.getMoon().touches(obj));
        addRemoveCondition(super::isTooFarObject);

        spawnTimer = new Timer(SPAWN_FREQUENCY, e -> spawn());

        this.model = model;
    }

    /**
     * 爆発アニメーションのリストを返します。
     * @return 爆発アニメーションのリスト
     */
    public List<ExplosionAnimation> getExplosionAnimations() {
        return explosionAnimations;
    }

    private void spawn() {
        if(ThreadLocalRandom.current().nextDouble() <
                CHANCE_OF_METEORITE * (1 + model.getCounter().getValue() / 50.0)) {
            MovableSymbol obj = new Meteorite(-100, -100);
            obj.setRelativeHitBox(RelativeHitBox.makeCircle(26));
            obj.setSpeedPxPerSecond(METEORITE_SPEED +
                    ThreadLocalRandom.current().nextInt(60) - 30);
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