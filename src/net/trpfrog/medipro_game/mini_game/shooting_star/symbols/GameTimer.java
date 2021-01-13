package net.trpfrog.medipro_game.mini_game.shooting_star.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.mini_game.shooting_star.ShootingStarModel;

import java.awt.*;

public class GameTimer implements Drawable {
    private long timerEnd = Long.MAX_VALUE;
    private long duringMillis;
    private Font font;
    private ShootingStarModel model;

    public GameTimer(long duringMillis, ShootingStarModel model) {
        this.model = model;
        this.duringMillis = duringMillis;
        font = new Font(Font.MONOSPACED, Font.BOLD, 30);
    }

    public void start() {
        if(timerEnd != Long.MAX_VALUE) return;
        timerEnd = System.currentTimeMillis() + duringMillis;
    }

    public boolean running() {
        return System.currentTimeMillis() < timerEnd;
    }

    public long remainingMillis() {
        if(timerEnd == Long.MAX_VALUE) return duringMillis;
        if(!running()) return 0;
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
}
