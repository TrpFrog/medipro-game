package net.trpfrog.medipro_game.symbol;

/**
 * 速度を持つSymbolのクラス。
 * @author つまみ
 */
public class MovableSymbol extends Symbol {

    private double speed = 0;

    /**
     * シンボルのx軸方向の速度を取得します。
     * @return シンボルのx軸方向の速度
     */
    public double getSpeedX() {
        return -Math.sin(Math.toRadians(getAngleDegrees())) * speed;
    }

    /**
     * シンボルのy軸方向の速度を取得します。
     * @return シンボルのy軸方向の速度
     */
    public double getSpeedY() {
        return Math.cos(Math.toRadians(getAngleDegrees())) * speed;
    }

    /**
     * シンボルの速度の大きさを取得します。
     * @return シンボルの速度の大きさ
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * シンボルの速度の大きさを設定します。
     * @param speed シンボルの速度の大きさ
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
