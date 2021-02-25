package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.symbol.Symbol;
import net.trpfrog.medipro_game.util.TimeLimited;

import java.awt.*;
import java.util.function.UnaryOperator;

public class Twinkle extends Symbol implements TimeLimited {

    private final TimeLimited.Impl limit;

    private Color randWhiterRGB() {
        return new Color(
                200 + (int)(55 * Math.random()),
                200 + (int)(55 * Math.random()),
                200 + (int)(55 * Math.random())
        );
    }

    /**
     * キラキラのオブジェクトを生成します。
     * @param x 生成するマップ上のx座標
     * @param y 生成するマップ上のy座標
     * @param duringMillis 画面上に滞在する時間
     * @param shineFunction 画面上に現れてから消えるまでを [0, 1] で表した値を受け取り、
     *                      それをもとにキラキラの大きさを決定する関数。
     *                      デフォルトは 10sin(t * 2pi)
     */
    public Twinkle(double x, double y, int duringMillis, UnaryOperator<Double> shineFunction) {
        super(x, y);
        limit = new TimeLimited.Impl(duringMillis);

        setDrawer(g -> {
            if(isOutdated()) return;

            double passedTimeRatio = 1 - (getDeadline() - System.currentTimeMillis()) / (double)duringMillis;
            int l = shineFunction.apply(passedTimeRatio).intValue();
            g.setColor(randWhiterRGB());

            g.drawLine((int)getX() - l, (int)getY(), (int)getX() + l, (int)getY());
            g.drawLine((int)getX(), (int)getY() - l, (int)getX(), (int)getY() + l);
        });
    }

    public Twinkle(double x, double y, int duringMillis) {
        this(x, y, duringMillis, timeRatio -> (10 * Math.sin(timeRatio * Math.PI)));
    }

    @Override
    public boolean isOutdated() {
        return limit.isOutdated();
    }

    @Override
    public long getDeadline() {
        return limit.getDeadline();
    }
}
