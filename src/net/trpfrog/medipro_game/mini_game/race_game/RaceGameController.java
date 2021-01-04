package net.trpfrog.medipro_game.mini_game.race_game;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.pause.EscapeToPause;
import net.trpfrog.medipro_game.scene.GameController;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RaceGameController extends GameController implements KeyListener {

    private RaceGameModel model;
    private RaceGameView  view;

    public RaceGameController(RaceGameModel model, RaceGameView view) {
        super(model, view);
        this.model = model;
        this.view = view;

        // view に listener を add する
        view.addKeyListener(new EscapeToPause());
        view.addKeyListener(this);
    }

    private boolean active = false;

    // 操作用タイマー
    private final Timer forwardTimer = new Timer(10, e -> model.getCar().accelerate(10));
    private final Timer backTimer    = new Timer(10, e -> model.getCar().accelerate(-10));

    private final Timer leftTimer    = new Timer(10, e -> {
        var car = model.getCar();
        if(forwardTimer.isRunning() || backTimer.isRunning()) {
            car.turnClockwiseDegrees(car.getSpeedPxPerSecond() / car.MAX_SPEED * 2);
        }
    });

    private final Timer rightTimer   = new Timer(10, e -> {
        var car = model.getCar();
        if(forwardTimer.isRunning() || backTimer.isRunning()) {
            car.turnAnticlockwiseDegrees(car.getSpeedPxPerSecond() / car.MAX_SPEED * 2);
        }
    });

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!active) return;
        switch(e.getKeyChar()) {
            case 'w' -> forwardTimer.start();
            case 'a' -> leftTimer.start();
            case 's' -> backTimer.start();
            case 'd' -> rightTimer.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!active) return;
        switch(e.getKeyChar()) {
            case 'w' -> forwardTimer.stop();
            case 'a' -> leftTimer.stop();
            case 's' -> backTimer.stop();
            case 'd' -> rightTimer.stop();
        }
    }

    // この2つでタイマーの制御をする
    @Override
    public void suspend() {
        active = false;
        forwardTimer.stop();
        rightTimer.stop();
        leftTimer.stop();
        backTimer.stop();
    }
    @Override
    public void resume() {
        active = true;
    }
}
