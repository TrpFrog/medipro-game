package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 背景としての星の情報を持つクラス。
 */
public class Star extends Symbol{
    public static Image starImage1 = getImagePath(Paths.get(".","resource","space_game","star1.png"));
    public static Image starImage2 = getImagePath(Paths.get(".","resource","space_game","star2.png"));
    static final int STAR_MAX_NUM = 2;
    private int starIndex;
    public static Image[] starImageArray = {starImage1,starImage2};
    MainView mainView = MainView.getInstance();

    public Star() {
        this.starIndex =(int)(Math.random()*STAR_MAX_NUM);
        setX(Math.random()*mainView.getHeight());
        setY(Math.random()*mainView.getWidth());
        this.setDrawer(new StarVisual());
    }

    public Star(Image starImage, int radiusPx) {
        setDrawer(g -> g.drawImage(starImage,
                (int)getX() - radiusPx, (int)getY() - radiusPx,
                2 * radiusPx, 2 * radiusPx, null));
    }

    public static Star getRandomStar() {
        return new Star();
    }

    public static class StarVisual implements Drawable {

        @Override
        public void draw(Graphics2D g) {
            MainView mainView = MainView.getInstance();
            int n = (int)(Math.random() * Star.STAR_MAX_NUM);
            int starSize = 48;
            int drawX = mainView.getWidth()/2 - starSize/2;
            int drawY = mainView.getHeight()/2 - starSize/2;
            int drawRangeW = drawX + (int)(Math.random()*350 - 175);
            int drawRangeH = drawY + (int)(Math.random()*350 - 175);
            Image drawImageNow = getStarImageArray()[n];
            g.drawImage(drawImageNow, drawRangeW,drawRangeH, starSize,starSize, null);
        }
    }

    public static Image getImagePath(Path path) {
        return Toolkit.getDefaultToolkit().getImage(path.toString());
    }

    public static Image[] getStarImageArray(){
        return starImageArray;
    }

    public int getStarIndex() {
        return starIndex;
    }
}

