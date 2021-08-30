package net.trpfrog.medipro_game.symbol;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;

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

    private static final boolean DEBUG = false;

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
     * 描画用クラスを設定します。
     * @param drawer Drawableを実装した描画用クラス
     */
    public final void setDrawer(Drawable drawer) {
        this.drawer = drawer;
    }

    /**
     * 描画用クラスを取得します。
     * @return Drawableを実装した描画用クラス
     */
    public final Drawable getDrawer() {
        if(!DEBUG) {
            return drawer == null ? g -> {} : drawer;
        } else {
            return createDrawerWithCollisionShape();
        }
    }

    /**
     * 描画範囲の座標に合わせて一時的に座標をずらして描画する {@code Drawer} を取得します。
     * @param drawRangeX 中央に表示したいオブジェクトのx座標
     * @param drawRangeY 中央に表示したいオブジェクトの左上のy座標
     * @return 描画範囲の座標に合わせて一時的に座標をずらして描画する {@code Drawer}
     */
    public final Drawable createTranslatedDrawer(int drawRangeX, int drawRangeY) {
        MainView mv = MainView.getInstance();
        int x = drawRangeX - mv.getWidth()/2;
        int y = drawRangeY - mv.getHeight()/2;
        return g -> {
            translate(-x, -y);
            getDrawer().draw(g);
            translate(x, y);
        };
    }

    private Drawable createDrawerWithCollisionShape() {
        return g -> {
            drawer.draw(g);
            if(this.relativeHitBox == null) return;
            Area area = relativeHitBox.createAbsoluteHitBoxArea(this);
            if(!relativeHitBox.equals(RelativeHitBox.EMPTY)) {
                var color = g.getColor();
                g.setColor(Color.GREEN);
                g.draw(area);
                g.setColor(color);
            }
        };
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

    public void faceTo(Symbol other){
        double myX = this.getX();
        double myY = this.getY();
        double otherX = other.getX();
        double otherY = other.getY();
        double dx = otherX - myX;
        double dy = otherY - myY;

        double toOtherAngle = Math.toDegrees(Math.atan2(dy, dx));
        this.setAngleDegrees(toOtherAngle);
    }

    public void faceTo(double x, double y){
        double myX = this.getX();
        double myY = this.getY();
        double dx = x - myX;
        double dy = y - myY;

        double toOtherAngle = Math.toDegrees(Math.atan2(dy, dx));
        this.setAngleDegrees(toOtherAngle);
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
     * 現在の座標と角度を元に生成された当たり判定の領域を返します。
     * @return 現在の座標と角度を元に生成された当たり判定の領域
     */
    public Area getAbsoluteHitBox() {
        return relativeHitBox.createAbsoluteHitBoxArea(this);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Double.compare(symbol.angleDegrees, angleDegrees) == 0 &&
                Objects.equals(drawer, symbol.drawer) &&
                Objects.equals(point, symbol.point) &&
                Objects.equals(hitJudgeRectangle, symbol.hitJudgeRectangle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drawer, hitJudgeRectangle);
    }
}
