package net.trpfrog.medipro_game.symbol;

import net.trpfrog.medipro_game.Drawable;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * 宇宙空間に描画するモノのクラス。
 * @author つまみ
 */
public class Symbol {

    private Drawable drawer;
    private Point2D point;
    private Rectangle hitJudgeRectangle;
    private RelativeHitBox relativeHitBox;
    private double angleDegrees;

    public Symbol() {
        point = new Point2D.Double();
        relativeHitBox = RelativeHitBox.EMPTY;
    }

    public Symbol(double x, double y) {
        point = new Point2D.Double(x, y);
    }

    public Symbol(int x, int y) {
        this(x, (double)y);
    }

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
     * @deprecated 座標管理をPoint2Dクラスへ移行するため、このメソッドは意図しない挙動を起こす可能性があります。
     */
    @Deprecated
    public Point getPoint() {
        return new Point((int)point.getX(), (int)point.getY());
    }

    /**
     * オブジェクトの座標を設定します。
     * @param point 新しく設定するオブジェクトの座標
     * @deprecated 座標管理をPoint2Dクラスへ移行するため、このメソッドは意図しない挙動を起こす可能性があります。
     */
    @Deprecated
    public void setPoint(Point point) {
        this.point.setLocation(point.x, point.y);
    }

    /**
     * オブジェクトの座標を表すPointクラスを作成します。
     * @return オブジェクトの座標
     */
    public Point createPoint() {
        return new Point((int)point.getX(), (int)point.getY());
    }

    /**
     * オブジェクトの座標をPoint2Dクラスで取得します。
     * @return オブジェクトの座標
     */
    public Point2D getPoint2D() {
        return point;
    }

    /**
     * オブジェクトの座標を設定します。
     * @param x x座標
     * @param y y座標
     */
    public void setLocation(double x, double y) {
        point.setLocation(x, y);
    }

    /**
     * オブジェクトの座標を設定した分だけ移動させます。
     * @param dx x軸方向への変位
     * @param dy y軸方向への変位
     */
    public void translate(double dx, double dy) {
        this.setLocation(point.getX() + dx, point.getY() + dy);
    }

    /**
     * オブジェクトのx座標を返します。
     * @return x座標
     */
    public double getX() {
        return point.getX();
    }

    /**
     * オブジェクトのy座標を返します。
     * @return y座標
     */
    public double getY() {
        return point.getY();
    }

    /**
     * オブジェクトのx座標を設定します。
     * @param x x座標
     */
    public void setX(double x) {
        this.setLocation(x, getY());
    }

    /**
     * オブジェクトのy座標を設定します。
     * @param y y座標
     */
    public void setY(double y) {
        this.setLocation(getX(), y);
    }

    /**
     * 大きさ1の視線方向のベクトルに対し、そのx軸成分を返します。
     * @return 大きさ1の視線方向ベクトルのx成分
     */
    public double calcSightLineX() {
        return Math.cos(Math.toRadians(getAngleDegrees()));
    }

    /**
     * 大きさ1の視線方向のベクトルに対し、そのy軸成分を返します。
     * @return 大きさ1の視線方向ベクトルのy成分
     */
    public double calcSightLineY() {
        return Math.sin(Math.toRadians(getAngleDegrees()));
    }

    /**
     * オブジェクトの角度を度数表記で取得します。
     * @return オブジェクトの角度
     */
    public double getAngleDegrees() {
        return angleDegrees;
    }

    /**
     * オブジェクトの角度を弧度法の表記で取得します。
     * @return オブジェクトの角度
     */
    public double getAngleRadians() {
        return Math.toRadians(angleDegrees);
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
                (int)(hitJudgeRectangle.getX() + this.getX()),
                (int)(hitJudgeRectangle.getY() + this.getY()),
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
            return isTouched(other.createPoint());
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
            return createPoint().equals(p);
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
            return r.contains(this.createPoint());
        } else {
            return r.intersects(myRect);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Double.compare(symbol.angleDegrees, angleDegrees) == 0 &&
                Objects.equals(drawer, symbol.drawer) &&
                Objects.equals(point, symbol.point) &&
                Objects.equals(hitJudgeRectangle, symbol.hitJudgeRectangle);
    }

    /**
     * 相対座標で管理された当たり判定の範囲を返します。
     * @return 相対座標で管理された当たり判定の範囲
     */
    public RelativeHitBox getRelativeHitBox() {
        return relativeHitBox;
    }

    /**
     * 相対座標で管理された当たり判定の範囲、RelativeHitBoxを登録します。
     * @param relativeHitBox 相対座標で管理された当たり判定の範囲
     */
    public void setRelativeHitBox(RelativeHitBox relativeHitBox) {
        this.relativeHitBox = relativeHitBox;
    }

    /**
     * 他のSymbolと触れているかどうかを返します。
     * @param other 相手のSymbol
     * @return 他のSymbolと触れているかどうか
     */
    public boolean touches(Symbol other) {
        Area myHitBox    = this .getRelativeHitBox().createAbsoluteHitBoxArea(this);
        Area otherHitBox = other.getRelativeHitBox().createAbsoluteHitBoxArea(other);
        myHitBox.intersect(otherHitBox);
        return !myHitBox.isEmpty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(drawer, hitJudgeRectangle);
    }
}
