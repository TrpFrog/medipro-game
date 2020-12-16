package net.trpfrog.medipro_game.scene;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * ゲームシーンの操作に使うメソッドを定義する抽象クラス。
 * ゲームシーンのControllerは必ずこの抽象クラスを継承してください。
 * @author つまみ
 */
public abstract class GameController implements GameMVC {
    private GameModel model;
    private GameView view;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }
}
