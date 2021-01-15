package net.trpfrog.medipro_game.fieldmap;

import net.trpfrog.medipro_game.data_structures.FastGridTree;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 宇宙の地図(平面)を管理するクラス。
 * @author つまみ
 */
public class FieldMap {

    private FastGridTree<Symbol> symbolsTree;

    private int chunkSquareLength;
    private int numberOfHorizontalChunks, numberOfVerticalChunks;

    /**
     * 指定したサイズの平面マップを作成します。
     * マップは一辺 {@code chunkSquareLength} の正方形のチャンクで区切られ、
     * そのチャンクが縦横がいくつ並ぶかでサイズが決定されます。
     * @param numberOfHorizontalChunks 横方向のチャンクの数
     * @param numberOfVerticalChunks 縦方向のチャンクの数
     * @param chunkSquareLength 正方形のチャンクの一辺の長さ
     */
    public FieldMap(int numberOfHorizontalChunks,
                    int numberOfVerticalChunks,
                    int chunkSquareLength) {
        this.chunkSquareLength = chunkSquareLength;
        this.numberOfHorizontalChunks = numberOfHorizontalChunks;
        this.numberOfVerticalChunks = numberOfVerticalChunks;
        symbolsTree = new FastGridTree<>();
    }

    /**
     * 指定したサイズの平面マップを作成します。
     * マップは一辺 {@code chunkSquareLength} (デフォルト: 16) の正方形のチャンクで区切られ、
     * そのチャンクが縦横がいくつ並ぶかでサイズが決定されます。
     * @param numberOfHorizontalChunks 横方向のチャンクの数
     * @param numberOfVerticalChunks 縦方向のチャンクの数
     */
    public FieldMap(int numberOfHorizontalChunks, int numberOfVerticalChunks) {
        this(numberOfHorizontalChunks, numberOfVerticalChunks, 16);
    }

    /**
     * マップのx, y座標を指定してシンボルを追加します。
     * @param x x座標
     * @param y y座標
     * @param symbol 追加するシンボル
     */
    public void addSymbol(int x, int y, Symbol symbol) {
        symbolsTree.put(x, y, symbol);
        symbol.setLocation(x,y);
    }

    /**
     * マップのx, y座標とシンボルを指定してそのシンボルを削除します。
     * @param x x座標
     * @param y y座標
     * @param symbol 削除するシンボル
     */
    public void removeSymbol(int x, int y, Symbol symbol) {
        symbolsTree.remove(x, y, symbol);
    }

    /**
     * 座標がマップの範囲内であるかを確認します。
     * @param x x座標
     * @param y y座標
     * @return 座標がマップの範囲内であるかどうか
     */
    public boolean isWithin(int x, int y) {
        return 0 <= x && x < chunkSquareLength * numberOfHorizontalChunks
                && 0 <= y && y < chunkSquareLength * numberOfVerticalChunks;
    }

    /**
     * チャンクの一辺の長さを取得します。
     * @return チャンクの一辺の長さ
     */
    public int getChunkSquareLength() {
        return chunkSquareLength;
    }

    /**
     * 横方向のチャンクの数を返します。
     * @return 横方向のチャンクの数
     */
    public int getNumberOfHorizontalChunks() {
        return numberOfHorizontalChunks;
    }

    /**
     * 縦方向のチャンクの数を返します。
     * @return 縦方向のチャンクの数
     */
    public int getNumberOfVerticalChunks() {
        return numberOfVerticalChunks;
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
        return symbolsTree.stream(range);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldMap fieldMap = (FieldMap) o;
        return chunkSquareLength == fieldMap.chunkSquareLength
                && numberOfHorizontalChunks == fieldMap.numberOfHorizontalChunks
                && numberOfVerticalChunks == fieldMap.numberOfVerticalChunks
                && symbolsTree.equals(fieldMap.symbolsTree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbolsTree, chunkSquareLength,
                numberOfHorizontalChunks, numberOfVerticalChunks);
    }
}

