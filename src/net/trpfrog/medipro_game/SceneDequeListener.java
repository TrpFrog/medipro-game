package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.scene.GameScene;

import java.util.Deque;

/**
 * SceneManagerのDequeが変更されたときに実行することを定義するListener
 * @author つまみ
 */
@FunctionalInterface
public interface SceneDequeListener {
    /**
     * SceneManagerのDequeが変更されたときに実行することを定義します
     * @param changedDeque 変更後のDeque
     */
    void sceneDequeChanged(Deque<GameScene> changedDeque);
}
