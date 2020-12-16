package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.scene.GameController;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class SpaceController extends GameController {

    private SpaceModel model;
    private SpaceView view;

    public SpaceController(SpaceModel model, SpaceView view) {
        super(model, view);
        this.model = model;
        this.view = view;
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }
}
