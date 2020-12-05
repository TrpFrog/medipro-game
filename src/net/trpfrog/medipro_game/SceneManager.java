package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.scene.GameScene;

import java.util.*;

/**
 * ゲームのシーン管理をするクラス
 * @author つまみ
 */
public class SceneManager {
    private Stack<GameScene> stack = new Stack<>();

    /**
     * 新たなゲームシーンを登録します。
     * 登録されたゲームシーンはresume()が呼び出され、動作を再開、ウィンドウ最上部に描画されます。
     * また、1つ前に追加されたシーンはsuspend()が呼び出され動作を停止します。
     * @param scene 新しく追加されるシーン
     */
    public void push(GameScene scene) {
        stack.peek().suspend();
        scene.resume();
        MainView.getView().addGameView(scene.getView());
        stack.push(scene);
    }

    /**
     * 最後に追加されたゲームシーンを取り出します。
     * このとき、取り出すシーンはsuspend()が呼び出され動作を停止、
     * 1つ前のシーンはresume()が呼び出され動作を再開します。
     * @return 取り出されたゲームシーン
     */
    public GameScene pop() {
        stack.peek().suspend();
        GameScene ret = stack.pop();
        if(stack.isEmpty()) {
            System.exit(0);
        }
        MainView.getView().popGameView();
        stack.peek().resume();
        return ret;
    }
}
