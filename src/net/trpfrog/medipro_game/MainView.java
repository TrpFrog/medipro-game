package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * ゲームのメインのウィンドウ。
 * @author つまみ
 */
public class MainView extends JFrame {
    private List<GameView> activeViews = new ArrayList<>();

    private MainView() {
        add(new MainPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private static MainView mainView = new MainView();

    /**
     * 唯一のMainViewのインスタンスを返します。
     * @return 唯一のMainViewのインスタンス
     */
    public static MainView getInstance() {
        return mainView;
    }

    /**
     * 描画用のGameViewを追加します。
     * @param view paintComponentから呼び出される描画用のGameView
     */
    public void addGameView(GameView view) {
        activeViews.add(view);
    }

    /**
     * 最後に追加されたGameViewを削除し、返します。
     * @return popされたGameView
     */
    public GameView popGameView() {
        if(activeViews.isEmpty()) return null;
        GameView view = activeViews.get(activeViews.size() - 1);
        activeViews.remove(activeViews.size() - 1);
        return view;
    }

    /**
     * 指定したGameViewを削除します。
     * @param view 削除するGameView
     */
    public void removeGameView(GameView view) {
        // 通常は後ろの要素のみを削除するはずである
        if(activeViews.get(activeViews.size() - 1) == view) {
            popGameView();
        } else {
            activeViews.remove(view);
        }
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
            activeViews.forEach(view -> view.draw((Graphics2D) g));
        }
    }
}
