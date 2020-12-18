package net.trpfrog.medipro_game.dialog_background;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.scene.GameModel;

import java.awt.*;

/**
 * ダイアログの背景のModel
 */
public class DialogBackgroundModel extends GameModel {

    private Rectangle dialogArea;

    protected DialogBackgroundModel(int dialogW, int dialogH) {
        int mainW   = MainView.getInstance().getWidth();
        int mainH   = MainView.getInstance().getHeight();
        dialogArea = new Rectangle((mainW - dialogW) / 2, (mainH - dialogH) / 2, dialogW, dialogH);
    }

    protected Rectangle getDialogArea() {
        return dialogArea;
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }
}
