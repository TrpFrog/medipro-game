package net.trpfrog.medipro_game.symbol;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * 任意形状の当たり判定のヒットボックスを管理します。
 */
public class RelativeHitBox extends Area {

    public static RelativeHitBox EMPTY = new RelativeHitBox(new Area());

    /**
     * 座標との相対位置で座標が設定されている当たり判定のShapeを登録して初期化します。
     * @param relativeHitBox Symbolの座標との相対位置で座標が設定されている当たり判定のShape
     */
    public RelativeHitBox(Shape relativeHitBox) {
        super(relativeHitBox);
    }

    /**
     * 指定した座標分だけ平行移動させ、(x, y)を中心に angleRadians だけ回転させた
     * HitBoxをAreaとして返します。
     * @param x x座標の平行移動する量
     * @param y y座標の平行移動する量
     * @param angleRadians 回転量
     * @return 平行移動と回転を行ったArea
     */
    public Area createAbsoluteHitBoxArea(double x, double y, double angleRadians) {
        var af = new AffineTransform();
        af.translate((int)x, (int)y);
        af.rotate(angleRadians);
        return this.createTransformedArea(af);
    }

    /**
     * Symbolから座標と角度の情報を読み、変形させた絶対位置のHitBoxをAreaとして返します。
     * @param symbol 座標と角度を持つSymbol
     * @return 絶対的な位置のHitBoxを表すArea
     */
    public Area createAbsoluteHitBoxArea(Symbol symbol) {
        return createAbsoluteHitBoxArea(symbol.getX(), symbol.getY(), symbol.getAngleRadians());
    }

    /**
     * 指定した座標分Symbolの座標からずらして、幅width, 高さheight の長方形の当たり判定を登録します。
     * @param dx x座標のSymbolの座標からずらす分
     * @param dy y座標のSymbolの座標からずらす分
     * @param width  HitBoxの幅
     * @param height HitBoxの高さ
     */
    public static RelativeHitBox makeRectangle(double dx, double dy, double width, double height) {
        return new RelativeHitBox(new Rectangle2D.Double(dx - width / 2, dy - height / 2, width, height));
    }

    /**
     * Symbolの座標を中心に、幅width, 高さheight の長方形の当たり判定を登録します。
     * @param width  HitBoxの幅
     * @param height HitBoxの高さ
     */
    public static RelativeHitBox makeRectangle(double width, double height) {
        return makeRectangle(0, 0, width, height);
    }

    /**
     * 指定した座標分Symbolの座標からずらして、半径radiusの円の当たり判定を登録します。
     * @param dx x座標のSymbolの座標からずらす分
     * @param dy y座標のSymbolの座標からずらす分
     * @param radius HitBoxの半径
     */
    public static RelativeHitBox makeCircle(double dx, double dy, double radius) {
        return new RelativeHitBox(new Ellipse2D.Double(dx - radius, dy - radius, 2 * radius, 2 * radius));
    }

    /**
     * Symbolの座標を中心に、半径radiusの円の当たり判定を登録します。
     * @param radius HitBoxの半径
     */
    public static RelativeHitBox makeCircle(double radius) {
        return makeCircle(0, 0, radius);
    }
}
