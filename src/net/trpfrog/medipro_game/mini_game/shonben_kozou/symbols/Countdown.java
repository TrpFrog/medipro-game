package net.trpfrog.medipro_game.mini_game.shonben_kozou.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.shonben_kozou.ShonbenKozouModel;

import java.awt.*;

public class Countdown implements Drawable {
    private ShonbenKozouModel model;
    private MainView mv;

    public Countdown(ShonbenKozouModel model){
        this.model = model;
        mv = MainView.getInstance();
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 60));
        if(model.getGameTimer().getCount() > 1 && model.getGameTimer().getCount() < 300){
            g.drawString(String.format("%d",3 - (model.getGameTimer().getCount() / 100)), mv.getWidth()/2, mv.getHeight()/2);
        }
        else if(model.getGameTimer().getCount() < 400) {
            g.drawString("Start", mv.getWidth() / 2 - 90, mv.getHeight() / 2);
        }
    }
}
