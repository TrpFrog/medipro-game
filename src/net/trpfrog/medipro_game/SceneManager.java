package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.animation.TransitionScene;
import net.trpfrog.medipro_game.scene.GameMVC;
import net.trpfrog.medipro_game.scene.GameScene;
import net.trpfrog.medipro_game.scene.GameView;

import java.util.*;
import java.util.List;

/**
 * ゲームのシーン管理をするクラス
 * @author つまみ
 */
public class SceneManager {
    private Deque<GameScene> deque = new ArrayDeque<>();
    private static final SceneManager instance = new SceneManager();
    private GameScene recentlyPopped = null;

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
     * 新たなゲームシーンをスタックに追加します。
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
     * 新たなゲームシーンをスタックに追加します。
     * 引数でシーン遷移アニメーションの有無を設定できます。
     * @param scene 新しく追加されるシーン
     * @param withTransition シーン遷移アニメーションをつけるか
     */
    public void push(GameScene scene, boolean withTransition) {
        if(!withTransition){
            push(scene);
        } else {
            push(TransitionScene.createPushTransition(scene));
        }
    }

    /**
     * シーンスタックの最前面のシーンを返します。
     * @return シーンスタックの最前面のシーン
     */
    public GameScene top() {
        return deque.peekFirst();
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
        recentlyPopped = ret;
        deque.peekFirst().resume();

        notifyDequeChanged();

        return ret;
    }

    /**
     * 最後に追加されたゲームシーンを取り出します。
     * また、スタックが空になった場合、ソフトウェアを終了します。
     * 引数でシーン遷移アニメーションの有無を設定できます。
     * @param withTransition シーン遷移アニメーションをつけるか
     * @return 取り出されたゲームシーン
     */
    public GameScene pop(boolean withTransition) {
        if(!withTransition){
            return pop();
        } else {
            var ret = top();
            push(TransitionScene.createPopTransition(top()));
            return ret;
        }
    }

    /**
     * 最近popされたGameSceneを返します。
     * @return 最近popされたGameScene
     */
    public GameScene getRecentlyPopped() {
        return recentlyPopped;
    }

    /**
     * 現在見えているシーンのリストを返します。
     * より厳密には {@link GameView#hasTransparency} が {@code false} であるもののうち、
     * スタックの一番上にあるシーンを選び、それより上のシーン全てを返します。
     * @return 現在見えているシーンのリスト
     */
    public List<GameScene> getCurrentVisibleScenes() {
        List<GameScene> list = new ArrayList<>();
        for(var scene : deque) {
            list.add(scene);
            if(!scene.getView().hasTransparency()) break;
        }
        return list;
    }

    /**
     * 指定したMVCのパーツを含むシーンが現れるまでpopを続けます。
     * また、そのようなシーンも一緒にpopし、そのシーンを返します。
     * そのようなシーンが存在しなかった場合、何もせずnullを返します。
     * @param sceneParts MVCのパーツ
     * @return 見つかった場合はpopしたシーン, それ以外はnull
     */
    public GameScene popAndAbove(GameMVC sceneParts) {
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
    public GameScene popAndAbove(GameScene scene) {
        boolean contain = deque.stream().anyMatch(e -> e.equals(scene));
        while(contain && !deque.isEmpty()) {
            GameScene popped = pop();
            if(popped.equals(scene)) return popped;
        }
        return null;
    }

    public int size() {
        return deque.size();
    }
}
