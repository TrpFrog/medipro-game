package net.trpfrog.medipro_game.mini_game.shonben_kozou.symbols;

import net.trpfrog.medipro_game.mini_game.shonben_kozou.ShonbenKozouModel;
import net.trpfrog.medipro_game.symbol.Symbol;

public class GameTimer extends Symbol {
    private ShonbenKozouModel model;
    private int count;
    private int remain;

    public GameTimer(ShonbenKozouModel model){
        this.model = model;
        count = 0;
        remain = 1000;
    }

    public int getCount(){
        return count;
    }

    public int getRemain(){
        return remain;
    }

    public void move(){
        count ++;
        if(count >= 300 && remain > 0) remain -= 1;
    }
}
