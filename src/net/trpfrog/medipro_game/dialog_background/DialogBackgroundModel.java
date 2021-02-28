package net.trpfrog.medipro_game.dialog_background;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.util.ImageFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;

/**
 * ダイアログの背景のModel
 */
public class DialogBackgroundModel extends GameModel {

    private Rectangle dialogArea;
    private Image blurredBackground;

    protected DialogBackgroundModel(int dialogW, int dialogH) {
        int mainW   = MainView.getInstance().getWidth();
        int mainH   = MainView.getInstance().getHeight();
        dialogArea = new Rectangle((mainW - dialogW) / 2, (mainH - dialogH) / 2, dialogW, dialogH);
    }

    protected Rectangle getDialogArea() {
        return dialogArea;
    }

    protected Image shootBlurredBackground() {
        var contentPane = MainView.getInstance().getContentPane();
        BufferedImage image = new BufferedImage(
                contentPane.getWidth(), contentPane.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) image.getGraphics();
        var scenes = SceneManager.getInstance().getCurrentVisibleScenes();
        Collections.reverse(scenes);

        for(var scene : scenes) {
            if(scene instanceof DialogBackgroundScene) continue;
            scene.getView().paint(g);
        }

        g.dispose();

        for(int i = 0; i < 3; i++) {
            image = (BufferedImage) ImageFilter.blur(image);
        }

        return image;
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }
}
