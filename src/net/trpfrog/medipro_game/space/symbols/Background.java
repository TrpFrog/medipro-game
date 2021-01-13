package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Background extends Symbol implements Drawable {
    private Path path = Paths.get(".","resource","space_game","spaceMap.jpg");
    private Image image = Toolkit.getDefaultToolkit().getImage(path.toString());
    private MainView mv;

    public Background(){
        mv = MainView.getInstance();
        setLocation(mv.getWidth()/2.0, mv.getHeight()/2.0);
        setDrawer(this::draw);
    }


    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image,0,0,mv.getWidth(),mv.getHeight(),null);
    }
}
