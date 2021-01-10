package net.trpfrog.medipro_game.animation;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.scene.GameScene;

import java.awt.*;

public class TransitionModel extends GameModel {

    private Rectangle holeRect;
    private boolean fadeOut;
    private GameScene popScene;
    private GameScene nextScene;

    protected TransitionModel(boolean fadeOut, GameScene popScene, GameScene nextScene) {
        this.fadeOut = fadeOut;
        this.popScene = popScene;
        this.nextScene = nextScene;
        this.holeRect = createHoleRectangle(fadeOut);
    }

    private static Rectangle createHoleRectangle(boolean fadeOut) {
        var mv = MainView.getInstance();
        Rectangle rect;
        if(fadeOut) {
            rect = new Rectangle(0, 0, mv.getWidth(), mv.getHeight());
        } else {
            rect = new Rectangle(mv.getWidth()/2, mv.getHeight()/2, 0, 0);
        }
        return rect;
    }

    /**
     * フェードアウトアニメーションであるかどうかを返します。
     * @return フェードアウトアニメーションであるかどうか
     */
    protected boolean isFadeOut() {
        return fadeOut;
    }

    /**
     * 覗き穴の範囲を表す長方形を返します。
     * @return 覗き穴の範囲を表す長方形
     */
    protected Rectangle getHoleRectangle() {
        return holeRect;
    }

    /**
     * フェードアウトアニメーション終了後にpopするシーンを返します。
     * @return pushするシーン
     */
    protected GameScene getPopScene() {
        return popScene;
    }

    /**
     * フェードアウトアニメーション終了後にシーンをpopするかどうかを返します。
     * @return フェードアウトアニメーション終了後にシーンをpopするかどうか
     */
    protected boolean isPopMode() {
        return popScene != null;
    }

    /**
     * フェードアウトアニメーション終了後にpushするシーンを返します。
     * @return pushするシーン
     */
    protected GameScene getNextScene() {
        return nextScene;
    }

    /**
     * フェードアウトからフェードインにアニメーションを切り替えます。
     */
    protected void transit() {
        var sm = SceneManager.getInstance();
        if(!fadeOut) return;
        assert(sm.top().getModel().equals(this));

        sm.pop();
        if(isPopMode()) {
            while(true) {
                if(sm.pop().equals(getPopScene())) {
                    break;
                }
            }
        } else {
            sm.push(getNextScene());
        }

        sm.push(TransitionScene.createFadeInTransition());
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }
}
