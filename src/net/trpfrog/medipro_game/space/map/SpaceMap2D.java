package net.trpfrog.medipro_game.space.map;

import net.trpfrog.medipro_game.data_structures.FastGridTree;
import net.trpfrog.medipro_game.space.symbols.Star;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.util.stream.Stream;

/**
 * 宇宙の地図(平面)を管理するクラス。
 * @author つまみ
 */
public class SpaceMap2D {

    private FastGridTree<Symbol> symbolsTree;

    private int chunkSize;
    private int chunkW, chunkH;
    private boolean[][] visited;

    /**
     * 指定したサイズの平面宇宙マップを作成します。
     * マップは一辺chunkSizeの正方形のチャンクで区切られ、
     * そのチャンクが縦横がいくつ並ぶかでサイズが決定されます。
     * チャンクは星の自動生成の処理に使用されます。
     * @param chunkH 縦方向のチャンクの数
     * @param chunkW 横方向のチャンクの数
     * @param chunkSize 正方形のチャンクの一辺の長さ
     */
    public SpaceMap2D(int chunkH, int chunkW, int chunkSize) {
        this.chunkSize = chunkSize;
        this.chunkW = chunkW;
        this.chunkH = chunkH;
        visited = new boolean[chunkW][chunkH];
        symbolsTree = new FastGridTree<>();
    }

    /**
     * 指定したサイズの平面宇宙マップを作成します。
     * マップは一辺 chunkSize (デフォルト: 16) の正方形のチャンクで区切られ、
     * そのチャンクが縦横がいくつ並ぶかでサイズが決定されます。
     * チャンクは星の自動生成の処理に使用されます。
     * @param chunkW 横方向のチャンクの数
     * @param chunkH 縦方向のチャンクの数
     */
    public SpaceMap2D(int chunkW, int chunkH) {
        this.chunkSize = 16;
        this.chunkW = chunkW;
        this.chunkH = chunkH;
    }

    /**
     * マップのx, y座標を指定してシンボルを追加します。
     * @param p 座標
     * @param symbol 追加するシンボル
     */
    public void addSymbol(Point p, Symbol symbol) {
        symbolsTree.put(p.x, p.y, symbol);
    }

    /**
     * マップのx, y座標とシンボルを指定してそのシンボルを削除します。
     * @param p 座標
     * @param symbol 削除するシンボル
     */
    public void removeSymbol(Point p, Symbol symbol) {
        symbolsTree.remove(p.x, p.y, symbol);
    }

    /**
     * 座標がマップの範囲内であるかを確認します。
     * @param p 座標
     * @return 座標がマップの範囲内であるかどうか
     */
    public boolean isWithin(Point p) {
        return 0 <= p.x && p.x < chunkSize * chunkW
                && 0 <= p.y && p.y < chunkSize * chunkH;
    }

    /**
     * 指定した座標が未踏のチャンクであればチャンク内に星オブジェクトを自動生成します。
     * @param x x座標
     * @param y x座標
     */
    public void generateStars(int x, int y) {
        if(!isWithin(new Point(x, y))) return;
        int cx = x/chunkSize, cy = y/chunkSize;
        if(visited[cx][cy]) return;

        double AUTO_GENERATED_STAR_DENSITY = 0.1;

        for(int i = cx * chunkSize; i < (cx + 1) * chunkSize; i++) {
            for(int j = cy * chunkSize; j < (cy + 1) * chunkSize; j++) {
                if(Math.random() > AUTO_GENERATED_STAR_DENSITY) continue;
                Star star = Star.getRandomStar();
                star.getPoint().setLocation(i, j);
                addSymbol(new Point(i, j), Star.getRandomStar());
            }
        }

        visited[cx][cy] = true;
    }

    /**
     * 指定した座標が未踏のチャンクであればチャンク内に星オブジェクトを自動生成します。
     * @param p 座標
     */
    public void generateStars(Point p) {
        generateStars(p.x, p.y);
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
        for(int i = range.x; i < range.x + range.width; i += chunkSize) {
            for(int j = range.y; j < range.y + range.height; j += chunkSize) {
                generateStars(i, j);
            }
        }
        return symbolsTree.stream(range);
    }
}

