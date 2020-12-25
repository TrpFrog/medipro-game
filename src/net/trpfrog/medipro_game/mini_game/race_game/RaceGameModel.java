package net.trpfrog.medipro_game.mini_game.race_game;

import net.trpfrog.medipro_game.mini_game.race_game.symbols.Background;
import net.trpfrog.medipro_game.mini_game.race_game.symbols.Car;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.scene.GameView;

public class RaceGameModel extends GameModel {

    private Car car;
    private Background background;

    public RaceGameModel() {
        car = new Car();
        background = new Background();

    }

    public Car getCar() {
        return car;
    }

    public Background getBackground() {
        return background;
    }

    @Override
    public void suspend() {
        car.suspend();
    }

    @Override
    public void resume() {
        car.resume();
    }
}
