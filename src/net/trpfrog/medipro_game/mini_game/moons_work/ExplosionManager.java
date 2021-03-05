package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.data_structures.SymbolManager;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.ExplosionAnimation;
import net.trpfrog.medipro_game.symbol.ImageAnimationSymbol;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;

import java.awt.*;

/**
 * 爆発アニメーションの管理クラス。
 */
public class ExplosionManager extends SymbolManager<ExplosionAnimation> {

    public ExplosionManager() {
        addRemoveCondition(ImageAnimationSymbol::hasPlayed);
    }

    public void add(Rectangle r) {
        r.grow(20, 20); // 実際よりアニメーションを大きくする
        var expAnime = new ExplosionAnimation();
        expAnime.setLocation(r.getCenterX(), r.getCenterY());
        expAnime.setFps(40);
        expAnime.setRelativeHitBox(RelativeHitBox.makeRectangle(r.width, r.height));
        expAnime.start();
        add(expAnime);
    }
}
