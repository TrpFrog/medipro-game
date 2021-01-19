package net.trpfrog.medipro_game.mini_game.shonben_kozou.symbols;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.shonben_kozou.ShonbenKozouModel;
import net.trpfrog.medipro_game.symbol.Symbol;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Circle extends Symbol {

    private ShonbenKozouModel model;
    private Random r = new Random();
    private MainView mv;
    private double vx, vy;
    private final int size;

    public Circle(ShonbenKozouModel model){
        this.model = model;
        mv = MainView.getInstance();
        size = 20;
        vy = r.nextDouble()*36 - 17;
        vx = -Math.sqrt(400 - vy * vy) * model.getKozou().getdirection();

        setDrawer(g -> {
            g.setColor(Color.YELLOW);
            g.fillOval((int)getX(), (int)getY(), size, size);
        });

        setLocation(model.getKozou().getP().x, model.getKozou().getP().y);
    }

    public void move(){
        setX(getX() + vx);
        setY(getY() + vy);
        vy += 1;

        repulsion();
    }

    public void repulsion(){
        if(getX() > mv.getWidth() - size){
            setX(2 * (double)mv.getWidth() - 2 * (double)size - getX());
            vx = -vx;
        }

        if(getX() < 0){
            setX((int)-getX());
            vx = -vx;
        }
    }
}
