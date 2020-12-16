package net.trpfrog.medipro_game.pause;

import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.scene.GameScene;
import net.trpfrog.medipro_game.scene.GameView;

public class PauseScene extends GameScene {

    private PauseModel model = new PauseModel();
    private PauseView view = new PauseView(model);
    private PauseController controller = new PauseController(model, view);

    public PauseScene() {
        setModel(model);
        setView(view);
        setController(controller);
    }
}
