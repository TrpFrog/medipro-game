package net.trpfrog.medipro_game.mainmenu;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.pause.PauseScene;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.space.SpaceScene;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends GameView {

    private JButton start, quit;

    public MainMenuView(GameModel model) {
        super(model);

        Rectangle buttonArea = new Rectangle(400, 300);
        int w = MainView.getInstance().getWidth();
        int h = MainView.getInstance().getHeight();

        setBorder(BorderFactory.createEmptyBorder(
                (h - buttonArea.height)/2,
                (w - buttonArea.width)/2,
                (h - buttonArea.height)/2,
                (w - buttonArea.width)/2
        ));

        start = new JButton("START");
        quit  = new JButton("QUIT");

        start.addActionListener(e -> SceneManager.getInstance().push(new SpaceScene()));
        quit .addActionListener(e -> SceneManager.getInstance().pop());

        setLayout(new BorderLayout());
        add(start, BorderLayout.NORTH);
        add(quit, BorderLayout.SOUTH);
    }

    @Override
    public void suspend() {
        start.setEnabled(false);
        quit.setEnabled(false);
    }

    @Override
    public void resume() {
        start.setEnabled(true);
        quit.setEnabled(true);
    }
}
