package net.trpfrog.medipro_game.mini_game.galaxy_express.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.symbol.MovableSymbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Train extends MovableSymbol implements Drawable, Suspendable, ActionListener {

    private Image image;
    private final Timer trainTimer = new Timer(10,this::actionPerformed);

    public Train(){
        setLocation(100,100);
        Path imagePath = Paths.get(".","resource","mini_game","galaxy_express","train.png");
        image = Toolkit.getDefaultToolkit().getImage(String.valueOf(imagePath));
        setDrawer(this);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image,(int)getX(),(int)getY(),170,140,null);
    }

    @Override
    public void suspend() {
        trainTimer.stop();
    }

    @Override
    public void resume() {
        trainTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // timerの度に呼び出される
    }
}
