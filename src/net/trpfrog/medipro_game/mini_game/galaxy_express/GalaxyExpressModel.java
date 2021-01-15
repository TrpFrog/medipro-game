package net.trpfrog.medipro_game.mini_game.galaxy_express;

import net.trpfrog.medipro_game.mini_game.galaxy_express.symbols.Background;
import net.trpfrog.medipro_game.mini_game.galaxy_express.symbols.Train;
import net.trpfrog.medipro_game.scene.GameModel;

public class GalaxyExpressModel extends GameModel {

    private Train train;
    private Background background;

    public GalaxyExpressModel(){
        train = new Train();
        background = new Background();
    }

    public Train getTrain(){ return train; }
    public Background getBackground(){ return background; }

    @Override
    public void suspend() {
        train.suspend();
    }

    @Override
    public void resume() {
        train.resume();
    }
}
