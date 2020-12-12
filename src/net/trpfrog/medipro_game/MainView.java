package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.awt.*;

/**
 * ゲームのメインのウィンドウ。
 * @author つまみ
 */
public class MainView extends JFrame {
    private SceneManager sceneManager;

    public MainView(SceneManager manager) {
        sceneManager = manager;
        setSize(800, 600);
        add(new MainPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 指定したGameControllerをMainViewに登録します。
     * @param controller 登録するコントローラ
     */
    public void addGameController(GameController controller) {
        addMouseListener(controller);
        addKeyListener(controller);
    }

    /**
     * 指定したGameControllerをMainViewから削除します
     * @param controller 削除するコントローラ
     */
    public void removeGameController(GameController controller) {
        removeMouseListener(controller);
        removeKeyListener(controller);
    }

    class MainPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for(var scene : sceneManager.getDeque()) {
                GameView view = scene.getView();
                view.draw((Graphics2D) g);
                if(!view.hasTransparency()) break;
            }
        }
    }
}
