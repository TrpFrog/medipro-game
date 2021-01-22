package net.trpfrog.medipro_game.mini_game.shonben_kozou.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Background implements Drawable {
    private Image background;
    private MainView mv;

    public Background(){
        mv = MainView.getInstance();

        Path imagePath = Paths.get(".", "resource", "mini_game", "shonben_kozou", "background.jpg");
        background = Toolkit.getDefaultToolkit().getImage(String.valueOf(imagePath));
    }

    @Override
    public void draw(Graphics2D g){
        g.drawImage(background, 0, 0, mv.getWidth(), mv.getWidth() * 450 / 800, null);
    }
}
