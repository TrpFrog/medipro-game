package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Background extends Symbol implements Drawable {

    private Image image;
    private Rectangle drawRange;

    public Background() {
        MainView mv = MainView.getInstance();
        setLocation(mv.getWidth()/2.0, mv.getHeight()/2.0);
        setRelativeHitBox(RelativeHitBox.makeRectangle(mv.getWidth(), mv.getHeight()));

        Path imagePath = Paths.get(".", "resource", "mini_game", "moons_work", "background.jpg");
        image = Toolkit.getDefaultToolkit().getImage(String.valueOf(imagePath));

        drawRange = getAbsoluteHitBox().getBounds();
        setDrawer(this);
    }


    @Override
    public void draw(Graphics2D g) {
        final int W = 500; //image.getWidth(null);
        final int H = 500; //image.getHeight(null);
        for(int x = 0; x <= drawRange.width; x += W) {
            for(int y = 0; y <= drawRange.height; y += H) {
                g.drawImage(image, x, y, W, H, null);
            }
        }
    }
}
