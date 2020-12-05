package net.trpfrog.medipro_game.scene;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * ゲームシーンの操作に使うメソッドを定義するインタフェース。
 * ゲームシーンのControllerは必ずこのインタフェースを実装してください。
 * @author つまみ
 */
public interface GameController extends GameMVC, KeyListener, MouseListener {
}
