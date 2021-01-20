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
    public static final Image STAR_IMAGE_LIGHTED = getImagePath(Paths.get(".","resource","space_game","star1.png"));
    public static final Image STAR_IMAGE_DARKENED = getImagePath(Paths.get(".","resource","space_game","star2.png"));
    private static int starIndex;
    public static Image[] starImageArray = {STAR_IMAGE_LIGHTED, STAR_IMAGE_DARKENED};
    private MainView mainView = MainView.getInstance();
    private Image drawStarImage;
    private int STAR_NUM = 0;

    public Star() {
        drawStarImage = starImageArray[(int)(Math.random()*starImageArray.length)];
        setX(Math.random()*mainView.getHeight());
        setY(Math.random()*mainView.getWidth());
        this.setDrawer(new StarVisual());
    }

    public Star(Image starImage, int radiusPx) {
        setImage(starImage, radiusPx);
    }

    public void setImage(Image starImage, int radiusPx) {
        setDrawer(g -> g.drawImage(starImage,
                (int)getX() - radiusPx, (int)getY() - radiusPx,
                2 * radiusPx, 2 * radiusPx, null));
    }

    public static Star getRandomStar() {
        return new Star();
    }
    private static Image getRandomStarImage(){
        return starImageArray[(int)(Math.random()*starImageArray.length)];
    }

    public class StarVisual implements Drawable {

        @Override
        public void draw(Graphics2D g) {
            int starSize = 35;
            g.drawImage(getRandomStarImage(),(int)getX() ,(int)getY(), starSize,starSize, null);
        }
    }

    public static Image getImagePath(Path path) {
        return Toolkit.getDefaultToolkit().getImage(path.toString());
    }
}

