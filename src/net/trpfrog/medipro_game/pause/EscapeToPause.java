package net.trpfrog.medipro_game.pause;

import net.trpfrog.medipro_game.SceneManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EscapeToPause extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            SceneManager.getInstance().push(new PauseScene());
        }
    }
}
