package net.trpfrog.medipro_game.scene;

import net.trpfrog.medipro_game.Suspendable;

import java.util.LinkedList;
import java.util.List;

/**
 * ゲームシーンのMVCをまとめて、1つのシーンとして扱うためのインタフェース。
 * このインタフェースを実装したクラスは、
 * シーンマネージャにシーンとして登録することができます。
 * @author つまみ
 */
public abstract class GameScene implements Suspendable {

    private GameModel model;
    private GameView view;
    private GameController controller;

    private List<GameScene> subScenes = new LinkedList<>();

    public GameScene(GameModel model, GameView view, GameController controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    public GameScene() {}

    protected void addSubScene(GameScene scene) {
        subScenes.add(scene);
    }

    public List<GameScene> getSubScenes() {
        return subScenes;
    }

    /**
     * 指定したMVCのパーツがこのシーンに含まれているかどうかを返します。
     * @param mvc MVCのパーツ
     * @return それがこのシーンに含まれているかどうか
     */
    public boolean contains(GameMVC mvc) {
        return model.equals(mvc) || view.equals(mvc) || controller.equals(mvc);
    }

    /**
     * このゲームシーンのModelを設定します。
     * @param model ゲームシーンのModel
     */
    protected void setModel(GameModel model) {
        this.model = model;
    }

    /**
     * このゲームシーンのViewを設定します。
     * @param view ゲームシーンのView
     */
    protected void setView(GameView view) {
        this.view = view;
    }

    /**
     * このゲームシーンのControllerを設定します。
     * @param controller ゲームシーンのController
     */
    protected void setController(GameController controller) {
        this.controller = controller;
    }

    /**
     * このゲームシーンのModelを返します。
     * @return このゲームシーンのModel
     */
    public GameModel getModel() {
        return model;
    }

    /**
     * このゲームシーンのViewを返します。
     * @return このゲームシーンのView
     */
    public GameView getView() {
        return view;
    }

    /**
     * このゲームシーンのControllerを返します。
     * @return このゲームシーンのController
     */
    public GameController getController() {
        return controller;
    }

    /**
     * MVCの全ての動作を一時停止します。
     */
    @Override
    public void suspend() {
        getModel().suspend();
        getView().suspend();
        getController().suspend();
    }

    /**
     * MVCの全ての動作を再開します。
     */
    @Override
    public void resume() {
        getModel().resume();
        getView().resume();
        getController().resume();
    }
}
