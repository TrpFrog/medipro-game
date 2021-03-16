package net.trpfrog.medipro_game.data_structures;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

/**
 * 2次元グリッド上にオブジェクトを配置するデータ構造。
 * このデータ構造を使うと長方形の区間について高速に処理を行うことができます。
 * また、同一の座標に複数のオブジェクトを乗せることができます。
 * 高速な部分木の切り出しのためのTreeMapとデータ重複時用のLinkedListにより実装されています。
 * @param <T> 乗せるオブジェクトの型
 * @author つまみ
 */
public class FastGridTree<T> {
    private final TreeMap<Integer, TreeMap<Integer, LinkedList<T>>> tree = new TreeMap<>();

    /**
     * グリッド(x,y)にオブジェクトを追加します。
     * 既にオブジェクトが存在した場合も元の要素を消さずに追加されます。
     * 計算量は O(log(W)+log(H)) です。
     * @param x グリッドのx座標
     * @param y グリッドのy座標
     * @param object 追加したいオブジェクト
     */
    public void put(int x, int y, T object) {
        if(!tree.containsKey(x)) tree.put(x, new TreeMap<>());
        if(!tree.get(x).containsKey(y)) tree.get(x).put(y, new LinkedList<>());
        tree.get(x).get(y).add(object);
    }

    /**
     * グリッド(x,y)にオブジェクトを追加します。
     * 既にオブジェクトが存在した場合は元の要素を削除します。
     * 計算量は O(log(W)+log(H)) です。
     * @param x グリッドのx座標
     * @param y グリッドのy座標
     * @param object 追加したいオブジェクト
     */
    public void replace(int x, int y, T object) {
        removeAll(x, y);
        put(x, y, object);
    }

    /**
     * グリッド(x,y)から指定したオブジェクトを削除します。
     * 計算量は O(log(W)+log(H)) です。
     * @param x グリッドのx座標
     * @param y グリッドのy座標
     * @param object 削除したいオブジェクト
     */
    public void remove(int x, int y, T object) {
        if(tree.containsKey(x) && tree.get(x).containsKey(y)){
            tree.get(x).get(y).remove(object);
        }
    }

    /**
     * グリッド(x,y)から全てのオブジェクトを削除します。
     * @param x グリッドのx座標
     * @param y グリッドのy座標
     */
    public void removeAll(int x, int y) {
        if(tree.containsKey(x) && tree.get(x).containsKey(y)){
            tree.get(x).get(y).clear();
        }
    }

    /**
     * グリッド(x,y)の要素をリストとして取得します。
     * @param x グリッドのx座標
     * @param y グリッドのy座標
     * @return グリッド上に存在する要素のリスト
     */
    public List<T> get(int x, int y) {
        if(tree.containsKey(x) && tree.get(x).containsKey(y)){
            return tree.get(x).get(y);
        } else {
            return List.of();
        }
    }

    /**
     * グリッド全体の要素をStreamとして取得します。
     * @return グリッド上に存在する要素のリスト
     */
    public Stream<T> stream() {
        return tree
                .values()
                .stream()
                .flatMap(e -> e.values().stream())
                .flatMap(Collection::stream);
    }

    /**
     * グリッド(x,y)の要素をStreamとして取得します。
     * @param x グリッドのx座標
     * @param y グリッドのy座標
     * @return グリッド上に存在する要素のリスト
     */
    public Stream<T> stream(int x, int y) {
        return get(x, y).stream();
    }


    /**
     * 指定した範囲に含まれる要素のStreamを返します。<br>
     * 計算量はフィールド全体の大きさを (H, W) として
     * O(log(H) + log(W) + range.h * range.w) です。
     * @param range オブジェクトが含まれている範囲
     * @return 範囲内に存在するオブジェクトのStream
     */
    public Stream<T> stream(final Rectangle range) {
        return tree
                // O(log(W))で範囲内の部分木を取得
                .subMap(range.x, range.x + range.width)
                // ValueのStreamを取得
                .values().stream()
                // 上で得た部分木のそれぞれに対し、範囲内の部分木をO(log(H))で取得
                .map(e -> e.subMap(range.y, range.y + range.height))
                // 部分木からStreamを取得
                .flatMap(e -> e.values().stream())
                // Stream上のLinkedListを全てStreamにしてフラット化
                .flatMap(Collection::stream);
    }

    public boolean contains(T object) {
        return stream().anyMatch(e -> e.equals(object));
    }

    /**
     * この実装に使われている生のTreeMapを取得します。
     * @return 実態のTreeMap
     */
    public TreeMap<Integer, TreeMap<Integer, LinkedList<T>>> getRawTree() {
        return tree;
    }
}