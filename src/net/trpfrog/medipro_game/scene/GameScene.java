package net.trpfrog.medipro_game.scene;

import net.trpfrog.medipro_game.Suspendable;

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

    public GameScene(GameModel model, GameView view, GameController controller) {
        this.model = model;
        this.view = view;
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
