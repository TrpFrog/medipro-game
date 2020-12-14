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
    private final SceneManager sceneManager = SceneManager.getInstance();

    private static final MainView view = new MainView();

    /**
     * MainViewの唯一のインスタンスを返します。
     * @return MainViewの唯一のインスタンス
     */
    public static MainView getInstance() {
        return view;
    }

    private MainView() {
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
        /**
         * SceneManagerのDequeを上から見て、シーンのViewのdraw()を順に呼び出します。
         * hasTransparency()がfalseであれば描画を終了します。
         * @see GameView#hasTransparency
         * @param g 描画に使うGraphics
         */
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
