package net.trpfrog.medipro_game.scene;

import net.trpfrog.medipro_game.Suspendable;

/**
 * ゲームシーンのMVCをまとめて、1つのシーンとして扱うためのインタフェース。
 * このインタフェースを実装したクラスは、
 * シーンマネージャにシーンとして登録することができます。
 * @author つまみ
 */
public interface GameScene extends Suspendable {

    /**
     * このゲームシーンのModelを返します。
     * @return このゲームシーンのModel
     */
    GameModel getModel();

    /**
     * このゲームシーンのViewを返します。
     * @return このゲームシーンのView
     */
    GameView getView();

    /**
     * このゲームシーンのControllerを返します。
     * @return このゲームシーンのController
     */
    GameController getController();

    /**
     * MVCの全ての動作を一時停止します。
     */
    @Override
    default void suspend() {
        getModel().suspend();
        getView().suspend();
        getController().suspend();
    }

    /**
     * MVCの全ての動作を再開します。
     */
    @Override
    default void resume() {
        getModel().resume();
        getView().resume();
        getController().resume();
    }
}
