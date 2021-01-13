package net.trpfrog.medipro_game.mini_game.shooting_star.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.shooting_star.ShootingStarModel;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.nio.file.Paths;

public class Couple extends Symbol implements Drawable {

    private long lookingAtTheSkyUntil = System.currentTimeMillis();

    public static final Image GAZING_STAR;
    public static final Image HUGGING;

    static {
        GAZING_STAR = Toolkit.getDefaultToolkit().getImage(Paths.get(
                ".", "resource", "mini_game", "shooting_star", "looking.png").toString());
        HUGGING = Toolkit.getDefaultToolkit().getImage(Paths.get(
                ".", "resource", "mini_game", "shooting_star", "hug.png").toString());
    }

    private ShootingStarModel model;

    public Couple(ShootingStarModel model) {
        setDrawer(this);
        this.model = model;
    }

    public void turnHead() {
        model.getStars().forEach(e -> e.isLooked(this));
        if(looksAtTheSky()) return;
        lookingAtTheSkyUntil  = System.currentTimeMillis();
        lookingAtTheSkyUntil += 1000 + 2000 * Math.random();
    }

    private int desireToSeeSky = 0;
    public void turnHeadRandomly() {
        long passedTime = System.currentTimeMillis() - lookingAtTheSkyUntil - 1000;
        double chance = 0.1;
        chance *= passedTime / (double)(1000 * 10);

        if(Math.random() < chance && !looksAtTheSky()) {
            desireToSeeSky++;
            if(Math.random() < 0.1) desireToSeeSky -= 2;
            if(desireToSeeSky >= 10) {
                turnHead();
                desireToSeeSky = 0;
            }
        }
    }

    public boolean looksAtTheSky() {
        return System.currentTimeMillis() < lookingAtTheSkyUntil;
    }

    @Override
    public void draw(Graphics2D g) {
        turnHeadRandomly();

        Image image = looksAtTheSky() ? GAZING_STAR : HUGGING;
        int height = 300;
        int width = height * image.getWidth(null) / image.getHeight(null);

        int vibration = Math.max(0, desireToSeeSky - 3);

        MainView mv = MainView.getInstance();
        g.drawImage(image,
                mv.getWidth() - width - 30 - (int)(vibration * Math.random()),
                mv.getHeight() - height,
                width, height, null);
    }
}
