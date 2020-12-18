package net.trpfrog.medipro_game.symbol;

import javax.swing.*;

/**
 * 速度を持つSymbolのクラス。
 * @author つまみ
 */
public class MovableSymbol extends Symbol {

    private double speedPxPerSecond = 0;
    private Timer timer;

    /**
     * 座標(double)を指定してMovableSymbolを生成します。
     * @param x x座標
     * @param y y座標
     */
    public MovableSymbol(double x, double y) {
        super(x, y);
        timer = new Timer(10, e -> move(timer.getDelay()));
    }

    /**
     * 座標(int)を指定してMovableSymbolを生成します。
     * @param x x座標
     * @param y y座標
     */
    public MovableSymbol(int x, int y) {
        this((double)x, y);
    }

    /**
     * 座標を指定せずMovableSymbolを生成します。
     */
    public MovableSymbol() {
        timer = new Timer(10, e -> move(timer.getDelay()));
    }

    /**
     * シンボルのx軸方向の速度を取得します。
     * @return シンボルのx軸方向の速度
     */
    public double getSpeedX() {
        return calcSightLineX() * speedPxPerSecond;
    }

    /**
     * シンボルのy軸方向の速度を取得します。
     * @return シンボルのy軸方向の速度
     */
    public double getSpeedY() {
        return calcSightLineY() * speedPxPerSecond;
    }

    /**
     * シンボルの速度の大きさを取得します。
     * @return シンボルの速度の大きさ
     */
    public double getSpeedPxPerSecond() {
        return speedPxPerSecond;
    }

    /**
     * シンボルの速度 [px/s] の大きさを設定します。
     * @param speedPxPerSecond シンボルの速度の大きさ
     */
    public void setSpeedPxPerSecond(double speedPxPerSecond) {
        this.speedPxPerSecond = speedPxPerSecond;
    }

    /**
     * 速度 [px/s] に秒数をかけた距離移動します。
     * @param seconds 移動する分の秒数
     */
    public void move(double seconds) {
        this.translate(getSpeedX() * seconds, getSpeedY() * seconds);
    }

    /**
     * 速度 [px/s] にミリ秒数をかけた距離移動します。
     * @param milliseconds 移動する分の秒数
     */
    public void move(int milliseconds) {
        move(milliseconds/1000.0);
    }

    /**
     * 移動に使うタイマーを取得します。
     * @return 移動に使うTimer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * 自動的な移動を開始します。
     */
    public void start() {
        timer.start();
    }

    /**
     * 自動的な移動を停止します。
     */
    public void stop() {
        timer.stop();
    }
}
