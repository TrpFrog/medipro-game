package net.trpfrog.medipro_game.data_structures;

import net.trpfrog.medipro_game.symbol.MovableSymbol;

import javax.swing.*;

/**
 * {@link MovableSymbol} の集合を管理するクラス
 */
public class MovableSymbolManager extends SymbolManager<MovableSymbol> {

    private Timer timer;
    private boolean autoCleanup = true;

    public MovableSymbolManager() {
        timer = new Timer(10, e -> {
            if(autoCleanup) cleanup();
            moveAll(timer.getDelay());
        });
    }

    /**
     * 指定したミリ秒数分の移動距離だけ登録された {@link MovableSymbol} を動かします。
     * @param milliseconds 移動するミリ秒数
     */
    public void moveAll(int milliseconds) {
        forEach(e -> e.moveMilliseconds(milliseconds));
    }

    /**
     * 登録された {@link MovableSymbol} を全て動かす {@link Timer} を開始します。
     */
    public void start() {
        timer.start();
    }

    /**
     * 登録された {@link MovableSymbol} を全て動かす {@link Timer} を停止します。
     */
    public void stop() {
        timer.stop();
    }

    /**
     * タイマーが作動しているとき自動で {@link MovableSymbolManager#cleanup()} を呼び出すかどうかを返します。
     * デフォルトは {@code true} です。
     * @return タイマーが作動しているとき自動で {@link MovableSymbolManager#cleanup()} を呼び出すか
     */
    public boolean isAutoCleanupMode() {
        return autoCleanup;
    }

    /**
     * タイマーが作動しているとき自動で {@link MovableSymbolManager#cleanup()} を呼び出すかどうかを設定します。
     * デフォルトは {@code true} です。
     * @param autoCleanup タイマーが作動しているとき自動で {@link MovableSymbolManager#cleanup()} を呼び出すか
     */
    public void setAutoCleanup(boolean autoCleanup) {
        this.autoCleanup = autoCleanup;
    }
}
