package net.trpfrog.medipro_game.space.map;

import net.trpfrog.medipro_game.fieldmap.FieldMap;
import net.trpfrog.medipro_game.space.symbols.Star;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.util.stream.Stream;

/**
 * 宇宙の地図(平面)を管理するクラス。
 * @author つまみ
 */
public class SpaceMap2D extends FieldMap {

    private boolean[][] visited;

    public SpaceMap2D(int numberOfVerticalChunks,
                      int numberOfHorizontalChunks,
                      int chunkSquareLength) {

        super(numberOfHorizontalChunks, numberOfVerticalChunks, chunkSquareLength);
        visited = new boolean[numberOfHorizontalChunks][numberOfVerticalChunks];
    }

    public SpaceMap2D(int numberOfHorizontalChunks,
                      int numberOfVerticalChunks) {

        super(numberOfHorizontalChunks, numberOfVerticalChunks);
        visited = new boolean[numberOfHorizontalChunks][numberOfVerticalChunks];
    }

    /**
     * 指定した座標が未踏のチャンクであればチャンク内に星オブジェクトを自動生成します。
     * @param x x座標
     * @param y x座標
     */
    public void generateStars(int x, int y) {
        if(!isWithin(x, y)) return;
        int chunkSize = getChunkSquareLength();
        int cx = x/chunkSize, cy = y/chunkSize;
        if(visited[cx][cy]) return;

        double AUTO_GENERATED_STAR_DENSITY = 0.1;

        for(int i = cx * chunkSize; i < (cx + 1) * chunkSize; i++) {
            for(int j = cy * chunkSize; j < (cy + 1) * chunkSize; j++) {
                if(Math.random() > AUTO_GENERATED_STAR_DENSITY) continue;
                Star star = Star.getRandomStar();
                star.setLocation(i, j);
                addSymbol(x, y, Star.getRandomStar());
            }
        }

        visited[cx][cy] = true;
    }

    /**
     * 指定した範囲に含まれるSymbolのStreamを返します。<br>
     * もし未踏のチャンクが含まれていればそれを同時に生成します。<br>
     * 計算量はフィールド全体の大きさを (H, W) として
     * O(log(H) + log(W) + range.h * range.w) です。
     * @param range Symbolが含まれている範囲
     * @return 範囲内に存在するSymbolのStream
     */
    public Stream<Symbol> rangeSymbolStream(final Rectangle range) {
        for(int i = range.x; i < range.x + range.width; i += getChunkSquareLength()) {
            for(int j = range.y; j < range.y + range.height; j += getChunkSquareLength()) {
                generateStars(i, j);
            }
        }
        return super.rangeSymbolStream(range);
    }
}

