package net.trpfrog.medipro_game.dialog_background;

import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.scene.GameScene;

import javax.swing.*;

/**
 * ダイアログの背景のScene
 */
public class DialogBackgroundScene extends GameScene {

    private DialogBackgroundModel model;
    private DialogBackgroundView  view;

    /**
     * 受け取ったJPanelにダイアログ背景をつけてシーンを生成します。
     * もしJPanelにSuspendableが実装されていた場合、そのsuspend/resumeもシーン切り替え時に呼び出されます。
     * @param dialogPanel 幅w, 高さh の描画パネル
     * @param w 描画パネルの幅
     * @param h 描画パネルの高さ
     * @param clickToClose 背景クリックでシーンを閉じるかどうか
     */
    public DialogBackgroundScene(JPanel dialogPanel, int w, int h, boolean clickToClose) {
        model = new DialogBackgroundModel(w, h);
        setModel(model);
        view  = new DialogBackgroundView(model, dialogPanel);
        setView(view);

        if(clickToClose) {
            setController(new DialogBackgroundController(model, view));
        } else {
            setController(new GameController(model, view) {
                @Override
                public void suspend() {}
                @Override
                public void resume() {}
            });
        }
    }

    public DialogBackgroundScene(JPanel dialogPanel, boolean clickToClose) {
        this(dialogPanel, dialogPanel.getWidth(), dialogPanel.getHeight(), clickToClose);
    }
}
