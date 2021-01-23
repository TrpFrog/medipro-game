package net.trpfrog.medipro_game.mini_game.macho_game.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

public class Background extends Symbol implements Drawable {

    public Background() {
        MainView mv = MainView.getInstance();
        setLocation(mv.getWidth()/2.0, mv.getHeight()/2.0);
        setDrawer(this);
    }

    @Override
    public void draw(Graphics2D g) {
    }
}
