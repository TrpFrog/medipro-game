package net.trpfrog.medipro_game.mini_game.race_game.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.util.ResourceLoader;

import javax.swing.*;
import java.awt.*;

public class Car extends MovableSymbol implements Drawable, Suspendable {
    private Image image;

    public final int MAX_SPEED = 500;
    private int nowspeed = 0;

    // 速度調整用タイマー
    private final Timer speedTimer   = new Timer(10, e -> {
        if(nowspeed > 0) accelerate(-8);
        if(nowspeed < 0) accelerate(8);
        setSpeedPxPerSecond(nowspeed);
        moveMilliseconds(10);
    });


    public Car() {

        // 画像の設定
        image = ResourceLoader.readImage(
                ".", "resource", "mini_game", "race_game", "car.png"
        );

        // 初期位置の設定
        setLocation(100, 100);

        // 速度の設定
        setSpeedPxPerSecond(nowspeed);

        // 当たり判定の設定
        var hitbox = RelativeHitBox.makeRectangle(80, 40);
        this.setRelativeHitBox(hitbox);

        // Drawableを乗せる
        setDrawer(this);
    }

    /**
     * 加速します
     * @param a 加速度
     */
    public void accelerate(int a) {
        if(a > 0) {
            nowspeed = Math.min( MAX_SPEED, nowspeed + a);
        } else {
            nowspeed = Math.max(-MAX_SPEED, nowspeed + a);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        Rectangle rect = new Rectangle((int)getX(), (int)getY(), 0, 0);
        rect.grow(40, 20);

        // 回転して画像をdraw
        double angle = this.getAngleRadians();
        g.rotate(angle, (int)getX(), (int)getY());
        g.drawImage(image, (int)rect.getX(),     (int)rect.getY(),
                           (int)rect.getWidth(), (int)rect.getHeight(), null);
        g.rotate(-angle, (int)getX(), (int)getY());
    }

    // 速度調整のタイマーを止められるようにする

    @Override
    public void suspend() {
        speedTimer.stop();
    }

    @Override
    public void resume() {
        speedTimer.start();
    }
}
