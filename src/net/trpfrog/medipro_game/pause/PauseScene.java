package net.trpfrog.medipro_game.pause;

import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;

public class PauseScene extends DialogBackgroundScene {
    public PauseScene(boolean hasBackToSpaceButton) {
        super(new PauseWindow(hasBackToSpaceButton), true);
    }
}
