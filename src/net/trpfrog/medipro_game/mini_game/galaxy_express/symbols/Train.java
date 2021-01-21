package net.trpfrog.medipro_game.mini_game.galaxy_express.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.symbol.MovableSymbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Train extends MovableSymbol implements Drawable, Suspendable{

    private final int imageWidth = (int)(90*Math.random()) + 30;
    private final int imageHeight =imageWidth*2/3;
    private MainView mainView;
    private int mvWidth,mvHeight;
    private Path path = Paths.get(".","resource","mini_game","galaxy_express","train.png");
    private Image image = Toolkit.getDefaultToolkit().getImage(String.valueOf(path));

    public Train(){
        mainView = MainView.getInstance();
        mvWidth = mainView.getWidth();
        mvHeight = mainView.getHeight();
        setLocation(Math.random()*(mvWidth/2),Math.random()*(mvHeight/2));
        setDrawer(this);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image,(int)getX(),(int)getY(),imageWidth,imageHeight,null);
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }

}
