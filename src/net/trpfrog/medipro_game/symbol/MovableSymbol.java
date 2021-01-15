package net.trpfrog.medipro_game.symbol;

import javax.swing.*;

/**
 * 速度を持つSymbolのクラス。
 * @author つまみ
 */
public class MovableSymbol extends Symbol {

    private double speedPxPerSecond = 0;
    private Timer timer;

    private double minSpeed = Double.MAX_VALUE, maxSpeed = Double.MAX_VALUE;

    /**
     * 座標(double)を指定してMovableSymbolを生成します。
     * @param x x座標
     * @param y y座標
     */
    public MovableSymbol(double x, double y) {
        super(x, y);
        timer = new Timer(10, e -> moveMilliseconds(timer.getDelay()));
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
        timer = new Timer(10, e -> moveMilliseconds(timer.getDelay()));
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
     * シンボルの速度の最小値を返します。
     * @return シンボルの速度の最小値
     */
    public double getMinSpeed() {
        return minSpeed;
    }

    /**
     * シンボルの速度の最小値を設定します。
     * @param minSpeed シンボルの速度の最小値
     */
    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
        setSpeedPxPerSecond(getSpeedPxPerSecond());
    }

    /**
     * シンボルの速度の最大値を返します。
     * @return シンボルの速度の最大値
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * シンボルの速度の最大値を設定します。
     * @param maxSpeed シンボルの速度の最大値
     */
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
        setSpeedPxPerSecond(getSpeedPxPerSecond());
    }

    /**
     * シンボルの速度 [px/s] の大きさを設定します。
     * @param speedPxPerSecond シンボルの速度の大きさ
     */
    public void setSpeedPxPerSecond(double speedPxPerSecond) {
        speedPxPerSecond = Math.max(getMinSpeed(), speedPxPerSecond);
        speedPxPerSecond = Math.min(getMaxSpeed(), speedPxPerSecond);
        this.speedPxPerSecond = speedPxPerSecond;
    }

    /**
     * 指定した量だけ加速します。
     * @param a 加速度
     */
    public void accelerate(double a) {
        setSpeedPxPerSecond(getSpeedPxPerSecond() + a);
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
    public void moveMilliseconds(int milliseconds) {
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
