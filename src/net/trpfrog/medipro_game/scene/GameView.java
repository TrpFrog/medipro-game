package net.trpfrog.medipro_game.scene;

import net.trpfrog.medipro_game.Drawable;

import javax.swing.*;
import java.awt.*;

/**
 * ゲームシーンのViewを定義する抽象クラス。
 * ゲームシーンのViewは必ずこの抽象クラスを継承してください。
 * @author つまみ
 */
public abstract class GameView extends JPanel implements GameMVC {

    private final GameModel model;

    public GameView(GameModel model) {
        this.model = model;
        setFocusable(true);
    }

    /**
     * Viewが透明性を持つかを返します。
     * デフォルトでは背景が透明であるかどうかで判定します。
     * これがtrueであると、SceneManagerのスタックで下にあるもののViewも一緒に描画されます。
     * @return Viewが透明性を持つか
     */
    public boolean hasTransparency() {
        return !isOpaque();
    }
}
