package net.trpfrog.medipro_game.symbol;

import net.trpfrog.medipro_game.Drawable;

import java.awt.*;

/**
 * 宇宙空間に描画するモノのクラス。
 * @author つまみ
 */
public class Symbol {

    private Drawable drawer;
    private Point point;
    private Rectangle hitJudgeRectangle;
    private double angleDegrees;

    /**
     * 描画用クラスを取得します。
     * @return Drawableを実装した描画用クラス
     */
    public final Drawable getDrawer() {
        return drawer;
    }

    /**
     * 描画用クラスを設定します。
     * @param drawer Drawableを実装した描画用クラス
     */
    public final void setDrawer(Drawable drawer) {
        this.drawer = drawer;
    }

    /**
     * オブジェクトの座標を取得します。
     * @return オブジェクトの座標
     */
    public Point getPoint() {
        return point;
    }

    /**
     * オブジェクトの座標を設定します。
     * @param point 新しく設定するオブジェクトの座標
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * オブジェクトの角度を度数表記で取得します。
     * @return オブジェクトの角度
     */
    public double getAngleDegrees() {
        return angleDegrees;
    }

    /**
     * オブジェクトの角度を度数表記で変更します。
     * @param angleDegrees オブジェクトの角度
     */
    public void setAngleDegrees(double angleDegrees) {
        this.angleDegrees = (angleDegrees % 360 + 360) % 360;
    }

    /**
     * 指定した角度だけ反時計回りにオブジェクトを回転させます。
     * @param dt 回転角 (度数表記)
     */
    public void turnAnticlockwiseDegrees(double dt) {
        setAngleDegrees(angleDegrees + dt);
    }

    /**
     * 指定した角度だけ時計回りにオブジェクトを回転させます。
     * @param dt 回転角 (度数表記)
     */
    public void turnClockwiseDegrees(double dt) {
        setAngleDegrees(angleDegrees - dt);
    }

    /**
     * 座標を中心に幅w, 高さh の当たり判定用長方形を作成します。
     * @param w 当たり判定用の長方形の幅
     * @param h 当たり判定用の長方形の高さ
     */
    public void createHitJudgementRectangle(int w, int h) {
        hitJudgeRectangle = new Rectangle(
                (int)(-w/2.0),
                (int)(-h/2.0), w, h);
    }

    /**
     * オブジェクトの座標を右上とした相対範囲の当たり判定を設定します。
     * @param hitJudgeRectangle 当たり判定の相対範囲
     */
    public void setRelativeHitJudgeRectangle(Rectangle hitJudgeRectangle) {
        this.hitJudgeRectangle = hitJudgeRectangle;
    }

    /**
     * 当たり判定の範囲を返します。
     * @return 当たり判定の範囲。範囲が設定されていない場合はnull
     */
    public Rectangle getHitJudgeRectangle() {
        if(hitJudgeRectangle == null) return null;
        return new Rectangle(
                (int)(hitJudgeRectangle.getX() + this.getPoint().getX()),
                (int)(hitJudgeRectangle.getY() + this.getPoint().getY()),
                (int) hitJudgeRectangle.getWidth(),
                (int) hitJudgeRectangle.getHeight()
        );
    }

    /**
     * 指定したSymbolが自身と接触しているかどうかを返します。
     * 当たり判定が設定されていない場合は座標が重なっているかで判断します。
     * @param other 接触しているかの判定対象のオブジェクト
     * @return otherと接触しているかどうか
     */
    public boolean isTouched(Symbol other) {
        Rectangle otherRect = other.getHitJudgeRectangle();
        if(otherRect == null) {
            return isTouched(other.getPoint());
        } else {
            return isTouched(otherRect);
        }
    }

    /**
     * 指定した点が自身と接触しているかどうかを返します。
     * 当たり判定が設定されていない場合は座標が重なっているかで判断します。
     * @param p 接触しているかの判定対象の座標
     * @return pと接触しているかどうか
     */
    public boolean isTouched(Point p) {
        Rectangle myRect = this.getHitJudgeRectangle();
        if(myRect == null) {
            return getPoint().equals(p);
        } else {
            return myRect.contains(p);
        }
    }

    /**
     * 指定した長方形範囲が自身と接触しているかどうかを返します。
     * 自身に当たり判定が設定されていない場合は座標が重なっているかで判断します。
     * @param r 接触しているかの判定対象の長方形
     * @return rと接触しているかどうか
     */
    public boolean isTouched(Rectangle r) {
        Rectangle myRect = this.getHitJudgeRectangle();
        if(myRect == null) {
            return r.contains(this.getPoint());
        } else {
            return r.intersects(myRect);
        }
    }
}
