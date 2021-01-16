package net.trpfrog.medipro_game.space.map;

import java.util.ArrayList;
import java.util.List;

/**
 * 宇宙の地図(立体)を管理するクラス。
 * @author つまみ
 */
public class SpaceMap3D {
    private List<SpaceMap2D> list;

    /**
     * マップの深さを返します。
     * @return マップの深さ
     */
    public int getDepth() {
        return list.size();
    }

    /**
     * 指定したサイズの立体宇宙マップを作成します。
     * 3Dマップはdepth枚の平面宇宙マップ(SpaceMap2D)から構成されます。
     * @see SpaceMap2D
     * @param chunkH 縦方向のチャンクの数
     * @param chunkW 横方向のチャンクの数
     * @param chunkSize 正方形のチャンクの一辺の長さ
     * @param depth マップの層の数
     */
    public SpaceMap3D(int chunkW, int chunkH, int chunkSize, int depth) {
        list = new ArrayList<>(depth);
        for(int i = 0; i < depth; i++) {
            list.add(new SpaceMap2D(chunkH, chunkW, chunkSize));
        }
    }

    /**
     * 指定した階層(0-indexed)の平面宇宙マップを返します。
     * @param depth 取得するマップの階層
     * @return その階層のSpaceMap2D
     */
    public SpaceMap2D get2DMap(int depth) {
        return list.get(depth);
    }
}
