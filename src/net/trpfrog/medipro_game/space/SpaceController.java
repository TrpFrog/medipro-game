package net.trpfrog.medipro_game.space;


import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.space.symbols.Rocket;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpaceController extends GameController implements KeyListener {

    private SpaceModel model;
    private SpaceView view;

    public SpaceController(SpaceModel model, SpaceView view) {
        super(model, view);
        this.model = model;

        Rocket rocket = model.getRocket();
        rocket.start(); // とりあえずC側でスタート

        this.view = view;
        this.view.addKeyListener(this);
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Rocket rocket = model.getRocket();
        double currentSpeedPxPerSecond = rocket.getSpeedPxPerSecond();
        double dSpeedPxPerSecond = 20.0; // とりあえず
        double dAngleDegrees = 5.0; // とりあえず

        switch (e.getKeyChar()){
            // W: 加速
            case 'w' -> {
                if(500.0 <= currentSpeedPxPerSecond) return; // 上限速度の場合に離脱
                currentSpeedPxPerSecond += dSpeedPxPerSecond;
                rocket.setSpeedPxPerSecond(currentSpeedPxPerSecond);
            }
            // A: 左旋回
            case 'a' -> rocket.turnAnticlockwiseDegrees(-dAngleDegrees);
            // S: 減速
            case 's' -> {
                if(currentSpeedPxPerSecond <= 0) return; // 下限速度の場合に離脱
                currentSpeedPxPerSecond -= dSpeedPxPerSecond;
                rocket.setSpeedPxPerSecond(currentSpeedPxPerSecond);
            }
            // D: 右旋回
            case 'd' -> rocket.turnAnticlockwiseDegrees(dAngleDegrees);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
