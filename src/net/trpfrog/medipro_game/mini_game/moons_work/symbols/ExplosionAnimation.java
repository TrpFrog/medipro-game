package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.symbol.ImageAnimationSymbol;
import net.trpfrog.medipro_game.util.GifConverter;

import java.awt.*;
import java.nio.file.Path;
import java.util.List;
import java.nio.file.Paths;

public class ExplosionAnimation extends ImageAnimationSymbol {

    private static final Path gifPath;
    private static final List<Image> frameList;

    private final float alpha;

    static {
        gifPath = Paths.get(".","resource","mini_game","moons_work","explosion-2.gif");
        frameList = GifConverter.toImageList(gifPath);
    }

    /**
     * 不透明度を設定して爆発アニメーションを初期化します。
     * @param alpha 不透明度
     */
    public ExplosionAnimation(float alpha) {
        super(frameList);
        setDrawer(this);
        this.alpha = alpha;
    }

    /**
     * 不透明度 0.5 で爆発アニメーションを初期化します。
     */
    public ExplosionAnimation() {
        this(0.5f);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.draw(g);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
    }
}
