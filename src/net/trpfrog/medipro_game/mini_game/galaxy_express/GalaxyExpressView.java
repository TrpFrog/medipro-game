package net.trpfrog.medipro_game.mini_game.galaxy_express;

import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.awt.*;

public class GalaxyExpressView extends GameView {

    private GalaxyExpressModel model;
    private Timer paintTimer = new Timer(10, g->repaint());

    public GalaxyExpressView(GalaxyExpressModel model) {
        super(model);
        this.model = model;
        setBackground(Color.pink);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JButton("SOUTH"),BorderLayout.SOUTH);
        panel.add(new JButton("center"),BorderLayout.CENTER);
        panel.add(new JButton("EAST"),BorderLayout.EAST);
        add(panel);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        model.getTrain().getDrawer().draw(g2);
        JButton button = new JButton("UNKO");
        add(button,BorderLayout.SOUTH);
    }

    @Override
    public void suspend() {
        paintTimer.stop();
    }

    @Override
    public void resume() {
        paintTimer.start();
    }
}
