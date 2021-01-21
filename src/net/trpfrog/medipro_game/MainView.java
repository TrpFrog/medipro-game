package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.scene.GameScene;
import net.trpfrog.medipro_game.scene.GameView;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.Deque;

/**
 * ゲームのメインのウィンドウ。
 * @author つまみ
 */
public class MainView extends JFrame implements SceneDequeListener {
    private final SceneManager sceneManager = SceneManager.getInstance();

    private static final MainView view = new MainView();

    private Clip runningBGM;

    /**
     * MainViewの唯一のインスタンスを返します。
     * @return MainViewの唯一のインスタンス
     */
    public static MainView getInstance() {
        return view;
    }

    private JLayeredPane pane = new JLayeredPane();

    private MainView() {
        sceneManager.addSceneDequeListener(this);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(pane);
        setVisible(true);
    }

    private void stopBgmNowRunning() {
        if(runningBGM != null && runningBGM.isRunning()) {
            runningBGM.stop();
        }
        runningBGM = null;
    }

    private void changeBGM(Deque<GameScene> changedDeque) {
        for(var scene : changedDeque) {
            var bgm = scene.getView().getBGM();
            if(bgm != null) {
                if(bgm.isRunning()) return;
                stopBgmNowRunning();
                bgm.setMicrosecondPosition(0);
                bgm.loop(Clip.LOOP_CONTINUOUSLY);
                runningBGM = bgm;
                return;
            }
            if(!scene.getView().hasTransparency()) {
                stopBgmNowRunning();
                break;
            }
        }
        stopBgmNowRunning();
    }

    /**
     * シーンが追加/削除されたとき、PanelをDequeを元に再追加します。
     * @param changedDeque SceneManagerのdeque
     */
    private void reAddPanels(Deque<GameScene> changedDeque) {
        pane.removeAll();
        Rectangle bound = new Rectangle(0, 0, getWidth(), getHeight());

        // continue adding panels until
        //  - an opaque panel appears, or
        //  - be all panels added
        for(var scene : changedDeque) {
            GameView view = scene.getView();
            view.setBounds(bound);
            pane.add(view);
            pane.moveToBack(view);
            if(!view.hasTransparency()) break;
        }

        // Request focus to accept keystrokes
        if(!changedDeque.isEmpty()) {
            changedDeque.peekFirst().getView().requestFocus();
        }
    }

    @Override
    public void sceneDequeChanged(Deque<GameScene> changedDeque) {
        reAddPanels(changedDeque);
        changeBGM(changedDeque);
    }
}
