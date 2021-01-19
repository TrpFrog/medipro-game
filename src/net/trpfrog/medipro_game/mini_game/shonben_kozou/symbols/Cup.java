package net.trpfrog.medipro_game.mini_game.shonben_kozou.symbols;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.shonben_kozou.ShonbenKozouModel;
import net.trpfrog.medipro_game.symbol.Symbol;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Cup extends Symbol {

    private final Image image;
    private ShonbenKozouModel model;
    private final int sizeX, sizeY;


    public Cup(ShonbenKozouModel model){
        this.model = model;
        sizeX = 100; sizeY = 130;
        MainView mv = MainView.getInstance();
        setX((double)mv.getWidth()/2);
        setY(mv.getHeight()-200);

        Path imagePath = Paths.get(".", "resource", "mini_game", "shonben_kozou", "cup.png");
        image = Toolkit.getDefaultToolkit().getImage(String.valueOf(imagePath));

        setDrawer(g -> g.drawImage(image, (int)getX()-sizeX/2, mv.getHeight() - 200, sizeX, sizeY, null));
    }
}
