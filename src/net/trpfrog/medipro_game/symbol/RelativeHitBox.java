package net.trpfrog.medipro_game.symbol;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * 任意形状の当たり判定のヒットボックスを管理します。
 */
public class RelativeHitBox {

    private Area relativeHitBox;

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
        af.translate(x, y);
        af.rotate(angleRadians, x, y);
        return relativeHitBox.createTransformedArea(af);
    }

    public Area createAbsoluteHitBoxArea(Symbol symbol) {
        return createAbsoluteHitBoxArea(symbol.getX(), symbol.getY(), symbol.getAngleRadians());
    }

    /**
     * 座標との相対位置で座標が設定されている当たり判定のShapeを登録します。
     * @param relativeHitBox Symbolの座標との相対位置で座標が設定されている当たり判定のShape
     */
    public void setRelativeHitBoxShape(Shape relativeHitBox) {
        this.relativeHitBox = new Area(relativeHitBox);
    }

    /**
     * 指定した座標分Symbolの座標からずらして、幅width, 高さheight の長方形の当たり判定を登録します。
     * @param dx x座標のSymbolの座標からずらす分
     * @param dy y座標のSymbolの座標からずらす分
     * @param width  HitBoxの幅
     * @param height HitBoxの高さ
     */
    public void registerRectangle(double dx, double dy, double width, double height) {
        setRelativeHitBoxShape(new Rectangle2D.Double(dx - width / 2, dy - height / 2, width, height));
    }

    /**
     * Symbolの座標を中心に、幅width, 高さheight の長方形の当たり判定を登録します。
     * @param width  HitBoxの幅
     * @param height HitBoxの高さ
     */
    public void registerRectangle(double width, double height) {
        registerRectangle(0, 0, width, height);
    }

    /**
     * 指定した座標分Symbolの座標からずらして、半径radiusの円の当たり判定を登録します。
     * @param dx x座標のSymbolの座標からずらす分
     * @param dy y座標のSymbolの座標からずらす分
     * @param radius HitBoxの半径
     */
    public void registerCircle(double dx, double dy, double radius) {
        setRelativeHitBoxShape(new Ellipse2D.Double(dx - radius, dy - radius, 2 * radius, 2 * radius));
    }

    /**
     * Symbolの座標を中心に、半径radiusの円の当たり判定を登録します。
     * @param radius HitBoxの半径
     */
    public void replaceCircle(double radius) {
        registerCircle(0, 0, radius);
    }
}
