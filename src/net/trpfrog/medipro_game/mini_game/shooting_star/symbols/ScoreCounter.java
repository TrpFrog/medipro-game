package net.trpfrog.medipro_game.mini_game.shooting_star.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.shooting_star.ShootingStarModel;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

public class ScoreCounter extends Symbol implements Drawable {

    private Font font;
    private ShootingStarModel model;

    public ScoreCounter(ShootingStarModel model) {
        this.model = model;
        var mv = MainView.getInstance();
        int size = (int)(mv.getHeight() * 0.6);
        font = new Font(Font.SANS_SERIF, Font.BOLD, size);
        setDrawer(this);
    }

    @Override
    public void draw(Graphics2D g) {
        var mv = MainView.getInstance();

        g.setColor(Color.WHITE);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));

        String scoreStr = model.getScore() + "";
        g.setFont(font);
        int x = (mv.getWidth() - g.getFontMetrics().stringWidth(scoreStr)) / 2;
        int y = (mv.getHeight() + font.getSize() / 2) / 2;
        g.drawString(scoreStr, x, y);

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
}
