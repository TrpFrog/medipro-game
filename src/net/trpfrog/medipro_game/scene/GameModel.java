package net.trpfrog.medipro_game.scene;

import net.trpfrog.medipro_game.symbol.Symbol;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * ゲームシーンのModelを定義する抽象クラス。
 * ゲームシーンのModelは必ずこの抽象クラスを継承してください。
 * @author つまみ
 */
public abstract class GameModel implements GameMVC {

    private List<Symbol> symbolsList = new LinkedList<>();

    public List<Symbol> getSymbolsList() {
        return symbolsList;
    }

    public void addSymbol(Symbol symbol) {
        symbolsList.add(symbol);
    }
}
