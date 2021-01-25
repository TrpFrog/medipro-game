package net.trpfrog.medipro_game.util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 一定間隔以上離れた点を生成するメソッドを持つクラス
 * @author つまみ
 */
public class SparsePointsBuilder {

    private static final double sqrt2 = Math.sqrt(2);

    // 六角形に範囲を切り分けてその頂点集合を返します。
    private static List<Point> createHexagonalSeparatedPoints(Rectangle range, int interval) {
        List<Point> ret = new ArrayList<>();

        final int xLimit = range.x + range.width;
        final int yLimit = range.y + range.height;

        for(int rows = 0; range.y + rows * interval * sqrt2 / 2 < yLimit; rows++) {
            double y = rows * interval * sqrt2 / 2;
            for(int x = range.x + (rows % 2) * interval / 2; x < xLimit; x += interval) {
                ret.add(new Point(x, (int)Math.round(y)));
            }
        }

        return ret;
    }

    // N点を選びます
    private static List<Point> selectNPoints(List<Point> list, int n) {
        if(n == -1) n = list.size();
        if(list.size() < n) {
            throw new IllegalArgumentException("Too many points!");
        }
        for(int i = 0; i < n; i++) {
            int selected = ThreadLocalRandom.current().nextInt(list.size() - i) + i;
            Collections.swap(list, i, selected);
        }
        return list.subList(0, n);
    }

    /**
     * 区間 {@code range} に収まる、それぞれが {@code interval} 以上の距離を持つ
     * {@code points} 点の座標のリストをランダムに生成します。
     * {@code points == -1} のときはできる点全てを返します。
     * アルゴリズムは以下の通りです。<br><br>
     *
     * <b>アルゴリズム</b><br>
     * 与えられた区間を一辺 {@code 2 * interval} の六角形の最密充填構造に切り分け、
     * その頂点の集合からランダムに {@code points} 点を選びます。
     * 選び終えたらそれぞれの点を中心とした半径 {@code interval} の円から
     * 1点をランダムに選び、点をそこへ移動します。
     * 全ての点に対し、この操作を行ってできた点のリストを返します。
     *
     * @param range 点を生成する範囲
     * @param interval 点の間隔の最小値
     * @param points 選ぶ点の数
     * @return 区間 {@code range} に収まる、それぞれが {@code interval} 以上の距離を持つ
     *         {@code points} 点の座標のリスト
     */
    public static List<Point> build(Rectangle range, int interval, int points) {

        List<Point> selected  = selectNPoints(
                createHexagonalSeparatedPoints(range, interval * 2),
                points
        );

        for(var point : selected) {
            double dR     = ThreadLocalRandom.current().nextDouble(interval);
            double dTheta = ThreadLocalRandom.current().nextDouble(2 * Math.PI);
            int dx = (int) (dR * Math.cos(dTheta));
            int dy = (int) (dR * Math.sin(dTheta));
            point.translate(dx, dy);
        }

        return selected;
    }

}