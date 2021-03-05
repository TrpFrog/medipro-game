package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkModel;
import net.trpfrog.medipro_game.symbol.ImageAnimationSymbol;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.symbol.Symbol;
import net.trpfrog.medipro_game.util.GifConverter;
import net.trpfrog.medipro_game.util.MusicPlayer;
import net.trpfrog.medipro_game.util.ResourceLoader;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Earth extends Symbol implements Suspendable {

    private static List<Image> earthExplosionFrame;

    static {
        Path gifPath = Paths.get(".","resource","mini_game","moons_work","explosion-2.gif");
        earthExplosionFrame = GifConverter.toImageList(gifPath);
    }

    private final Image image;
    private ImageAnimationSymbol finalEarthExplosion;
    private static boolean exploded = false;
    private MoonsWorkModel model;

    public Earth(MoonsWorkModel model) {
        this.model = model;

        image = ResourceLoader
                .readImage(".", "resource", "mini_game", "moons_work", "earth.png");

        Point p = model.getCenterPoint();
        setLocation(p.x, p.y);

        int radius = 70;
        setRelativeHitBox(RelativeHitBox.makeCircle(radius));

        Rectangle rect = new Rectangle((int)getX(), (int)getY(), 0, 0);
        rect.grow(radius, radius);
        setDrawer(g -> g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null));

        initExplosionAnimation();
    }

    private void initExplosionAnimation() {
        MainView mv = MainView.getInstance();
        finalEarthExplosion = new ImageAnimationSymbol(earthExplosionFrame);
        finalEarthExplosion.setFps(20);
        finalEarthExplosion.setLocation(mv.getWidth()/2.0, mv.getHeight()/2.0);
        finalEarthExplosion.setRelativeHitBox(RelativeHitBox.makeRectangle(mv.getWidth(), mv.getHeight()));
    }

    public void explode(boolean quitGame) {
        if(!exploded) {
            exploded = true;
            finalEarthExplosion.start();
            if(!quitGame) return;
            Timer t = new Timer(3000, e -> model.endGame());
            t.setRepeats(false);
            t.start();
            Clip se = MusicPlayer.EXPLOSION_SE;
            if(se.isRunning()) se.stop();
            se.setFramePosition(0);
            se.loop(10);
        }
    }

    public static boolean isExploded() {
        return exploded;
    }

    public int dangerousLevel() {
        int cx = MainView.getInstance().getWidth() / 2;
        int cy = MainView.getInstance().getHeight() / 2;

        int lessRocketCount = model.getRocketLiveCount().getCount() <= 1 ? 1 : 0;

        int meteoriteApproaching = model.getMeteoriteManager().stream()
                .filter(e -> e.getPoint2D().distance(cx, cy) < model.getCircleDrawArea().width / 2.0 + 100)
                .anyMatch(e -> e.getPoint2D().distance(model.getMoon().getPoint2D()) > model.getCircleDrawArea().width
                        || e.getPoint2D().distance(cx, cy) < model.getCircleDrawArea().width / 2.0) ? 1 : 0;

        int goesToGameOver = model.getMeteoriteManager().stream()
                .anyMatch(e -> e.getPoint2D().distance(cx, cy) < model.getCircleDrawArea().width / 2.0) ? 3 : 0;

        return Math.max(lessRocketCount + meteoriteApproaching, goesToGameOver);
    }

    public ImageAnimationSymbol getExplosionAnimation() {
        return finalEarthExplosion;
    }

    @Override
    public void suspend() {
    }

    @Override
    public void resume() {
    }
}
