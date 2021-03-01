package net.trpfrog.medipro_game.settings;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SettingScene extends DialogBackgroundScene {
    public SettingScene() {
        super(new SettingWindow(), true, true);
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
