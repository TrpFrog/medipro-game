package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.scene.GameScene;

import java.util.*;

/**
 * ゲームのシーン管理をするクラス
 * @author つまみ
 */
public class SceneManager {
    private Stack<GameScene> stack = new Stack<>();
    private final MainView mainView = MainView.getInstance();

    private void activateScene(GameScene scene) {
        mainView.addGameController(scene.getController());
        mainView.addGameView(scene.getView());
        scene.resume();
    }

    private void inactivateScene(GameScene scene) {
        scene.suspend();
        mainView.removeGameController(scene.getController());
        mainView.removeGameView(scene.getView());
    }

    /**
     * 新たなゲームシーンをスタックに登録します。
     * また、登録されたゲームシーンにこれに加えて次の3つの処理を行います。<br>
     *  1. Viewにコントローラを登録。<br>
     *  2. Viewに描画処理を登録。<br>
     *  3. resume()を呼び出し、動作を再開させる。<br>
     * また、1つ前に追加されたシーンには上と真逆の処理を行います。
     * @param scene 新しく追加されるシーン
     */
    public void push(GameScene scene) {
        inactivateScene(stack.peek());
        activateScene(scene);
        stack.push(scene);
    }

    /**
     * 最後に追加されたゲームシーンを取り出します。
     * また、これに加えて次の3つの処理を行います。<br>
     *  1. suspend()を呼び出し、動作を停止させる。<br>
     *  2. Viewからコントローラを削除。<br>
     *  3. Viewから描画処理を削除。<br>
     * また、スタックが空になった場合、ソフトウェアを終了します。
     * 空でなかった場合は上の3つと真逆の処理をスタックの先頭に適用します。
     * @return 取り出されたゲームシーン
     */
    public GameScene pop() {
        inactivateScene(stack.peek());
        GameScene ret = stack.pop();
        if(stack.isEmpty()) {
            System.exit(0);
        }
        activateScene(stack.peek());
        return ret;
    }
}
