package net.trpfrog.medipro_game.symbol;

import net.trpfrog.medipro_game.Drawable;

import java.awt.*;
import java.util.List;

public class ImageAnimationSymbol extends Symbol implements Drawable {
    private int fps;
    private long playStart;
    private boolean playing = false;
    private boolean played = false;

    private List<Image> frameList;

    public ImageAnimationSymbol(List<Image> frameList) {
        this.frameList = frameList;
        setDrawer(this);
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public void start(int millisecondsToStart) {
        playStart = System.currentTimeMillis() + millisecondsToStart;
        playing = true;
    }

    public void start() {
        start(0);
    }

    public void stop() {
        playing = false;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean hasPlayed() {
        return played;
    }

    @Override
    public void draw(Graphics2D g) {
        if(!isPlaying()) return;
        long frame = (System.currentTimeMillis() - playStart) * fps / 1000;
        if(frame < 0 || frameList.size() <= frame) {
            if(frameList.size() <= frame) {
                played = true;
            }
            stop();
            return;
        }
        Rectangle bounds = getRelativeHitBox().getBounds();
        bounds.translate((int) getX(), (int) getY());

        g.drawImage(frameList.get((int)frame), bounds.x, bounds.y, bounds.width, bounds.height, null);
    }
}
