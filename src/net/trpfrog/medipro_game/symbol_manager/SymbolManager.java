package net.trpfrog.medipro_game.symbol_manager;

import net.trpfrog.medipro_game.symbol.Symbol;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * {@link Symbol} の集合を管理するクラス
 * @param <T> Symbolを継承した型
 */
public class SymbolManager<T extends Symbol> extends LinkedList<T> {

    private List<Predicate<T>> removeConditions = new LinkedList<>();

    /**
     * {@link SymbolManager#cleanup()} が呼ばれたときに適用する {@code Symbol} の削除条件を追加します。
     * @param condition 追加する削除条件
     */
    public void addRemoveCondition(Predicate<T> condition) {
        removeConditions.add(condition);
    }

    /**
     * {@link SymbolManager#cleanup()} が呼ばれたときに適用する {@code Symbol} の削除条件を削除します。
     * @param condition 削除する削除条件
     */
    public void removeRemoveCondition(Predicate<T> condition) {
        removeConditions.remove(condition);
    }

    /**
     * {@link SymbolManager#addRemoveCondition} で追加した条件に合う {@code Symbol} を削除します。
     */
    public void cleanup() {
        removeIf(e -> removeConditions.stream().anyMatch(cd -> cd.test(e)));
    }
}
