package net.trpfrog.medipro_game.mini_game.shonben_kozou.symbols;

import com.sun.tools.javac.Main;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.shonben_kozou.ShonbenKozouModel;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;

public class Character extends Symbol {
    private ShonbenKozouModel model;
    private String time, score;
    private MainView mv;

    public Character(ShonbenKozouModel model) {
        this.model = model;
        mv = MainView.getInstance();

        setDrawer(g -> {
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            g.drawString("score  " + model.getCircles().getScore(), mv.getWidth() - 120, 20);
        });
    }
}
