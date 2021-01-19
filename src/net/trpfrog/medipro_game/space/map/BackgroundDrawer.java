package net.trpfrog.medipro_game.space.map;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.space.SpaceModel;

import java.awt.*;

public class BackgroundDrawer implements Drawable {
    private Image background;
    private SpaceModel model;
    private float alpha = 1;

    public BackgroundDrawer(SpaceModel model, Image background) {
        this.background = background;
        this.model = model;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public void draw(Graphics2D g){
        int imageWidth  = 1024;
        int imageHeight = 1024;
        var mainView    = MainView.getInstance();
        var rocket      = model.getRocket();

        int x0 = -(int)(rocket.getX()/2.0 - mainView.getWidth()/2.0);
        int y0 = -(int)(rocket.getY()/2.0 - mainView.getHeight()/2.0);
        x0 = (x0 % imageWidth + imageWidth) % imageWidth - imageWidth;
        y0 = (y0 % imageHeight + imageHeight) % imageHeight - imageHeight;

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        for(int x = x0; x < mainView.getWidth(); x+= imageWidth){
            for(int y = y0; y < mainView.getHeight(); y+= imageHeight){
                g.drawImage(background, x, y, imageWidth, imageHeight, null);
            }
        }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
    }
}
