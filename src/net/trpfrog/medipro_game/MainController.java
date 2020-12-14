package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.scene.GameScene;

import java.util.Deque;

/**
 * MainViewに適用するControllerを管理するクラス
 * @author つまみ
 */
public class MainController implements SceneDequeListener {

    private GameController activeGameController;
    private SceneManager model;
    private MainView view;

    public MainController(SceneManager model, MainView view) {
        this.model = model;
        this.view = view;
        model.addSceneDequeListener(this);
    }

    @Override
    public void sceneDequeChanged(Deque<GameScene> changedDeque) {
        if(changedDeque.isEmpty()) return;
        if(activeGameController != null) {
            view.removeGameController(activeGameController);
        }
        GameScene front = changedDeque.peekFirst();
        if(front != null && front.getController() != null) {
            activeGameController = front.getController();
            view.addGameController(activeGameController);
        }
    }
}
