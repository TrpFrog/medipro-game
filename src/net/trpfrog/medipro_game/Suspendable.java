package net.trpfrog.medipro_game;

/**
 * 動作の停止、再開を定義するインタフェース。
 * Timerの再開、停止などに使うことができます。
 * @author つまみ
 */
public interface Suspendable {
    /**
     * 動作の停止を定義します。
     */
    void suspend();

    /**
     * 動作の再開を定義します。
     */
    void resume();
}
