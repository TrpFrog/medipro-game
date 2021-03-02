package net.trpfrog.medipro_game.player;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MedalCollectionScene extends DialogBackgroundScene {
    public MedalCollectionScene() {
        super(new MedalCollectionWindow(), true, true);
        getView().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    SceneManager.getInstance().pop();
                }
            }
        });
    }
}
