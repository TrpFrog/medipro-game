package net.trpfrog.medipro_game.data_structures;

import net.trpfrog.medipro_game.symbol.Symbol;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * {@link Symbol} の集合を管理するクラス
 * @param <T> Symbolを継承した型
 */
public class SymbolManager<T extends Symbol> extends LinkedList<T> {

    private final List<Predicate<T>> removeConditions = new LinkedList<>();
    private final List<Consumer<T>>  removingHooks    = new LinkedList<>();

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
     * {@link SymbolManager#cleanup()} により削除されたときの挙動を追加します。
     * @param hook 削除時の挙動
     */
    public void addRemovingHook(Consumer<T> hook) {
        removingHooks.add(hook);
    }

    /**
     * {@link SymbolManager#cleanup()} により削除されたときの挙動を削除します。
     * @param hook 削除する削除時の挙動
     */
    public void removeRemovingHook(Consumer<T> hook) {
        removingHooks.remove(hook);
    }


    /**
     * {@link SymbolManager#addRemoveCondition} で追加した条件に合う {@code Symbol} を削除します。
     */
    public void cleanup() {
        stream().filter(e -> removeConditions.stream().anyMatch(cd -> cd.test(e)))
                .forEach(e -> removingHooks.forEach(hook -> hook.accept(e)));
        removeIf(e -> removeConditions.stream().anyMatch(cd -> cd.test(e)));
    }
}
