package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.scene.GameMVC;
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
        deque.addFirst(scene);
        for(GameScene sub : scene.getSubScenes()) {
            deque.addFirst(sub);
        }
        if(scene.getSubScenes().isEmpty()) {
            scene.resume();
        } else {
            scene.getSubScenes().get(scene.getSubScenes().size() - 1).resume();
        }
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

    /**
     * 指定したMVCのパーツを含むシーンが現れるまでpopを続けます。
     * また、そのようなシーンも一緒にpopし、そのシーンを返します。
     * そのようなシーンが存在しなかった場合、何もせずnullを返します。
     * @param sceneParts MVCのパーツ
     * @return 見つかった場合はpopしたシーン, それ以外はnull
     */
    public GameScene popUntil(GameMVC sceneParts) {
        boolean contain = deque.stream().anyMatch(e -> e.contains(sceneParts));
        while(contain && !deque.isEmpty()) {
            GameScene popped = pop();
            if(popped.contains(sceneParts)) return popped;
        }
        return null;
    }

    /**
     * 指定したシーンが存在する場合、それが現れるまでpopを続け、最後にそのシーンを返します。
     * そのようなシーンが存在しなかった場合、何もせずnullを返します。
     * @param scene pop対象のシーン
     * @return 見つかった場合はpopしたシーン, それ以外はnull
     */
    public GameScene popUntil(GameScene scene) {
        boolean contain = deque.stream().anyMatch(e -> e == scene);
        while(contain && !deque.isEmpty()) {
            GameScene popped = pop();
            if(popped == scene) return popped;
        }
        return null;
    }

    public int size() {
        return deque.size();
    }
}
