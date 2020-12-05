package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.scene.*;

public class SpaceScene extends GameScene {

    private SpaceModel model;
    private SpaceView view;
    private SpaceController controller;

    public SpaceScene() {
        model = new SpaceModel();
        view = new SpaceView(model);
        controller = new SpaceController(model, view);
    }

    @Override
    public GameModel getModel() {
        return model;
    }

    @Override
    public GameView getView() {
        return view;
    }

    @Override
    public GameController getController() {
        return controller;
    }
}
