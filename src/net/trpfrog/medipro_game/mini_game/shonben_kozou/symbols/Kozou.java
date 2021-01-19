package net.trpfrog.medipro_game.mini_game.shonben_kozou.symbols;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.shonben_kozou.ShonbenKozouModel;
import net.trpfrog.medipro_game.symbol.Symbol;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Kozou extends Symbol {

    private final Image image;
    private ShonbenKozouModel model;
    private final int sizeX, sizeY;
    private int direction;
    private double vx;
    private MainView mv;
    private Point p;


    public Kozou(ShonbenKozouModel model){
        this.model = model;
        sizeX = 112;
        sizeY = 267;
        direction = -1; // -1のとき右向き。
        vx = 5;
        setLocation(sizeX, 0);
        mv = MainView.getInstance();
        p = new Point();

        Path imagePath = Paths.get(".", "resource", "mini_game", "shonben_kozou", "kozou.png");
        image = Toolkit.getDefaultToolkit().getImage(String.valueOf(imagePath));

        setDrawer(g -> g.drawImage(image, (int)getX(), 0, direction * sizeX, sizeY, null));
    }

    public void move(){
        setX(getX() + (int)vx);
        repulsion();
    }

    public void repulsion(){
        if(getX() > mv.getWidth() - sizeX){
            direction = 1;
            setX(2 * (double)mv.getWidth() - 3 * (double)sizeX - getX());
            vx = -vx;
        }

        if(getX() < sizeX){
            direction = -1;
            setX(3 * (double)sizeX - getX());
            vx = -vx;
        }
    }

    public Point getP(){
        if(direction == 1) p.x = (int)getX() + 20;
        else p.x = (int)getX() - 35;
        p.y = (int)getY() + 190;

        return p;
    }

    public int getdirection(){
        return direction;
    }

}
