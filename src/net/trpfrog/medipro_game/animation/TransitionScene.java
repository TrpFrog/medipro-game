package net.trpfrog.medipro_game.animation;

import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.scene.GameScene;

public class TransitionScene extends GameScene {

    private TransitionModel model;
    private TransitionView view;

    private TransitionScene(GameScene pushScene, GameScene popScene, boolean fadeOut) {

        model = new TransitionModel(fadeOut, popScene, pushScene);
        view = new TransitionView(model);

        setModel(model);
        setView(view);

        setController(new GameController(getModel(), getView()) {
            @Override public void suspend() {}
            @Override public void resume() {}
        });
    }

    /**
     * フェードインアニメーションを返します。
     * @return フェードインアニメーション
     */
    protected static TransitionScene createFadeInTransition() {
        return new TransitionScene(null, null, false);
    }

    /**
     * シーンをpushするときのトランジションシーンを返します。
     * @param nextScene 新たにpushするシーン
     * @return pushすることが予約されたトランジション
     */
    public static TransitionScene createPushTransition(GameScene nextScene) {
        return new TransitionScene(nextScene, null, true);
    }

    /**
     * シーンをpopするときのトランジションシーンを返します。指定したシーン以上が全てpopされます。
     * @param popScene 新たにpopするシーン
     * @return popすることが予約されたトランジション
     */
    public static TransitionScene createPopTransition(GameScene popScene) {
        return new TransitionScene(null, popScene, true);
    }
}
