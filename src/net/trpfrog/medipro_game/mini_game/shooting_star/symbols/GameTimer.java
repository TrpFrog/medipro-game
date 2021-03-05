package net.trpfrog.medipro_game.mini_game.shooting_star.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.mini_game.shooting_star.ShootingStarModel;

import java.awt.*;

/**
 * 残り時間の管理と描画を行うクラス
 * @author つまみ
 */
public class GameTimer implements Drawable, Suspendable {

    private static final long INFINITE_TIME = Long.MAX_VALUE;
    private long timerEnd = INFINITE_TIME;
    private long duringMillis;
    private boolean started = false;
    private Font font;
    private ShootingStarModel model;

    public GameTimer(long duringMillis, ShootingStarModel model) {
        this.model = model;
        this.duringMillis = duringMillis;
        font = new Font(Font.MONOSPACED, Font.BOLD, 30);
    }

    public void start() {
        started = true;
        if(timerEnd != INFINITE_TIME) return;
        timerEnd = System.currentTimeMillis() + duringMillis;
    }

    public void stop() {
        duringMillis = remainingMillis();
        timerEnd = INFINITE_TIME;
    }

    public boolean isRunning() {
        return System.currentTimeMillis() < timerEnd;
    }

    public long remainingMillis() {
        if(timerEnd == INFINITE_TIME) return duringMillis;
        if(!isRunning()) return 0;
        return timerEnd - System.currentTimeMillis();
    }

    @Override
    public void draw(Graphics2D g) {
        long remain = remainingMillis();
        String time = String.format("%2d.%03d seconds left", (remain / 1000), (remain % 1000));

        if(remain == duringMillis) {
            time = "PRESS SPACE KEY TO START";
        }

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(time, 10, 10 + font.getSize());
        if(remain == 0) {
            model.endGame();
        }
    }

    @Override
    public void suspend() {
        stop();
    }

    @Override
    public void resume() {
        if(started) start();
    }
}
