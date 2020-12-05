package net.trpfrog.medipro_game.scene;

import net.trpfrog.medipro_game.Suspendable;

/**
 * ゲームシーンのMVCをまとめて、1つのシーンとして扱うためのインタフェース。
 * このインタフェースを実装したクラスは、
 * シーンマネージャにシーンとして登録することができます。
 * @author つまみ
 */
public abstract class GameScene implements Suspendable {

    /**
     * このゲームシーンのModelを返します。
     * @return このゲームシーンのModel
     */
    public abstract GameModel getModel();

    /**
     * このゲームシーンのViewを返します。
     * @return このゲームシーンのView
     */
    public abstract GameView getView();

    /**
     * このゲームシーンのControllerを返します。
     * @return このゲームシーンのController
     */
    public abstract GameController getController();

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
