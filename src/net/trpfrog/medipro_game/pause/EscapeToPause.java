package net.trpfrog.medipro_game.pause;

import net.trpfrog.medipro_game.SceneManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EscapeToPause extends KeyAdapter {
    private int prevKeyCode;
    private long prevKeyPressedUNIXTime;
    private boolean hasBackToSpaceButton;

    public EscapeToPause(boolean hasBackToSpaceButton){
        this.hasBackToSpaceButton = hasBackToSpaceButton;
        prevKeyCode = 0;
        prevKeyPressedUNIXTime = 0;
    }

    public EscapeToPause(){
        this(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        long currentKeyPressedUNIXTime = System.currentTimeMillis();
        if(currentKeyPressedUNIXTime - prevKeyPressedUNIXTime > 1000) prevKeyCode = 0;

        int keyCode = e.getKeyCode();
        if(keyCode == prevKeyCode) return;
        if(keyCode == KeyEvent.VK_ESCAPE) {
            SceneManager.getInstance().push(new PauseScene(hasBackToSpaceButton));
            prevKeyPressedUNIXTime = System.currentTimeMillis();
            prevKeyCode = keyCode;
        }
    }

    public void keyReleased(KeyEvent e) {
        prevKeyCode = 0;
    }
}
