package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkModel;
import net.trpfrog.medipro_game.symbol.ImageAnimationSymbol;
import net.trpfrog.medipro_game.symbol.Symbol;
import net.trpfrog.medipro_game.util.GifConverter;

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
    private boolean exploded = false;
    private MoonsWorkModel model;
    private Timer stateChecker;

    public Earth(MoonsWorkModel model) {
        this.model = model;

        Path imagePath = Paths.get(".", "resource", "mini_game", "moons_work", "earth.png");
        image = Toolkit.getDefaultToolkit().getImage(String.valueOf(imagePath));

        Point p = model.getCenterPoint();
        setLocation(p.x, p.y);
        createHitJudgementRectangle(100, 100);

        Rectangle rect = getHitJudgeRectangle();
        setDrawer(g -> g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null));

        initExplosionAnimation();
        stateChecker = new Timer(100, e -> checkPeaceOfEarth());
    }

    private void initExplosionAnimation() {
        MainView mv = MainView.getInstance();
        finalEarthExplosion = new ImageAnimationSymbol(earthExplosionFrame);
        finalEarthExplosion.setFps(20);
        finalEarthExplosion.setLocation(mv.getWidth()/2.0, mv.getHeight()/2.0);
        finalEarthExplosion.createHitJudgementRectangle(mv.getWidth(), mv.getHeight());
    }

    // 地球に隕石が当たっていないかを確認し、当たっていたら爆破します
    private void checkPeaceOfEarth() {
        boolean earthCollapsed = model
                .getMeteoriteManager()
                .getObstacles()
                .stream()
                .filter(e -> e instanceof Meteorite)
                .anyMatch(e -> model.getEarth().isTouched(e));

        if(earthCollapsed){
            model.getEarth().explode(true);
        }

        model.getMeteoriteManager()
                .getObstacles()
                .stream()
                .filter(e -> e instanceof Rocket)
                .filter(e -> model.getEarth().isTouched(e))
                .forEach(e -> model.getRocketLiveCount().increment((Rocket) e));
    }

    public void explode(boolean quitGame) {
        finalEarthExplosion.start();

        if(quitGame && !exploded) {
            stateChecker.stop();
            exploded = true;

            // explode in 3 seconds
            Timer t = new Timer(3000, e -> model.endGame());
            t.setRepeats(false);
            t.start();
        }
    }

    public ImageAnimationSymbol getExplosionAnimation() {
        return finalEarthExplosion;
    }

    @Override
    public void suspend() {
        stateChecker.stop();
    }

    @Override
    public void resume() {
        stateChecker.start();
    }
}
