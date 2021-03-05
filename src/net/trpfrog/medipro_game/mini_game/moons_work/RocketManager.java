package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.ExplosionAnimation;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.Rocket;
import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import javax.swing.*;


/**
 * Moon's Workにおけるロケットの管理クラス。
 */
public class RocketManager extends MoonsWorkCharactersManager<Rocket> implements Suspendable {

    private final ExplosionManager explosionAnimations = new ExplosionManager();

    private static final double CHANCE_OF_HEALING_ROCKET = 0.005;
    private static final double CHANCE_OF_ROCKET = 0.04;
    private static final int ROCKET_SPEED = 120 * MoonsWorkModel.busyLevel;
    private static final int SPAWN_FREQUENCY = 100 / MoonsWorkModel.busyLevel;

    private final Timer spawnTimer;

    /**
     * MoonsWorkModelからマネージャを作成します。
     * @param model MoonsWorkModel
     */
    public RocketManager(MoonsWorkModel model) {

        Consumer<MovableSymbol> healing = obj -> {
            if(model.getEarth().touches(obj)) {
                model.getRocketLiveCount().increment((Rocket) obj);
            }
        };

        Consumer<MovableSymbol> breakingRocket = obj -> {
            if(model.getMoon().touches(obj)) {
                model.getRocketLiveCount().decrement();
                explosionAnimations.add(obj.getAbsoluteHitBox().getBounds());
            }
        };

        addRemovingHook(healing);
        addRemovingHook(breakingRocket);

        addRemoveCondition(obj -> !((Rocket)obj).isLeavingEarth() &&
                model.getEarth().getAbsoluteHitBox()
                        .contains(obj.getAbsoluteHitBox().getBounds()));
        addRemoveCondition(obj -> model.getMoon().touches(obj));
        addRemoveCondition(super::isTooFarObject);

        spawnTimer = new Timer(SPAWN_FREQUENCY, e -> spawn());
    }

    /**
     * 爆発アニメーションのリストを返します。
     * @return 爆発アニメーションのリスト
     */
    public java.util.List<ExplosionAnimation> getExplosionAnimations() {
        return explosionAnimations;
    }

    private void spawn() {
        var rand = ThreadLocalRandom.current();
        if(rand.nextDouble() < CHANCE_OF_HEALING_ROCKET) {
            Rocket obj = new Rocket(-100, -100);
            obj.setRelativeHitBox(RelativeHitBox.makeRectangle(80, 50));
            obj.setSpeedPxPerSecond(ROCKET_SPEED + rand.nextInt(60) - 90);
            sendToEarth(obj);
        }
        if(rand.nextDouble() < CHANCE_OF_ROCKET) {
            Rocket obj = new Rocket(-100, -100);
            obj.setRelativeHitBox(RelativeHitBox.makeRectangle(80, 50));
            obj.setSpeedPxPerSecond(ROCKET_SPEED + rand.nextInt(60) - 30);
            obj.setReturnedToEarth(true);
            obj.setLeavingEarth(true);
            leaveFromEarth(obj);
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
