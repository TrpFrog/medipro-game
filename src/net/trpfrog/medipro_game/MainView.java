package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.scene.GameScene;
import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.util.Deque;
import java.util.Stack;

/**
 * ゲームのメインのウィンドウ。
 * @author つまみ
 */
public class MainView extends JFrame implements SceneDequeListener {
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * シーンが追加/削除されたとき、PanelをDequeを元に再追加します。
     * @param changedDeque SceneManagerのdeque
     */
    private void reAddPanels(Deque<GameScene> changedDeque) {
        getContentPane().removeAll();

        // a stack for reverse panels
        Stack<JPanel> panels = new Stack<>();

        for(var scene : changedDeque) {
            GameView view = scene.getView();
            panels.push(view);
            if(!view.hasTransparency()) break;
        }

        while(panels.empty()){
            add(panels.pop());
        }
    }

    @Override
    public void sceneDequeChanged(Deque<GameScene> changedDeque) {
        reAddPanels(changedDeque);
    }
}
