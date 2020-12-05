package net.trpfrog.medipro_game.scene;

import net.trpfrog.medipro_game.Suspendable;

public interface GameScene extends Suspendable {
    GameModel getModel();
    GameView getView();
    GameController getController();

    @Override
    default void suspend() {
        getModel().suspend();
        getView().suspend();
        getController().suspend();
    }

    @Override
    default void resume() {
        getModel().resume();
        getView().resume();
        getController().resume();
    }
}
