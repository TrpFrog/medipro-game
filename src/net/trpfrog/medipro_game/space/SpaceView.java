package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.scene.GameView;

import java.awt.*;

public class SpaceView extends GameView {

    private SpaceModel model;

    public SpaceView(SpaceModel model) {
        super(model);
        this.model = model;
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }
}
