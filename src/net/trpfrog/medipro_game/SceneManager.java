package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.scene.GameScene;

import java.util.*;
import java.util.List;

/**
 * ゲームのシーン管理をするクラス
 * @author つまみ
 */
public class SceneManager {
    private Deque<GameScene> deque = new ArrayDeque<>();
    private static final SceneManager instance = new SceneManager();

    private SceneManager() {}

    /**
     * ゲーム内で唯一のSceneManagerを返します
     * @return ゲーム内で唯一のSceneManager
     */
    public static SceneManager getInstance() {
        return instance;
    }

    // Listener関連
    private List<SceneDequeListener> listeners = new LinkedList<>();

    private void notifyDequeChanged() {
        listeners.forEach(e -> e.sceneDequeChanged(deque));
    }

    /**
     * SceneManagerのDequeが変更されたときに実行するListenerを登録します
     * @param listener SceneManagerのDequeが変更されたときに実行するListener
     */
    public void addSceneDequeListener(SceneDequeListener listener) {
        listeners.add(listener);
    }

    /**
     * GameSceneの乗ったDequeを返します。
     * @return GameSceneの乗ったDeque
     */
    protected Deque<GameScene> getDeque() {
        return deque;
    }

    /**
     * 新たなゲームシーンをスタックに登録します。
     * @param scene 新しく追加されるシーン
     */
    public void push(GameScene scene) {
        if(!deque.isEmpty()) {
            deque.peekFirst().suspend();
        }
        scene.resume();
        deque.addFirst(scene);
        notifyDequeChanged();
    }

    /**
     * 最後に追加されたゲームシーンを取り出します。
     * また、スタックが空になった場合、ソフトウェアを終了します。
     * @return 取り出されたゲームシーン
     */
    public GameScene pop() {
        assert deque.peekFirst() != null;
        deque.peekFirst().suspend();
        GameScene ret = deque.removeFirst();
        if(deque.isEmpty()) {
            System.exit(0);
        }
        deque.peekFirst().resume();
        notifyDequeChanged();
        return ret;
    }

    public int size() {
        return deque.size();
    }
}
